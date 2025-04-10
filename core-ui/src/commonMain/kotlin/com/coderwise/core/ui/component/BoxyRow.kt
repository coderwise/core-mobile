package com.coderwise.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.utils.CorePreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.max

@Composable
fun BoxyRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        //assert(measurables.size == 2) { "BoxyRow must have 2 child composables" }

        val contentConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(contentConstraints)
        }

        val leftPlaceable = placeables[0]
        val rightPlaceable = placeables[1]
        val isOverflow = leftPlaceable.width + rightPlaceable.width > constraints.maxWidth

        val layoutHeight = if (isOverflow) {
            leftPlaceable.height + rightPlaceable.height
        } else {
            max(leftPlaceable.height, rightPlaceable.height)
        }

        layout(width = constraints.maxWidth, height = layoutHeight) {
            leftPlaceable.placeRelative(0, 0)
            if (isOverflow) {
                rightPlaceable.placeRelative(
                    x = constraints.maxWidth - rightPlaceable.width,
                    y = leftPlaceable.height
                )
            } else {
                rightPlaceable.placeRelative(constraints.maxWidth - rightPlaceable.width, 0)
            }
        }
    }
}

@Preview
@Composable
private fun BoxyRowPreview() {
    CorePreview {
        BoxyRow {
            Text(
                text = "Text 1",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Text 2",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}