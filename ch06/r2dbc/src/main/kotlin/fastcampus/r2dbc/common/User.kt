package fastcampus.r2dbc.common

import java.util.*

data class User(
    var id: String,
    var name: String,
    var age: Int,
    var profileImage: Optional<Image>,
    var articleList: List<Article>,
    var followCount: Long
)