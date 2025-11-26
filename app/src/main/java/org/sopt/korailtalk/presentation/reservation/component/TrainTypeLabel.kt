package org.sopt.korailtalk.presentation.reservation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.korailtalk.core.designsystem.theme.LocalKorailTalkColorsProvider
import org.sopt.korailtalk.core.designsystem.theme.LocalKorailTalkTypographyProvider
import org.sopt.korailtalk.domain.type.TrainType


@Composable
fun TrainTypeLabel(
    trainType: TrainType,
    isEnabled: Boolean = true
) {
    val colors = LocalKorailTalkColorsProvider.current
    val typography = LocalKorailTalkTypographyProvider.current

    val backgroundColor = if (isEnabled) {
        trainType.enabledColor
    } else {
        colors.gray200
    }

    Box(
        modifier = Modifier
            .height(26.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = trainType.displayName,
            style = typography.body.body4M14,
            color = colors.white,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrainTypeLabelPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TrainTypeLabel(TrainType.KTX, isEnabled = true)
            TrainTypeLabel(TrainType.SRT, isEnabled = true)
            TrainTypeLabel(TrainType.ITX_SAEMAEUL, isEnabled = true)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TrainTypeLabel(TrainType.MUGUNGHWA, isEnabled = false)
            TrainTypeLabel(TrainType.ITX_MAEUM, isEnabled = false)
        }
    }
}