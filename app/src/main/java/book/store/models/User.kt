package book.store.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (val id:Int,
                 val name:String,
                 val surname: String,
                 val email: String,
                 @SerializedName("email_verified_at")
                 val emailVerifiedAt: Date,
                 val password: String,
                 @SerializedName("activation_token")
                 val activationToken: String,
                 val active: Boolean,
                 @SerializedName("remember_token")
                 val rememberToken: String,
                 @SerializedName("created_at")
                 val createdAt: Date,
                 @SerializedName("updated_at")
                 val updatedAt: Date


)

data class userResp(val user: List<User>,
val error:String
)