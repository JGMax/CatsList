package gortea.jgmax.cats.catslist.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter  = true)
data class CatModel(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "url") val url: String
)