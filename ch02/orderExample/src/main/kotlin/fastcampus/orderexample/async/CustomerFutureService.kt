package fastcampus.orderexample.async

import fastcampus.orderexample.common.Customer
import java.util.concurrent.CompletableFuture

class CustomerFutureService {

    fun findCustomerFuture(id: Long): CompletableFuture<Customer>{
        return CompletableFuture.supplyAsync {
            Thread.sleep(1000)
            Customer(id, "seungkyu", listOf(1, 2, 3))
        }
    }
}