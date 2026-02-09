package com.example.cntrgssr.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

private val GREY_100 = Color(0xFF0C0F0F)
private val GREY_70 = Color(0xFF262D2F)
private val GREY_68 = Color(0xFF2D3335)
private val GREY_60 = Color(0xFF383838)
private val GREY_40 = Color(0xFF3F3F3F)
private val GREY_30 = Color(0xFF525251)
private val GREY_25 = Color(0xFF5C5B5B)
private val GREY_24 = Color(0xFF757C7D)
private val GREY_22 = Color(0xFF707778)
private val GREY_20 = Color(0xFFE4E2E1)
private val GREY_15 = Color(0xFFD6D4D3)
private val GREY_10 = Color(0xFFFAF7F7)
private val GREY_5 = Color(0xFFF9F9F9)

private val GREY_SECONDARY_100 = Color(0xFF1F2020)
private val GREY_SECONDARY_70 = Color(0xFF3B3B3B)
private val GREY_SECONDARY_45 = Color(0xFF515252)
private val GREY_SECONDARY_44 = Color(0xFF5A6061)
private val GREY_SECONDARY_42 = Color(0xFF5B5B5B)
private val GREY_SECONDARY_40 = Color(0xFF5C5C5C)
private val GREY_SECONDARY_35 = Color(0xFF9C9D9D)
private val GREY_SECONDARY_22 = Color(0xFFD6D4D4)
private val GREY_SECONDARY_20 = Color(0xFFE4E2E2)
private val GREY_SECONDARY_10 = Color(0xFFFAF8F8)
private val GREY_SECONDARY_5 = Color(0xFFEBEEEF)

private val SURFACE_LOW = Color(0xFFF2F4F4)
private val SURFACE_HIGH = Color(0xFFE4E9EA)
private val SURFACE_HIGHEST = Color(0xFFDEE3E5)
private val SURFACE_DIM_LIGHT = Color(0xFFD4DBDD)

private val SURFACE_LOW_DARK = Color(0xFF111414)
private val SURFACE_DARK = Color(0xFF161A1B)
private val SURFACE_HIGH_DARK = Color(0xFF1B2021)
private val SURFACE_HIGHEST_DARK = Color(0xFF212728)
private val OUTLINE_DARK = Color(0xFF43494A)

private val RED_90 = Color(0xFF490005)
private val RED_80 = Color(0xFF6C050E)
private val RED_70 = Color(0xFF881D1F)
private val RED_35 = Color(0xFFFF756F)
private val RED_32 = Color(0xFFFC736D)
private val RED_30 = Color(0xFFF9706B)
private val RED_20 = Color(0xFFFF9993)
private val RED_10 = Color(0xFFFFF7F6)

private val GREEN_80 = Color(0xFF205019)
private val GREEN_75 = Color(0xFF33632A)
private val GREEN_70 = Color(0xFF3D6E33)
private val GREEN_25 = Color(0xFFB6EFA5)
private val GREEN_20 = Color(0xFFC4FDB2)
private val GREEN_18 = Color(0xFFC6FFB4)
private val GREEN_10 = Color(0xFFEBFFE0)

val LightColorScheme = ColorScheme(
    primary = GREY_60,
    onPrimary = GREY_10,
    primaryContainer = GREY_20,
    onPrimaryContainer = GREY_30,

    primaryFixed = GREY_20,
    primaryFixedDim = GREY_15,
    onPrimaryFixed = GREY_40,
    onPrimaryFixedVariant = GREY_25,

    secondary = GREY_SECONDARY_40,
    onSecondary = GREY_SECONDARY_10,
    secondaryContainer = GREY_SECONDARY_20,
    onSecondaryContainer = GREY_SECONDARY_45,

    secondaryFixed = GREY_SECONDARY_20,
    secondaryFixedDim = GREY_SECONDARY_22,
    onSecondaryFixed = GREY_40,
    onSecondaryFixedVariant = GREY_SECONDARY_42,

    tertiary = GREEN_18,
    onTertiary = GREEN_10,
    tertiaryContainer = GREEN_20,
    onTertiaryContainer = GREEN_75,

    tertiaryFixed = GREEN_20,
    tertiaryFixedDim = GREEN_25,
    onTertiaryFixed = GREEN_80,
    onTertiaryFixedVariant = GREEN_70,

    error = RED_35,
    onError = RED_10,
    errorContainer = RED_30,
    onErrorContainer = RED_80,

    background = GREY_5,
    onBackground = GREY_68,

    surface = GREY_5,
    onSurface = GREY_68,
    surfaceVariant = GREY_SECONDARY_5,
    onSurfaceVariant = GREY_SECONDARY_44,
    surfaceTint = GREY_60,

    inverseSurface = GREY_100,
    inverseOnSurface = GREY_SECONDARY_35,
    inversePrimary = Color.White,

    outline = GREY_24,
    outlineVariant = GREY_20,
    scrim = Color.Black,

    surfaceBright = GREY_5,
    surfaceDim = SURFACE_DIM_LIGHT,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = SURFACE_LOW,
    surfaceContainer = GREY_SECONDARY_5,
    surfaceContainerHigh = SURFACE_HIGH,
    surfaceContainerHighest = SURFACE_HIGHEST,
)

val DarkColorScheme = ColorScheme(
    primary = GREY_20,
    onPrimary = GREY_40,
    primaryContainer = GREY_60,
    onPrimaryContainer = GREY_15,

    primaryFixed = GREY_20,
    primaryFixedDim = GREY_15,
    onPrimaryFixed = GREY_40,
    onPrimaryFixedVariant = GREY_25,

    secondary = GREY_SECONDARY_35,
    onSecondary = GREY_SECONDARY_100,
    secondaryContainer = GREY_SECONDARY_70,
    onSecondaryContainer = GREY_15,

    secondaryFixed = GREY_SECONDARY_20,
    secondaryFixedDim = GREY_SECONDARY_22,
    onSecondaryFixed = GREY_40,
    onSecondaryFixedVariant = GREY_SECONDARY_42,

    tertiary = GREEN_10,
    onTertiary = GREEN_70,
    tertiaryContainer = GREEN_20,
    onTertiaryContainer = GREEN_75,

    tertiaryFixed = GREEN_20,
    tertiaryFixedDim = GREEN_25,
    onTertiaryFixed = GREEN_80,
    onTertiaryFixedVariant = GREEN_70,

    error = RED_32,
    onError = RED_90,
    errorContainer = RED_70,
    onErrorContainer = RED_20,

    background = GREY_100,
    onBackground = GREY_5,

    surface = GREY_100,
    onSurface = GREY_5,
    surfaceBright = GREY_70,
    surfaceDim = GREY_100,
    surfaceVariant = GREY_70,
    onSurfaceVariant = GREY_SECONDARY_35,
    surfaceTint = GREY_20,

    outline = GREY_22,
    outlineVariant = OUTLINE_DARK,
    scrim = Color.Black,

    surfaceContainerLowest = Color.Black,
    surfaceContainerLow = SURFACE_LOW_DARK,
    surfaceContainer = SURFACE_DARK,
    surfaceContainerHigh = SURFACE_HIGH_DARK,
    surfaceContainerHighest = SURFACE_HIGHEST_DARK,

    inverseSurface = GREY_5,
    inverseOnSurface = Color(0xFF545556),
    inversePrimary = Color(0xFF5F5F5F),
)