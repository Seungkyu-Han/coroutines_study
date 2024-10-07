package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.Store


class StoreBlockingService {
    fun findStoresByIds(storeIds: List<Long>): List<Store> {
        return storeIds.map { Store(it, "매장 $it") }
    }
}