package com.coderwise.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.coderwise.core.ui.location.LocationContent
import com.coderwise.core.ui.location.LocationUiState
import com.coderwise.core.ui.theme.ScreenPreview

@Preview
@Composable
fun SamplePreview() {
}

@Preview
@Composable
fun LocationContentPreview() {
    ScreenPreview {
        LocationContent(
            LocationUiState(
                gpsMessage = null,
                minTime = "1",
                minDistance = "2"
            )
        ) {}
    }
}
