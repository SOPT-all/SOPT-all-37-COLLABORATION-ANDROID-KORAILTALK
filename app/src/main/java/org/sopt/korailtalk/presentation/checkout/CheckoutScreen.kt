package org.sopt.korailtalk.presentation.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sopt.korailtalk.core.common.util.extension.noRippleClickable
import org.sopt.korailtalk.core.common.util.preview.DefaultPreview
import org.sopt.korailtalk.presentation.checkout.view.CheckoutBottomView
import org.sopt.korailtalk.presentation.checkout.view.CheckoutTopView

@Composable
fun CheckoutRoute(
    paddingValues: PaddingValues,
    navigateToHome: () -> Unit,
    navigateUp: () -> Unit
) {
    CheckoutScreen(
        modifier = Modifier.padding(paddingValues),
        onBackClick = navigateUp,
        onCloseClick = navigateToHome
    )
}

@Composable
private fun CheckoutScreen(
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row( //TODO: TopAppBar로 수정 (@kimjw2003)
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "뒤로가기",
                modifier = Modifier.noRippleClickable(
                    onClick = onBackClick
                )
            )
            Text(
                "닫기",
                modifier = Modifier.noRippleClickable(
                    onClick = onCloseClick
                )
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item { // @kimjw2003
                CheckoutTopView() // 상단 ~ 할인쿠폰 적용
            }
            item { // @nahy-512
                CheckoutBottomView() // 국가유공자 할인 ~ 하단
            }
        }

        // TODO: 하단 Fixed 영역 작업 (@nahy-512)
    }
}

@DefaultPreview
@Composable
private fun CheckoutScreenPreview() {
    CheckoutScreen(
        onBackClick = {},
        onCloseClick = {}
    )
}