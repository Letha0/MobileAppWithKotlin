package book.store.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Author (
    val id: Int,
    val name:String,
    val surname:String,
    @SerializedName("date_birth")
    val dateOfBirth: String,
    @SerializedName("date_death")
    val dateOfDeath:String?,
    val description:String
    )