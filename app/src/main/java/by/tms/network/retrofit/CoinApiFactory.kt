package by.tms.network.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

const val BASEURL = "https://pro-api.coinmarketcap.com/v1/"

object CoinApiFactory {
    private val KEY_NAME = "X-CMC_PRO_API_KEY"
    private val KEY_VALUE = "eb58ea2f-1d43-405f-bb97-ef01946db5fb"

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader(KEY_NAME, KEY_VALUE)
                    .build()
                return chain.proceed(request)
            }
        })

    fun getRetrofit(): CoinAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()
        return retrofit.create()
    }
}