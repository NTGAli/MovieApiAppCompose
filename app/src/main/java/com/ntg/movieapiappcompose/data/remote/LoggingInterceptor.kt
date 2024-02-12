package com.ntg.movieapiappcompose.data.remote

import com.ntg.movieapiapp.util.timber
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor {
            timber("HttpLog: log: http log: $it")
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}