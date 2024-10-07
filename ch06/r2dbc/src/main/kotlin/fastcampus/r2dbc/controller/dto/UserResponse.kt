package fastcampus.r2dbc.controller.dto

import java.util.*

data class UserResponse(
    var id: String,
    var age: Int,
    var followCount: Long,
    var image: Optional<ProfileImageResponse>,
    var name: String
)
