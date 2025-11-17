package org.sopt.korailtalk.core.designsystem.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun KorailTalkBasicTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    backgroundColor: Color = KorailTalkTheme.colors.primary700
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 4.dp)
            .height(56.dp)
    ) {
        Row( // left content
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationIcon()
        }

        Row( // right icons
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }
    }
}

@Preview
@Composable
private fun KorailTalkBasicTopAppBarPreview() {
    KORAILTALKTheme {
        KorailTalkBasicTopAppBar(
            navigationIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.img_korail_logo),
                    contentDescription = null,
                )
            },
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_translate),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_hamburger),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            }
        )
    }
}
