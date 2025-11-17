package org.sopt.korailtalk.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

//primary Color
val primary_700 = Color(0xFF0C3C60)
val primary_500 = Color(0xFF005799)
val primary_400 = Color(0xFF106EBE)
val primary_300 = Color(0xFF4A91C7)
val primary_200 = Color(0xFFCDE0EE)

//secondary Color
val secondary_p_400 = Color(0xFF7B5DB3)
val secondary_p_200 = Color(0xFFD5D6EA)
val secondary_m_400 = Color(0xFF00C3CE)
val secondary_m_200 = Color(0xFFBBEFF6)

//point
val point_red = Color(0xFFFF2200)
val point_orange = Color(0xFFE45500)

//grayscale
val gray_500 = Color(0xFF666666)
val gray_400 = Color(0xFF757575)
val gray_300 = Color(0xFFC2C2C2)
val gray_200 = Color(0xFFDFDFDF)
val gray_150 = Color(0xFFEAEAEA)
val gray_100 = Color(0xFFF0F0F0)
val gray_50 = Color(0xFFFAFAFA)

//black_and_white
val black_ = Color(0xFF000000)
val white_ = Color(0xFFFFFFFF)


@Immutable
data class KorailTalkColors(
    //primary Color
    val primary700: Color = primary_700,
    val primary500: Color = primary_500,
    val primary400: Color = primary_400,
    val primary300: Color = primary_300,
    val primary200: Color = primary_200,

    //secondary Color
    val secondaryP400: Color = secondary_p_400,
    val secondaryP200: Color = secondary_p_200,
    val secondaryM400: Color = secondary_m_400,
    val secondaryM200: Color = secondary_m_200,


    //point
    val pointRed: Color = point_red,
    val pointOrange: Color = point_orange,

    //grayscale
    val gray500: Color = gray_500,
    val gray400: Color = gray_400,
    val gray300: Color = gray_300,
    val gray200: Color = gray_200,
    val gray150: Color = gray_150,
    val gray100: Color = gray_100,
    val gray50: Color = gray_50,

    //black_and_white
    val black: Color = black_,
    val white: Color = white_
)

val defaultKorailTalkColors = KorailTalkColors(
    //primary Color
    primary700 = primary_700,
    primary500 = primary_500,
    primary400 = primary_400,
    primary300 = primary_300,
    primary200 = primary_200,
    //secondary Color
    secondaryP400 = secondary_p_400,
    secondaryP200 = secondary_p_200,
    secondaryM400 = secondary_m_400,
    secondaryM200 = secondary_m_200,
    //point
    pointRed = point_red,
    pointOrange = point_orange,
    //grayscale
    gray500 = gray_500,
    gray400 = gray_400,
    gray300 = gray_300,
    gray200 = gray_200,
    gray150 = gray_150,
    gray100 = gray_100,
    gray50 = gray_50,
    //black_and_white
    black = black_,
    white = white_
)

val LocalKorailTalkColorsProvider = staticCompositionLocalOf { defaultKorailTalkColors }
