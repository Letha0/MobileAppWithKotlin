package book.store.requests

import book.store.models.*
import com.google.gson.annotations.SerializedName
import java.util.*

data class AddBookRequest (
    val title: String,
    val description: String,
    val release: String,
    val price: Float,
    @SerializedName("cover_image")
    val coverImage: String?

)