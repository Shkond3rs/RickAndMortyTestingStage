package shkond3rs.rickandmorty.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkHeaderLightBlue,
    onPrimary = DarkHeaderBlue,
    primaryContainer = DarkCardBG,
    onPrimaryContainer = DarkTextPrimary,
    outline = DarkBorderColor,

    background = DarkBG,
    secondary = DarkMale,
    onSecondary = DarkFemale,
    tertiary = AliveColor,
    onTertiary = DeadColor
)

private val LightColorScheme = lightColorScheme(
    primary = LightHeaderLightBlue,
    onPrimary = LightHeaderBlue,
    primaryContainer = LightCardBG,
    onPrimaryContainer = LightTextPrimary,
    outline = LightBorderColor,

    background = LightBG,
    secondary = LightMale,
    onSecondary = LightFemale,
    tertiary = AliveColor,
    onTertiary = DeadColor
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}