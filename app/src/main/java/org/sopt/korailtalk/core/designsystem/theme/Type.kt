package org.sopt.korailtalk.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.sopt.korailtalk.R

object PretendardFont {
    val Medium = FontFamily(Font(R.font.pretendard_medium))
    val Regular = FontFamily(Font(R.font.pretendard_regular))
    val SemiBold = FontFamily(Font(R.font.pretendard_semibold))

}

private fun KorailTalkTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    lineHeight: TextUnit = 1.3.em,
    letterSpacing: TextUnit = (-1.5).sp
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

sealed interface TypographyTokens {
    @Immutable
    data class Headline(
        val head1M30: TextStyle,
        val head2M20: TextStyle,
        val head3Sb18: TextStyle,
        val head4M18: TextStyle,
        val head5R18: TextStyle
    )

    @Immutable
    data class Subtitle(
        val sub1M17: TextStyle,
        val sub2R17: TextStyle,
        val sub3M16: TextStyle
    )

    @Immutable
    data class Body(
        val body1R16: TextStyle,
        val body2M15: TextStyle,
        val body3R15: TextStyle,
        val body4M14: TextStyle,
        val body4R14: TextStyle,
        val body5R13: TextStyle
    )

    @Immutable
    data class Cap(
        val cap1M12: TextStyle,
        val cap2R12: TextStyle
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
        head1M30 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 30.sp
        ),
        head2M20 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 20.sp
        ),

        head3Sb18 = KorailTalkTextStyle(
            fontFamily = PretendardFont.SemiBold,
            fontSize = 18.sp
        ),

        head4M18 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 18.sp
        ),
        head5R18 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 18.sp
        )
    ),
    subtitle = TypographyTokens.Subtitle(
        sub1M17 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 17.sp
        ),
        sub2R17 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 17.sp
        ),

        sub3M16 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 16.sp
        ),
    ),
    body = TypographyTokens.Body(
        body1R16 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 16.sp
        ),

        body2M15 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 15.sp
        ),
        body3R15 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 15.sp
        ),
        body4M14 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 14.sp
        ),
        body4R14 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 14.sp
        ),
        body5R13 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 13.sp
        ),
    ),
    cap = TypographyTokens.Cap(
        cap1M12 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 12.sp
        ),
        cap2R12 = KorailTalkTextStyle(
            fontFamily = PretendardFont.Regular,
            fontSize = 12.sp
        )
    )
)

val LocalKorailTalkTypographyProvider = staticCompositionLocalOf { defaultKorailTalkTypography }
