package fastcampus.orderexample.async

import fastcampus.orderexample.common.Product
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

class ProductRxjava3Service {

    fun findAllProductsFlowable(
        ids: List<Long>
    ): Flowable<Product>{
        return Flowable.create({
            emitter -> ids.forEach {
                Thread.sleep(100)
            val p = Product(it, "상품 $it", 1000L + it)
            emitter.onNext(p)
        }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}