package fastcampus.r2dbc.service.response

import lombok.Data

@Data
data class ImageResponse(
    var id: String?,
    var name: String?,
    var url: String?
)
