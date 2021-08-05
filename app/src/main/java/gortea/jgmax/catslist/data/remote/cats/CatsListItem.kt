package gortea.jgmax.catslist.data.remote.cats

import gortea.jgmax.catslist.data.remote.cats.additional.CatsBreedsItem
import gortea.jgmax.catslist.data.remote.cats.additional.CatsCategory

data class CatsListItem(
    val breeds: List<CatsBreedsItem>,
    val categories: List<CatsCategory>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
