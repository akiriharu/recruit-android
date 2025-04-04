package nz.co.test.transactions.data.di.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Custom Moshi adapter for [LocalDateTime]
 */
class LocalDateTimeAdapter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @FromJson
    fun fromJson(json: String) = LocalDateTime.parse(json, formatter)

    @ToJson
    fun toJson(localDateTime: LocalDateTime) = localDateTime.format(formatter)
}