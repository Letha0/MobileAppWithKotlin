package book.store.models

import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName

data class PasswordReset (
    val id: Int,
    val email: String,
    val token: String,
    @SerializedName("created_at")
    val createdAt: DateFormat,
    @SerializedName("updated_at")
    val updatedAt: DateFormat


)