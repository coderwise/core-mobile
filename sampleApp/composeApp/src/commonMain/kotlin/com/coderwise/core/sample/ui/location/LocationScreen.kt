package com.coderwise.core.sample.ui.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.component.CoreOutlinedDropdown
import com.coderwise.core.location.GpsMessage
import com.coderwise.core.location.LocationService
import com.coderwise.core.ui.component.BoxyRow
import com.coderwise.core.ui.component.CoreProgressButton
import com.coderwise.core.sample.ui.theme.ScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LocationContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
internal fun LocationContent(
    uiState: LocationUiState,
    dispatch: (LocationAction) -> Unit
) {
    Column {
        ServiceControllerCard(
            minTime = uiState.minTime,
            minDistance = uiState.minDistance,
            status = uiState.locationServiceStatus,
            dispatch = dispatch
        )

        Text(
            text = "Status ${uiState.locationServiceStatus?.name}",
            modifier = Modifier.padding(16.dp)
        )
        uiState.gpsMessage?.let {
            LastLocation(uiState.gpsMessage)
        }
    }
}

@Composable
fun LastLocation(
    lastLocation: GpsMessage
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Timestamp")
            Text(text = lastLocation.timestamp.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Lat/Lon")
            Text(text = lastLocation.latLon.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Horizontal Accuracy")
            Text(text = lastLocation.horizontalAccuracy.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Altitude")
            Text(text = lastLocation.altitude.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Vertical Accuracy")
            Text(text = lastLocation.verticalAccuracy.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Speed")
            Text(text = lastLocation.speed.toString())
        }
        BoxyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Speed Accuracy")
            Text(text = lastLocation.speedAccuracyMps.toString())
        }
    }
}

@Composable
fun ServiceControllerCard(
    minTime: String,
    minDistance: String,
    status: LocationService.Status?,
    dispatch: (LocationAction) -> Unit
) {
    Card(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            CoreOutlinedDropdown(
                selectedOption = minTime,
                onOptionSelected = { dispatch(LocationAction.OnMinTimeChanged(it)) },
                options = listOf("1", "5", "10"),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = "Min Time"
            )
            OutlinedTextField(
                value = minDistance,
                onValueChange = { dispatch(LocationAction.OnMinDistanceChanged(it)) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                label = { Text("Min Distance") }
            )
            Row {
                CoreProgressButton(
                    text = "Configure",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.5f)
                ) {
                    dispatch(LocationAction.OnConfigureClicked)
                }
                CoreProgressButton(
                    text = "Start",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.5f),
                    enabled = status == LocationService.Status.STOPPED,
                    isProgress = status == LocationService.Status.STARTING
                ) {
                    dispatch(LocationAction.OnStartClicked)
                }
                CoreProgressButton(
                    text = "Stop",
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.5f),
                    enabled = status == LocationService.Status.STARTED,
                    isProgress = status == LocationService.Status.STOPPING
                ) {
                    dispatch(LocationAction.OnStopClicked)
                }
            }
        }
    }
}

@Preview
@Composable
fun LocationPreview() {
    ScreenPreview {
        LocationContent(
            LocationUiState()
        ) {}
    }
}
