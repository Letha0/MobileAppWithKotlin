package book.store.api

import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import java.io.ObjectOutput
import java.util.*

data class RegisterResponse(val status: String)

data class LoginResponse(val token: String)

data class PasswordResetResponse(val message: String)

data class FindTokenToResetPasswordResponse(val message: String)

data class EnterNewPasswordResponse( val id: Int,
                                     val email: String,
                                     val token: String,
                                     @SerializedName("created_at")
                                     val createdAt: String,
                                     @SerializedName("updated_at")
                                     val updatedAt: String
)