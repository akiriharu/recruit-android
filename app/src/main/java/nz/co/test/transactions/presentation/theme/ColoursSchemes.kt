package nz.co.test.transactions.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val LightColourScheme = lightColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryVariantLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val DarkColourScheme = darkColors(
    primary = PrimaryDark,
    primaryVariant = PrimaryVariantDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)


// Custom Light Color Scheme
val customLightColorScheme = CustomColours(
    customDebitColor = RedLight,
    customCreditColor = GreenLight,
)

// Custom Dark Color Scheme
val customDarkColorScheme = CustomColours(
    customDebitColor = RedDark,
    customCreditColor = GreenDark,
)
