package nz.co.test.transactions.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Application default theme
 */
@Composable
fun TransactionAppTheme(
    content: @Composable () -> Unit,
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    val colours = if (isSystemInDarkTheme) DarkColourScheme else LightColourScheme
    val customColours = if (isSystemInDarkTheme) customDarkColorScheme else customLightColorScheme

    CompositionLocalProvider(
        LocalCustomColours provides customColours
    ) {
        MaterialTheme(
            colors = colours,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content,
        )
    }
}