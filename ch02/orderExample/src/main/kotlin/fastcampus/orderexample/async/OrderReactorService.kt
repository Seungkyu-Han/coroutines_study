package fastcampus.orderexample.async

import fastcampus.orderexample.blocking.Order
import fastcampus.orderexample.common.Customer
import fastcampus.orderexample.common.DeliveryAddress
import fastcampus.orderexample.common.Product
import fastcampus.orderexample.common.Store
import reactor.core.publisher.Mono

class OrderReactorService {
    fun createOrderMono(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>,
    ): Mono<Order> {
        return Mono.create { sink ->
            Thread.sleep(1000)
            sink.success(
                Order(
                    stores = stores,
                    products = products,
                    customer = customer,
                    deliveryAddress = deliveryAddress,
                )
            )
        }
    }
}