package nz.co.test.transactions.presentation.model

import androidx.compose.ui.graphics.Color

/**
 * UI model for a transaction.
 *
 * @param summary The summary of the transaction.
 * @param transactionDate The date of the transaction.
 * @param amount The amount of the transaction.
 * @param amountType The type of the amount to define colour (green for credit and red for debit)
 * @param gst The GST of the transaction.
 */
data class UITransaction (
    val id: Int,
    val summary: String,
    val transactionDate: String,
    val amount: String,
    val amountType: AmountType,
    val gst: String,
)