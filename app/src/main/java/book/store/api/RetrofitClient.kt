package book.store.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

        private const val BASE_URL: String = "http://bookstore.dizajnstudio.eu/"
       // private const val BASE_URL: String = "http://10.0.2.2:8000/"

        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    private val cookiesInterceptor: MyCookieManager by lazy {
        MyCookieManager()
    }
    private fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(this.cookiesInterceptor)
            .addInterceptor(logger)


        return httpClient.build()
    }

    private val okHttp = OkHttpClient.Builder().addInterceptor(logger).build()


        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader( "Content-Type", "application/json")
                    .addHeader("Set-Cookie","")
                    .method(original.method, original.body)

                val builder = OkHttpClient.Builder()

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()


        var gson = GsonBuilder()
            .setLenient()
            .create()


        val instance: Api by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(provideOkHttpClient())
//                /.client(okHttpClient)
                .build()


            retrofit.create(Api::class.java)

        }


    }
