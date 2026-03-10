package com.coderwise.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Density

@Composable
fun Int.pxToDp(density: Density) = with(density) { this@pxToDp.toDp() }
