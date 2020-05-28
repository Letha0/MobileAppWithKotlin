package book.store.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Opinion (
    val id: Int,
    val title: String,
    val content:String,
    @SerializedName("user_id")
    val userId:Int,
    @SerializedName("book_id")
    val bookId:Int,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date
)