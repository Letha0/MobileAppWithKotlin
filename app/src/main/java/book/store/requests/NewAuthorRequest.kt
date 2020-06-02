package book.store.requests

import com.google.gson.annotations.SerializedName

data class NewAuthorRequest (
    val name:String,
    val surname:String,
    @SerializedName("date_birth")
    val dateOfBirth: String,
    @SerializedName("date_death")
    val dateOfDeath:String?,
    val description:String
)