package gortea.jgmax.catslist.data.remote.cats.additional

import com.google.gson.annotations.SerializedName

data class CatsBreedsItem(
    val weight: CatsWeightData,
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    @SerializedName("country_codes")
    val countryCodes: String,
    @SerializedName("country_code")
    val countryCode: String,
    val description: String,
    @SerializedName("life_span")
    val lifeSpan: String,
    val indoor: Int,
    val lap: Int,
    @SerializedName("alt_names")
    val alternativeNames: String,
    val adaptability: Int,
    @SerializedName("child_friendly")
    val childFriendly: Int,
    @SerializedName("dog_friendly")
    val dogFriendly: Int,
    @SerializedName("energy_level")
    val energyLevel: Int,
    val grooming: Int,
    @SerializedName("health_issues")
    val healthIssues: Int,
    val intelligence: Int,
    @SerializedName("shedding_level")
    val sheddingLevel: Int,
    @SerializedName("social_needs")
    val socialNeeds: Int,
    @SerializedName("stranger_friendly")
    val strangerFriendly: Int,
    val vocalisation: Int,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    @SerializedName("suppressed_tail")
    val suppressedTail: Int,
    @SerializedName("short_legs")
    val shortLegs: Int,
    @SerializedName("wikipedia_url")
    val wikipediaUrl: String,
    val hypoallergenic: Int,
    @SerializedName("reference_image_id")
    val referenceImageId: String
)