package com.sepanta.controlkit.inboxviewkit.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LocalMarginDimensions = compositionLocalOf { MarginDimensions() }
val LocalSizeDimensions = compositionLocalOf { SizeDimensions() }
val LocalFontSizeDimensions = compositionLocalOf { FontSizeDimensions() }

data class MarginDimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 2.dp,
    val spaceXExtraSmall: Dp = 4.dp,
    val spaceXXExtraSmall: Dp = 6.dp,
    val spaceSmall: Dp = 10.dp,
    val spaceXSmall: Dp = 14.dp,
    val spaceXXSmall: Dp = 16.dp,
    val spaceMedium: Dp = 20.dp,
    val spaceXMedium: Dp = 24.dp,
    val spaceXXMedium: Dp = 32.dp,
    val spaceLarge: Dp = 45.dp,
    val spaceXLarge: Dp = 64.dp,
    val spaceExtraLarge: Dp = 72.dp,
    val spaceXExtraLarge: Dp = 86.dp,
    val spaceXXExtraLarge: Dp = 126.dp,
    val spaceXXLarge: Dp = 145.dp,
    val spaceXXXLarge: Dp = 256.dp,
)
data class SizeDimensions(
    val default: Dp = 0.dp,
    val size1: Dp = 1.dp,
    val size2: Dp = 2.dp,
    val size3: Dp = 3.dp,
    val size4: Dp = 4.dp,
    val size10: Dp = 10.dp,
    val size12: Dp = 12.dp,
    val size14: Dp = 14.dp,
    val size15: Dp = 15.dp,
    val size16: Dp = 16.dp,
    val size17: Dp = 17.dp,
    val size20: Dp = 20.dp,
    val size22: Dp = 22.dp,
    val size24: Dp = 24.dp,
    val size26: Dp = 26.dp,
    val size28: Dp = 28.dp,
    val size30: Dp = 30.dp,
    val size35: Dp = 35.dp,
    val size40: Dp = 40.dp,
    val size42: Dp = 42.dp,
    val size45: Dp = 45.dp,
    val size52: Dp = 52.dp,
    val size58: Dp = 58.dp,
    val size56: Dp = 56.dp,
    val size60: Dp = 60.dp,
    val size70: Dp = 70.dp,
    val size80: Dp = 80.dp,
    val size82: Dp = 82.dp,
    val size86: Dp = 86.dp,
    val size92: Dp = 92.dp,
    val size96: Dp = 96.dp,
    val size100: Dp = 100.dp,
    val size110: Dp = 110.dp,
    val size120: Dp = 120.dp,
    val size125: Dp = 125.dp,
    val size150: Dp = 150.dp,
    val size160: Dp = 160.dp,
    val size170: Dp = 170.dp,
    val size182: Dp = 182.dp,
    val size190: Dp = 190.dp,
    val size200: Dp = 200.dp,
    val size250: Dp = 250.dp,
    val size280: Dp = 280.dp,
    val size285: Dp = 285.dp,
    val size294: Dp = 294.dp,
    val size340: Dp = 340.dp,
    val size380: Dp = 380.dp,
    val size480: Dp = 480.dp,
    val size520: Dp = 520.dp,

    )
data class FontSizeDimensions(
    val size0: TextUnit = 0.sp,
    val size15: TextUnit = 15.sp,
    val size16: TextUnit = 16.sp,
    val size18: TextUnit = 18.sp,
    val size20: TextUnit = 20.sp,
    val size24: TextUnit = 24.sp,
    val size28: TextUnit = 28.sp,
    val size32: TextUnit = 32.sp,

    )