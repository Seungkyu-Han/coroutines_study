package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.Customer
import fastcampus.orderexample.common.DeliveryAddress
import fastcampus.orderexample.common.Product
import fastcampus.orderexample.common.Store

data class Order (
    val stores: List<Store>,
    val products: List<Product>,
    val customer: Customer,
    val deliveryAddress: DeliveryAddress
)