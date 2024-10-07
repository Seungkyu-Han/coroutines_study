package fastcampus.orderexample.common

data class Customer(
    val id: Long,
    val name: String,
    val deliveryAddressIds: List<Long>
)