package book.store.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    val name: String,
    val surname: String
)