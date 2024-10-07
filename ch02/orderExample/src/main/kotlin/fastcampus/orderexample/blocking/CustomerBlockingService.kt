package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.Customer

class CustomerBlockingService {
    fun findCustomerById(id: Long): Customer {
        return Customer(id, "seungkyu", listOf(1, 2, 3))
    }
}