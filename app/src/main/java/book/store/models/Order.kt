package book.store.models

import com.google.gson.annotations.SerializedName

data class Order (
    val id:Int,
    @SerializedName("user_id")
    val userId:Int,
    val address: String,
    val city: String,
    val totalPrice: Float,
    val user:User
)
