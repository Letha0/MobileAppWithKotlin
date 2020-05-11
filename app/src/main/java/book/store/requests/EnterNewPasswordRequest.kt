package book.store.requests

import com.google.gson.annotations.SerializedName

data class EnterNewPasswordRequest (
    val email: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    val token: String
)