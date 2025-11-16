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
val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)


@Immutable
data class KorailTalkColors(
    //primary Color
    val Primary_700: Color = primary_700,
    val Primary_500: Color = primary_500,
    val Primary_400: Color = primary_400,
    val Primary_300: Color = primary_300,
    val Primary_200: Color = primary_200,

   //secondary Color
    val Secondary_p_400: Color = secondary_p_400,
    val Secondary_p_200: Color = secondary_p_200,
    val Secondary_m_400: Color = secondary_m_400,
    val Secondary_m_200: Color = secondary_m_200,


   //point
    val Point_red: Color = point_red,
    val Point_orange: Color = point_orange,

   //grayscale
    val Gray_500: Color = gray_500,
    val Gray_400: Color = gray_400,
    val Gray_300: Color = gray_300,
    val Gray_200: Color = gray_200,
    val Gray_150: Color = gray_150,
    val Gray_100: Color = gray_100,
    val Gray_50: Color = gray_50,

   //black_and_white
    val Black: Color = black,
    val White: Color = white
)
val defaultKorailTalkColors=KorailTalkColors(
    //primary Color
    Primary_700=primary_700,
    Primary_500=primary_500,
    Primary_400=primary_400,
    Primary_300=primary_300,
    Primary_200=primary_200,
    //secondary Color
    Secondary_p_400=secondary_p_400,
    Secondary_p_200=secondary_p_200,
    Secondary_m_400=secondary_m_400,
    Secondary_m_200=secondary_m_200,
    //point
    Point_red=point_red,
    Point_orange=point_orange,
    //grayscale
    Gray_500=gray_500,
    Gray_400=gray_400,
    Gray_300=gray_300,
    Gray_200=gray_200,
    Gray_150=gray_150,
    Gray_100=gray_100,
    Gray_50=gray_50,
    //black_and_white
    Black=black,
    White=white
)

val LocalKorailTalkColorsProvider = staticCompositionLocalOf { defaultKorailTalkColors }
