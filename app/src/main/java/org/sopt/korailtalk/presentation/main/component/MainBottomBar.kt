package org.sopt.korailtalk.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.core.designsystem.theme.KORAILTALKTheme
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.presentation.main.MainTab

@Composable
internal fun MainBottomBar(
    visible: Boolean,
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .padding(top = 1.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .dropShadow(
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    shadow = Shadow(
                        radius = 12.dp,
                        color = Color.Black,
                        alpha = 0.05f,
                        offset = DpOffset(x = 0.dp, y = (-0.5).dp),
                    )
                )
                .background(KorailTalkTheme.colors.white)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                tabs.forEach { tab ->
                    key(tab.route) {
                        MainBottomBarItem(
                            tab = tab,
                            selected = (tab == currentTab),
                            onClick = { onTabSelected(tab) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .noRippleClickable(onClick = onClick)
            .weight(1f)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(tab.iconResId),
            modifier = Modifier.size(28.dp),
            contentDescription = tab.label,
            tint = if (selected) KorailTalkTheme.colors.black else KorailTalkTheme.colors.gray200
        )
        Text(
            text = tab.label,
            color = if (selected) KorailTalkTheme.colors.black else KorailTalkTheme.colors.gray200,
            style = KorailTalkTheme.typography.cap.cap1M12
        )
    }
}

@DefaultPreview
@Composable
private fun MainBottomBarPreview() {
    KORAILTALKTheme {
        Column(modifier = Modifier) {
            MainBottomBar(
                visible = true,
                tabs = MainTab.entries.toPersistentList(),
                currentTab = MainTab.HOME,
                onTabSelected = {}
            )
        }
    }
}