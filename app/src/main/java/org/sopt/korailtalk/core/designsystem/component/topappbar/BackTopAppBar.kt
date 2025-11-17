package org.sopt.korailtalk.core.designsystem.component.topappbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.R
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme

@Composable
fun BackTopAppBar(
    onBackClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {}
) {
    KorailTalkBasicTopAppBar(
        modifier = modifier,
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable(onClick = onBackClick)
                        .size(44.dp)
                )
                Text(
                    text = title,
                    color = KorailTalkTheme.colors.white,
                    style = KorailTalkTheme.typography.subtitle.sub3M16
                )
            }
        },
        actions = actions
    )
}


@Preview
@Composable
private fun BackTopAppBarPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BackTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onBackClick = {},
            title = "결제",
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                    contentDescription = null,
                    modifier = Modifier.size(44.dp)
                )
            }
        )

        BackTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onBackClick = {},
            title = "열차 조회",
            actions = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_reload),
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