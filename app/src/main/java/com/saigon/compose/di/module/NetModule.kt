package com.saigon.compose.di.module

import android.annotation.SuppressLint
import com.saigon.compose.data.network.ServiceAPI
import com.saigon.compose.di.module.NetworkModuleConstants.HOST_URL
import com.saigon.compose.di.module.NetworkModuleConstants.KEY_AUTHORIZATION
import com.saigon.compose.di.module.NetworkModuleConstants.TIME_OUT
import com.saigon.compose.di.module.NetworkModuleConstants.TOKEN
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val networkModule = module {
    single { okHttpClient() }
    single { retrofit(get()) }
    single { providesAPI(get()) }
}

private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

private fun okHttpClient(): OkHttpClient {
    val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        )
        .build()

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    })

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .addInterceptor(getHttpLoggingInterceptor())
        .addInterceptor { chain ->
            val request = chain.request()
            val newBuilder: Request.Builder = request.newBuilder()
                .header(KEY_AUTHORIZATION, "Bearer $TOKEN")
            chain.proceed(newBuilder.build())
        }
        .connectionSpecs(Collections.singletonList(spec))
        .retryOnConnectionFailure(true)
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .build()
}

private fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    builder.baseUrl(HOST_URL)
    return builder.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun providesAPI(retrofit: Retrofit): ServiceAPI = retrofit.create(ServiceAPI::class.java)

private object NetworkModuleConstants {
    const val TIME_OUT = 30000
    const val KEY_AUTHORIZATION = "Authorization"
    const val HOST_URL = "https://api.stripe.com"
    const val TOKEN =
        "sk_test_51LQ1BkErmCZ0gli9G05bvptmofxjz8mG3gTNEAjY6CKHT2wYkCGXEI6zGacE3axxueL4nbGmGCnHJZLJNvPrJLvf00NvJHMM8i"
}
