package nz.co.test.transactions.data.di.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

/**
 * Custom Moshi adapter to convert [String] to [BigDecimal]
 */
class BigDecimalStringAdapter {
    @ToJson
    fun toJson(bigDecimal: BigDecimal) = bigDecimal.toString()

    @FromJson
    fun fromJson(value: String) = BigDecimal(value)
}