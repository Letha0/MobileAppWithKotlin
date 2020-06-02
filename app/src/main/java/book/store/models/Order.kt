package book.store.models

import com.google.gson.annotations.SerializedName

data class Order (
    @SerializedName("user_id")
    val userId:Int,
    val address: String,
    val city: String,
    val totalPrice: Float
)
