package book.store.api

import book.store.api.*
import book.store.models.*
import book.store.requests.*
import retrofit2.http.*
import java.util.*

interface Api {

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

    @PATCH("api/user/{id}")
    fun updateUser(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<SuccessResponse>

    @GET("/api/authors")
    fun getAllAuthors(
        @Header("Authorization")  token: String
    ) : retrofit2. Call<List<Author>>

    @DELETE("api/author/{id}")
    fun deleteAuthor(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<String>

    @PATCH("api/author/{id}")
    fun editAuthor(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:AuthorRequest
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

    @GET("api/series")
    fun getAllSeries(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Serie>>

    @POST("/api/books/{author}/{genre}/{series}/{covertype}/{publHouse}")
    fun addBook(
        @Header("Authorization") token: String,
        @Body add: AddBookRequest,
        @Path("author") author: Int,
        @Path("genre") genre: Int,
        @Path("series") series: Int,
        @Path("covertype") covertype: Int,
        @Path("publHouse") publHouse: Int
    ): retrofit2.Call<AddBookResponse>

    @PATCH("/api/books/{book}/{author}/{genre}/{series}/{covertype}/{publHouse}")
    fun editBook(
        @Header("Authorization") token: String,
        @Body add: AddBookRequest,
        @Path("book") book:Int,
        @Path("author") author: Int,
        @Path("genre") genre: Int,
        @Path("series") series: Int,
        @Path("covertype") covertype: Int,
        @Path("publHouse") publHouse: Int
    ): retrofit2.Call<Book>


    @DELETE("api/books/{id}")
    fun deleteBook(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<Book>

    @GET("api/opinion")
    fun getAllOpinions(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Opinion>>

    @DELETE("api/opinion/{id}")
    fun deleteOpinion(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<String>

    @DELETE("api/genres/{id}")
    fun deleteGenre(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<String>

    @PATCH("api/genres/{id}")
    fun editGenre(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:NewGenreRequest
    ) : retrofit2.Call<Genre>

    @POST("/api/genres")
    fun addGenre(
        @Header("Authorization")  token: String,
        @Body add: NewGenreRequest
    ): retrofit2.Call<Genre>

    @GET("api/paymethod")
    fun getAllPayments(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Payment>>

    @DELETE("api/paymethod/{id}")
    fun deletePayment(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<SuccessResponse>

    @POST("/api/paymethod")
    fun addPayment(
        @Header("Authorization")  token: String,
        @Body add: PaymentRequest
    ): retrofit2.Call<SuccessResponse>

    @PATCH("api/paymethod/{id}")
    fun editPayment(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:PaymentRequest
    ) : retrofit2.Call<Payment>

    @GET("api/publhouse")
    fun getAllPublHouses(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<PublishingHouse>>

    @DELETE("api/publhouse/{id}")
    fun deletePublHouse(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<String>

    @POST("/api/publhouse")
    fun addPublHouse(
        @Header("Authorization")  token: String,
        @Body add: PublHouseRequest
    ): retrofit2.Call<SuccessResponse>

    @PATCH("api/publhouse/{id}")
    fun editPublHouse(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:PublHouseRequest
    ) : retrofit2.Call<PublishingHouse>

    @GET("api/genres")
    fun getAllGenres(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Genre>>

    @DELETE("api/series/{id}")
    fun deleteSerie(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<SuccessResponse>

    @POST("/api/series/{author}/{publHouse}")
    fun addSerie(
        @Header("Authorization")  token: String,
        @Body add: SerieRequest,
        @Path("author") author:Int,
        @Path("publHouse") publHouse:Int
    ): retrofit2.Call<Serie>

    @PATCH("api/series/{id}")
    fun editSerie(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:EditSerieRequest
    ) : retrofit2.Call<Serie>

    @GET("api/covertype")
    fun getAllCoverTypes(
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<CoverType>>

    @DELETE("api/covertype/{id}")
    fun deleteCoverType(
        @Header("Authorization")  token: String,
        @Path("id") id: Int
    ): retrofit2.Call<String>

    @POST("/api/covertype")
    fun addCoverType(
        @Header("Authorization")  token: String,
        @Body add: CoverTypeRequest
    ): retrofit2.Call<CoverType>

    @PATCH("api/covertype/{id}")
    fun editCoverType(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:CoverTypeRequest
    ) : retrofit2.Call<CoverType>

    @GET("api/orders")
    fun getAllOrders(
        @Header("Cookie") cookie: String?,
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Order>>

    @GET("api/cart/mobile")
    fun getCart(
        @Header("Cookie") cookie: String?,
        @Header("Authorization")  token: String
    ): retrofit2.Call<Cart<List<Products>>>

    @POST("/api/cart/{book}")
    fun addToCart(
        @Header("Cookie") cookie: String?,
        @Header("Authorization")  token: String,
        @Path("book") book: Int
    ): retrofit2.Call<SuccessResponse>

    @DELETE("api/cart")
    fun deleteCart(
        @Header("Cookie") cookie: String?,
        @Header("Authorization")  token: String
    ): retrofit2.Call<SuccessResponse>

    @POST("/api/orders")
    fun addOrder(
        @Header("Cookie") cookie: String?,
        @Header("Authorization")  token: String,
        @Body add:OrderRequest
    ): retrofit2.Call<SuccessResponse>

    @GET("api/order/see/{id}")
    fun getUserOrders(
        @Path("id") id:Int,
        @Header("Authorization")  token: String
    ): retrofit2.Call<List<Order>>

    @POST("/api/search")
    fun search(
        @Body add:SearchRequest
    ): retrofit2.Call<List<Book>>

    @GET("api/opinions/{id}")
    fun getOpinionsAboutBook(
        @Path("id") id:Int
    ): retrofit2.Call<List<Opinion>>

    @POST("api/opinion/{id}")
    fun addOpinion(
        @Path("id") id:Int,
        @Body add:OpinionRequest
    ): retrofit2.Call<Opinion>

    @PATCH("api/auth/me/{id}")
    fun updateSelfAcc(
        @Header("Authorization")  token: String,
        @Path("id") id: Int,
        @Body add:AccUpdateRequest
    ): retrofit2.Call<SuccessResponse>
}

