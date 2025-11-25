package org.sopt.korailtalk.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import org.sopt.korailtalk.core.designsystem.theme.KorailTalkTheme
import org.sopt.korailtalk.domain.model.GridItemData
import org.sopt.korailtalk.domain.model.items


@Composable
fun EtcGridCards(
    items: ImmutableList<GridItemData>,
    modifier: Modifier= Modifier
) {
    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = KorailTalkTheme.colors.gray100,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .background(
                color = KorailTalkTheme.colors.white,
                shape = RoundedCornerShape(size = 10.dp)
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),

        ) {
        LazyVerticalGrid(
            modifier = Modifier.background(KorailTalkTheme.colors.white),
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            items(items) { item ->
                GridItemView(item = item)
            }
        }

    }
}

// 개별 아이콘 항목 컴포저블
@Composable
fun GridItemView(item: GridItemData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = item.iconId),
            contentDescription = item.title,
            modifier = Modifier
                .size(24.dp),
            tint = KorailTalkTheme.colors.primary400
        )

        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = item.title,
            style = KorailTalkTheme.typography.body.body3R15,
            color = KorailTalkTheme.colors.black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun GridPreview() {
    val previewItems = items.toPersistentList()
    EtcGridCards(items = previewItems)
}
