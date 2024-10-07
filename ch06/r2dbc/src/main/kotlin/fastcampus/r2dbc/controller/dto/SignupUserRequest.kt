package fastcampus.r2dbc.controller.dto

data class SignupUserRequest(
    var name: String,
    val age: Int,
    val password: String,
    val profileImageId: String
)
