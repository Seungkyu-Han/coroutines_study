package fastcampus.r2dbc.controller.dto

import lombok.Data

data class ProfileImageResponse(
    var id: String,
    var name: String,
    var url: String,
)