package nz.co.test.transactions.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf


@Immutable
data class CustomColours(
    val customDebitColor: Color = Color.Unspecified,
    val customCreditColor: Color = Color.Unspecified,
)

val LocalCustomColours = staticCompositionLocalOf { CustomColours() }