package com.alexit.justrecipes.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.alexit.justrecipes.presentation.theme.JustRecipesTheme
import kotlinx.coroutines.launch

@Composable
fun BoxScope.CustomScrollBar(
    listState: LazyListState,
) {
    val thickness: Dp = JustRecipesTheme.dimensions.scrollBarThickness
    val minLength: Dp = JustRecipesTheme.dimensions.scrollBarMinLength
    val color: Color = JustRecipesTheme.colors.scrollBarColor
    val innerPaddingScroll = JustRecipesTheme.dimensions.innerPaddingCard

        val coroutineScope = rememberCoroutineScope()
        var isDraggingScrollbar by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (listState.isScrollInProgress || isDraggingScrollbar) 1f else 0f,
            animationSpec = tween(400, delayMillis = if (listState.isScrollInProgress || isDraggingScrollbar) 0 else 700),
            label = "ScrollBarAlpha"
        )

        val listIsEmpty by remember {
            derivedStateOf {
                listState.layoutInfo.totalItemsCount == 0 || listState.layoutInfo.visibleItemsInfo.isEmpty()
            }
        }
        if (listIsEmpty) return

        with(LocalDensity.current) {

            val minLengthPx = minLength.toPx()
            val thicknessPx = thickness.toPx()

            val averageItemSize by remember {
                derivedStateOf {
                    listState.layoutInfo.visibleItemsInfo.sumOf { it.size }.toFloat() /
                            listState.layoutInfo.visibleItemsInfo.size
                }
            }

            val firstVisiblyItemOffset by remember {
                derivedStateOf {
                    listState.firstVisibleItemScrollOffset
                }
            }

            val firstVisiblyItemIndex by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex
                }
            }

            val visibleHeightPx by remember {
                derivedStateOf {
                    (listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset).toFloat()
                }
            }

            val totalItemsCount by remember {
                derivedStateOf {
                    listState.layoutInfo.totalItemsCount
                }
            }
            val totalContentHeightPx = averageItemSize * totalItemsCount

            // Thumb height proportional to visible fraction
            val scrollbarHeightPx = (visibleHeightPx * (visibleHeightPx / totalContentHeightPx))
                .coerceIn(minLengthPx..visibleHeightPx)
            val variableZone = (visibleHeightPx - scrollbarHeightPx).coerceAtLeast(1f) // avoid /0

            // Estimate how many pixels we've scrolled from top
            val scrolledPx = firstVisiblyItemIndex * averageItemSize + firstVisiblyItemOffset
            val totalScrollableRange = (totalContentHeightPx - visibleHeightPx).coerceAtLeast(1f)

            // normalized progress and thumb offset
            val scrollProgress = (scrolledPx / totalScrollableRange).coerceIn(0f, 1f)
            val scrollOffsetPx = scrollProgress * variableZone

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(innerPaddingScroll)
                    .align(Alignment.TopEnd)
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                // Map thumb drag to content scroll
                                // dragAmount is thumb delta in px (positive if user drags down/right).
                                val scrollDeltaPx =
                                    (delta / variableZone) * totalScrollableRange
                                // Compute desired scrolled position (clamped)
                                val desiredScrolled =
                                    (scrolledPx + scrollDeltaPx).coerceIn(0f, totalScrollableRange)
                                // Compute how many pixels we need to scroll now
                                val deltaToScroll = desiredScrolled - scrolledPx
                                listState.scrollBy(deltaToScroll)
                            }
                        },
                        onDragStarted = { isDraggingScrollbar = true },
                        onDragStopped = { isDraggingScrollbar = false }
                    )
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawRoundRect(
                        topLeft = Offset((innerPaddingScroll - thickness).toPx(), scrollOffsetPx),
                        size = Size(thicknessPx, scrollbarHeightPx),
                        cornerRadius = CornerRadius(thicknessPx / 2f),
                        color = color,
                        alpha = alpha
                    )
                }
            }
        }
    }