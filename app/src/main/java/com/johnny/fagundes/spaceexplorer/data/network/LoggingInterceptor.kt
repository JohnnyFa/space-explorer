package com.johnny.fagundes.spaceexplorer.data.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log the request type, parameters, and URL
        Timber.d("Request: ${request.method()} ${request.url()}")

        var response = chain.proceed(request)

        // Log the response code
        Timber.d("Response Code: ${response.code()} ${request.url()}")
        Timber.d("Response isSuccessful: ${response.isSuccessful}")
        val responseBody = response.body()
        responseBody?.let {
            val responseBodyString = it.string()
            Timber.d("Response Body: $responseBodyString")
            response = response.newBuilder()
                .body(ResponseBody.create(it.contentType(), responseBodyString.toByteArray()))
                .build()
        }
        Timber.d("Response Body:${response.body()}")

        return response
    }
}