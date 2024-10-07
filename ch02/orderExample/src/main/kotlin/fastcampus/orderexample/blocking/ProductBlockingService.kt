package fastcampus.orderexample.blocking

import fastcampus.orderexample.common.Product

class ProductBlockingService {
    fun findAllProductsByIds(ids: List<Long>): List<Product>{
        return ids.map{Product(it,"상품 $it", 1000L + it)}
    }
}