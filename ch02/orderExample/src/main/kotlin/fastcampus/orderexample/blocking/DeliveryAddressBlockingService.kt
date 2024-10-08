package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.DeliveryAddress


class DeliveryAddressBlockingService {
    fun findDeliveryAddresses(ids: List<Long>): List<DeliveryAddress> {
        return ids.mapIndexed { index, id ->
            DeliveryAddress(
                id = id,
                roadNameAddress = "도로명 주소 $id",
                detailAddress = "상세 주소 $id",
            )
        }
    }
}