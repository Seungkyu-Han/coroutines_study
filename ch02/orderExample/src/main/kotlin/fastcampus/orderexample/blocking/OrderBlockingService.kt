package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.Customer
import fastcampus.orderexample.common.DeliveryAddress
import fastcampus.orderexample.common.Product
import fastcampus.orderexample.common.Store

class OrderBlockingService {

    fun createOrder(
        customer: Customer,
        products: List<Product>,
        deliveryAddress: DeliveryAddress,
        stores: List<Store>
    ): Order{
        return Order(
            stores = stores,
            products = products,
            customer = customer,
            deliveryAddress = deliveryAddress
        )
    }
}