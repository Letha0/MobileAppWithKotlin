package book.store.api

import UserRequest
import book.store.api.*
import book.store.models.*
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

    @GET("api/users/{id}")
    fun getUser(
        @Path("id") id: Int
    ): retrofit2.Call<UserResponse>

    @GET("api/auth/me")
    fun getAccount(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<UserResponse>

    @GET("api/users")
    fun getAllUsers(
        @Header("Authorization")  token: String
    ) : retrofit2.Call<List<User>>

    @GET( "api/user/{id}")
    fun getUserToEdit(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ) : retrofit2.Call<User>

    @POST("/api/users")
    fun addUser(
        @Header("Authorization")  token: String,
        @Body create: NewUserRequest
    ): retrofit2.Call<AddUserResponse>

    @DELETE("api/user/{id}")
    fun deleteUser(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<User>

    @GET("/api/authors")
    fun getAllAuthors(
        @Header("Authorization")  token: String
    ) : retrofit2. Call<List<Author>>

    @DELETE("api/author/{id}")
    fun deleteAuthor(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<Author>

    @PATCH("api/author/{id}")
    fun editAuthor(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ) : retrofit2.Call<Author>

    @POST("/api/authors")
    fun addAuthor(
        @Header("Authorization")  token: String,
        @Body add: NewAuthorRequest
    ): retrofit2.Call<AddAuthorResponse>

    @GET ("api/author/{id}")
    fun getAuthor(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<Author>

    @GET("api/genres")
    fun getAllGenres(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Genre>>

    @GET("api/series")
    fun getAllSeries(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Serie>>

    @GET("api/covertype")
    fun getAllCoverTypes(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<CoverType>>

    @GET("api/publhouse")
    fun getAllPublHouses(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<PublishingHouse>>
}

