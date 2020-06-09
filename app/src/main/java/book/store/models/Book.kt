package book.store.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Book (
    val id: Int,
    val title: String,
    @SerializedName("author_id")
    val authorId: Int,
    @SerializedName("genre_id")
    val genreId: Int?,
    val description: String,
    @SerializedName("series_id")
    val seriesId: Int,
    val release: String,
    @SerializedName("cover_type_id")
    val coverTypeId: Int,
    @SerializedName("publishing_house_id")
    val publishingHouseId: Int?,
    val price: Float,
    @SerializedName("cover_image")
    val coverImage: String?,
    //var coverImage : List<CoverImage> = arrayListOf(),
   // @SerializedName("order_id")
    //val orderId: Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
   // val photos: String
    val opinions: List<Opinion>,
    val author: Author,
    val genre: Genre?,
    val serie: Serie,
    val covertype: CoverType,
    @SerializedName("publ_house")
    val publHouse: PublishingHouse?

)