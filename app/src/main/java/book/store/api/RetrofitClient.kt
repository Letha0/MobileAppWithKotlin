package book.store.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    object RetrofitClient {

        private const val BASE_URL: String = "http://10.0.2.2:8000/"

        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


        private val okHttp = OkHttpClient.Builder().addInterceptor(logger)


        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader( "Content-Type", "application/json")
                    .method(original.method, original.body)

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()



        val instance: Api by lazy{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp.build())
                .build()


            retrofit.create(Api::class.java)

        }


    }
