package gortea.jgmax.catslist.data.remote.cats.model

import gortea.jgmax.catslist.data.remote.cats.model.additional.CatsBreedsItem
import gortea.jgmax.catslist.data.remote.cats.model.additional.CatsCategory

data class CatsListItem(
    val breeds: List<CatsBreedsItem>,
    val categories: List<CatsCategory>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
