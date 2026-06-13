package com.alexit.justrecipes.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.coroutines.launch

enum class Direction { Vertical, Horizontal }

@Composable
fun BoxScope.CustomScrollBar(
    state: LazyListState,
    direction: Direction,
) {
    val thickness: Dp = JustRecipesTheme.dimensions.scrollBarThickness
    val minLength: Dp = JustRecipesTheme.dimensions.scrollBarMinLength
    val color: Color = JustRecipesTheme.colors.scrollBarColor

    val coroutineScope = rememberCoroutineScope()
    var isDraggingScrollbar by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (state.isScrollInProgress || isDraggingScrollbar) 1f else 0f,
        animationSpec = tween(
            400,
            delayMillis = if (state.isScrollInProgress || isDraggingScrollbar) 0 else 700
        ),
        label = "ScrollBarAlpha"
    )

    val layoutInfo = state.layoutInfo
    //val layoutInfo by remember {
    //    derivedStateOf { state.layoutInfo }
    //}

    val visibleItems = layoutInfo.visibleItemsInfo

    if (layoutInfo.totalItemsCount == 0 || visibleItems.isEmpty()) return

    with(LocalDensity.current) {
        val visibleHeightPx =
            (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset).toFloat()
        val minLengthPx = minLength.toPx()
        val thicknessPx = thickness.toPx()

        // Better average: sum of visible sizes / visible count
        val averageItemSize = visibleItems.sumOf { it.size }.toFloat() / visibleItems.size
        val totalItemsCount = layoutInfo.totalItemsCount
        val totalContentHeightPx = averageItemSize * totalItemsCount

        // Thumb height proportional to visible fraction
        val scrollbarHeightPx = (visibleHeightPx * (visibleHeightPx / totalContentHeightPx))
            .coerceIn(minLengthPx..visibleHeightPx)
        val variableZone = (visibleHeightPx - scrollbarHeightPx).coerceAtLeast(1f) // avoid /0

        // Estimate how many pixels we've scrolled from top
        val scrolledPx =
            state.firstVisibleItemIndex * averageItemSize + state.firstVisibleItemScrollOffset
        //val scrolledPx by remember {
        //    derivedStateOf {
        //        state.firstVisibleItemIndex * averageItemSize + state.firstVisibleItemScrollOffset
        //    }
        //}

        val totalScrollableRange = (totalContentHeightPx - visibleHeightPx).coerceAtLeast(1f)

        // normalized progress and thumb offset
        val scrollProgress = (scrolledPx / totalScrollableRange).coerceIn(0f, 1f)
        val scrollOffsetPx = scrollProgress * variableZone

        val isVertical = direction == Direction.Vertical
        val modifier = if (isVertical) {
            Modifier
                .fillMaxHeight()
                .width(thickness)
                .align(Alignment.CenterEnd)
        } else {
            Modifier
                .fillMaxWidth()
                .height(thickness)
                .align(Alignment.BottomCenter)
        }

        Box(
            modifier = modifier.pointerInput(state) {
                val onStart: (Offset) -> Unit = { isDraggingScrollbar = true }

                val onDrag: (PointerInputChange, Float) -> Unit = { _, dragAmount ->
                    coroutineScope.launch {
                        // Map thumb drag to content scroll
                        // dragAmount is thumb delta in px (positive if user drags down/right).
                        val scrollDeltaPx = (dragAmount / variableZone) * totalScrollableRange
                        // Compute desired scrolled position (clamped)
                        val desiredScrolled =
                            (scrolledPx + scrollDeltaPx).coerceIn(0f, totalScrollableRange)
                        // Compute how many pixels we need to scroll now
                        val deltaToScroll = desiredScrolled - scrolledPx
                        // If scrolling seems inverted on your device, flip sign: state.scrollBy(-deltaToScroll)
                        state.scrollBy(deltaToScroll)
                    }
                }

                val onEnd = { isDraggingScrollbar = false }

                if (isVertical) {
                    detectVerticalDragGestures(
                        onDragStart = onStart,
                        onVerticalDrag = onDrag,
                        onDragEnd = onEnd,
                        onDragCancel = onEnd
                    )
                } else {
                    detectHorizontalDragGestures(
                        onDragStart = onStart,
                        onHorizontalDrag = onDrag,
                        onDragEnd = onEnd,
                        onDragCancel = onEnd
                    )
                }
            }
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                if (isVertical) {
                    // We draw at x=0 because the parent Box width equals thickness
                    drawRoundRect(
                        topLeft = Offset(0f, scrollOffsetPx),
                        size = Size(thicknessPx, scrollbarHeightPx),
                        cornerRadius = CornerRadius(thicknessPx / 2f),
                        color = color,
                        alpha = alpha
                    )
                } else {
                    drawRoundRect(
                        topLeft = Offset(scrollOffsetPx, 0f),
                        size = Size(scrollbarHeightPx, thicknessPx),
                        cornerRadius = CornerRadius(thicknessPx / 2f),
                        color = color,
                        alpha = alpha
                    )
                }
            }
        }
    }
}