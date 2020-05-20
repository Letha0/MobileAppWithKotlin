package book.store.api

import book.store.api.*
import book.store.models.Book
import book.store.requests.*
import retrofit2.http.*

interface Api {

    //@FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Body registration: RegisterRequest
    ): retrofit2.Call<RegisterResponse>

    @POST("api/auth/login")
    fun login(
        @Body login: LoginRequest
    ): retrofit2.Call<LoginResponse>

    @POST("api/password/create")
    fun createNewPass(
        @Body create: PasswordResetRequest
    ): retrofit2.Call<PasswordResetResponse>


    @GET("api/password/find/{token}")
    fun findToken(
        @Path("token") token: String
    ): retrofit2.Call<FindTokenToResetPasswordResponse>

    @POST("api/password/reset")
    fun resetPass(
        @Body create: EnterNewPasswordRequest
    ): retrofit2.Call<EnterNewPasswordResponse>

    @GET("api/books")
    fun getBooks(): retrofit2.Call<List<Book>>


}