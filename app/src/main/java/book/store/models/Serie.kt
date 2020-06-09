package book.store.models

import com.google.gson.annotations.SerializedName

data class Serie(
    val id:Int,
    val name:String,
    val description:String,
    @SerializedName("author_id")
    val authorId:Int,
    @SerializedName("publishing_house_id")
    val publHouseID: Int,
    val author: Author?,
    val publHouse: PublishingHouse?

)