package fastcampus.orderexample.async

import fastcampus.orderexample.common.Store
import io.smallrye.mutiny.Multi


class StoreMutinyService {
    fun findStoresMutli(storeIds: List<Long>): Multi<Store> {
        return Multi.createFrom().emitter {
            storeIds.map { id ->
                Store(id, "매장 $id")
            }.forEach { store ->
                Thread.sleep(100)
                it.emit(store)
            }
            it.complete()
        }
    }
}