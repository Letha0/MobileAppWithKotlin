package book.store.api

import book.store.models.RegisterResponse
import book.store.requests.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    //@FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Body registration: RegisterRequest
    ): retrofit2.Call<RegisterResponse>

}