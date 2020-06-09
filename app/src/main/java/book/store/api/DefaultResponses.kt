package book.store.api

import android.text.format.DateFormat
import book.store.models.Book
import book.store.models.CoverType
import book.store.models.Order
import book.store.models.User
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


data class UserResponse (val id:Int,
                         val name:String,
                         val surname: String,
                         val email: String,
                         @SerializedName("email_verified_at")
                         val emailVerifiedAt: Date,
                         val password: String,
                         @SerializedName("activation_token")
                         val activationToken: String,
                         val active: Int,
                         @SerializedName("remember_token")
                         val rememberToken: String,
                         @SerializedName("created_at")
                         val createdAt: String,
                         @SerializedName("updated_at")
                         val updatedAt: String,
                         val error: String,
                         val order: Order
)

data class AddUserResponse(
                            val id:Int,
                            val name:String,
                            val surname: String,
                            val email: String,
                            val error:String
)

data class AddAuthorResponse(val id: Int,
                             val name:String,
                             val surname:String,
                             @SerializedName("date_birth")
                             val dateOfBirth: String,
                             @SerializedName("date_death")
                             val dateOfDeath:String?,
                             val description:String
)

data class AddBookResponse(
    val id:Int,
    val title:String,
    val authorId: Int,
    val genreId:Int,
    val seriesId:Int,
    val description: String,
    val price: Float,
    val coverTypeId:Int,
    val publHouseId:Int,
    val release:String,
    val coverImage:String

)

data class SuccessResponse(val status: String)
