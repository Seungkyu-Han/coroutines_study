package fastcampus.orderexample.async

import fastcampus.orderexample.blocking.Order
import fastcampus.orderexample.common.Customer
import fastcampus.orderexample.common.DeliveryAddress
import fastcampus.orderexample.common.Product
import fastcampus.orderexample.common.Store
import io.reactivex.rxjava3.core.Single
import io.smallrye.mutiny.Uni
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.properties.Delegates


class OrderAsyncExampleUpgrade3(
    private val customerService: CustomerFutureService,
    private val productService: ProductRxjava3Service,
    private val storeService: StoreMutinyService,
    private val deliveryAddressService: DeliveryAddressPublisherService,
    private val orderService: OrderReactorService,
) {
    private class CustomContinuation(
        private val completion: Continuation<Any>,
    ) : Continuation<Any> {
        var result: Any? = null
        var label = 0

        // arguments and instance
        lateinit var that: OrderAsyncExampleUpgrade3
        var userId by Delegates.notNull<Long>()
        lateinit var productIds: List<Long>

        // variables
        lateinit var customer: Customer
        lateinit var products: List<Product>
        lateinit var stores: List<Store>
        lateinit var deliveryAddress: DeliveryAddress

        override val context: CoroutineContext
            get() = completion.context

        override fun resumeWith(result: Result<Any>) {
            this.result = result.getOrThrow()
            that.execute(0, emptyList(), this)
        }

        fun complete(value: Any) {
            completion.resume(value)
        }
    }

    fun execute(userId: Long,
                productIds: List<Long>,
                continuation: Continuation<Any>) {

        val cont = if (continuation is CustomContinuation) {
            continuation
        } else {
            CustomContinuation(continuation).apply {
                that = this@OrderAsyncExampleUpgrade3
                this.userId = userId
                this.productIds = productIds
            }
        }

        when (cont.label) {
            0 -> {
                // 1. 고객 정보 조회
                cont.label = 1

                customerService.findCustomerFuture(cont.userId)
                    .await(cont)
            }
            1 -> {
                // 2. 상품 정보 조회
                cont.customer = cont.result as Customer
                cont.label = 2

                productService.findAllProductsFlowable(cont.productIds)
                    .toList().await(cont)
            }
            2 -> {
                // 3. 스토어 조회
                cont.products = cont.result as List<Product>
                cont.label = 3

                val products = cont.products
                val storeIds = products.map { it.storeId }
                storeService.findStoresMutli(storeIds)
                    .collect().asList().awaitSuspending(cont)
            }
            3 -> {
                // 4. 주소 조회
                cont.stores = cont.result as List<Store>
                cont.label = 4

                val customer = cont.customer
                val daIds = customer.deliveryAddressIds
                deliveryAddressService.findDeliveryAddressesPublisher(daIds)
                    .awaitFirst(cont)
            }
            4 -> {
                // 5. 주문 생성
                cont.deliveryAddress = cont.result as DeliveryAddress
                cont.label = 5

                val customer = cont.customer
                val products = cont.products
                val deliveryAddress = cont.deliveryAddress
                val stores = cont.stores

                orderService.createOrderMono(
                    customer, products, deliveryAddress, stores,
                ).awaitSingle(cont)
            }
            5 -> {
                val order = cont.result as Order
                cont.complete(order)
            }
        }
    }

    private fun <T> CompletableFuture<T>.await(
        cont: Continuation<T>
    ) {
        this.thenAccept(cont::resume)
    }

    private fun <T> Single<T>.await(cont: Continuation<T>)
            where T : Any {
        this.subscribe(cont::resume)
    }

    private fun <T> Uni<T>.awaitSuspending(cont: Continuation<T>) {
        this.subscribe().with(cont::resume)
    }

    private fun <T> Publisher<T>.awaitFirst(
        cont: Continuation<T>
    ) {
        this.subscribe(FirstFinder(cont::resume))
    }

    private fun <T> Mono<T>.awaitSingle(
        cont: Continuation<T>
    ) {
        this.subscribe(cont::resume)
    }
}

fun main(args: Array<String>) {
    val customerService = CustomerFutureService()
    val productService = ProductRxjava3Service()
    val storeService = StoreMutinyService()
    val deliveryAddressService = DeliveryAddressPublisherService()
    val orderService = OrderReactorService()

    val example = OrderAsyncExampleUpgrade3(
        customerService = customerService,
        productService = productService,
        storeService = storeService,
        deliveryAddressService = deliveryAddressService,
        orderService = orderService,
    )

    val cont = Continuation<Any>(EmptyCoroutineContext) {
        println("result: $it")
    }

    example.execute(1, listOf(1, 2, 3), cont)
    Thread.sleep(5000)
}