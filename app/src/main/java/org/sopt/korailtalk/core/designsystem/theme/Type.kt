package org.sopt.korailtalk.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.sopt.korailtalk.R

object PretendardFont {
    val Medium = FontFamily(Font(R.font.pretendard_medium))
    val Regular = FontFamily(Font(R.font.pretendard_regular))
    val SemiBold = FontFamily(Font(R.font.pretendard_semibold))

}


sealed interface TypographyTokens {
    @Immutable
    data class Headline(
        val head1_m_30: TextStyle,
        val head2_m_20: TextStyle,
        val head3_sb_18: TextStyle,
        val head4_m_18: TextStyle,
        val head5_r_18: TextStyle
    )

    @Immutable
    data class Subtitle(
        val sub1_m_17: TextStyle,
        val sub2_r_17: TextStyle,
        val sub3_m_16: TextStyle
    )

    @Immutable
    data class Body(
        val body1_r_16: TextStyle,
        val body2_m_15: TextStyle,
        val body3_r_15: TextStyle,
        val body4_m_14: TextStyle,
        val body5_r_13: TextStyle
    )

    @Immutable
    data class Cap(
        val cap1_m_12: TextStyle,
        val cap2_r_12: TextStyle
    )
}

@Immutable
data class KorailTalkTypography(
    val headline: TypographyTokens.Headline,
    val subtitle: TypographyTokens.Subtitle,
    val body: TypographyTokens.Body,
    val cap: TypographyTokens.Cap
)


val defaultKorailTalkTypography = KorailTalkTypography(
    headline = TypographyTokens.Headline(
        head1_m_30 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 30.sp,
            lineHeight = 39.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        head2_m_20 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 20.sp,
            lineHeight = 26.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        head3_sb_18 = TextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 18.sp,
            lineHeight = 23.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        head4_m_18 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 18.sp,
            lineHeight = 23.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        head5_r_18 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 18.sp,
            lineHeight = 23.sp, //130%
            letterSpacing = (-1.5).sp
        )
    ),
    subtitle = TypographyTokens.Subtitle(
        sub1_m_17 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 17.sp,
            lineHeight = 22.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        sub2_r_17 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 17.sp,
            lineHeight = 22.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        sub3_m_16 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 16.sp,
            lineHeight = 21.sp, //130%
            letterSpacing = (-1.5).sp
        )
    ),
    body = TypographyTokens.Body(
        body1_r_16 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 16.sp,
            lineHeight = 21.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        body2_m_15 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 15.sp,
            lineHeight = 19.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        body3_r_15 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 15.sp,
            lineHeight = 19.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        body4_m_14 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 14.sp,
            lineHeight = 18.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        body5_r_13 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 13.sp,
            lineHeight = 17.sp, //130%
            letterSpacing = (-1.5).sp
        )
    ),
    cap = TypographyTokens.Cap(
        cap1_m_12 = TextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp, //130%
            letterSpacing = (-1.5).sp
        ),
        cap2_r_12 = TextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 12.sp,
            lineHeight = 16.sp, //130%
            letterSpacing = (-1.5).sp
        )
    )
)

val LocalKorailTalkTypographyProvider = staticCompositionLocalOf { defaultKorailTalkTypography }
