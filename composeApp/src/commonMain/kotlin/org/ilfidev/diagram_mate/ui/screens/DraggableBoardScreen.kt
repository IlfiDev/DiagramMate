package org.ilfidev.diagram_mate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardEvent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableItem
import org.ilfidev.diagram_mate.presentation.draggableBoard.viewmodel.DraggableBoardViewModel


@Composable
fun DraggableBoardScreen(viewModel: DraggableBoardViewModel = viewModel()) {
    val state by viewModel.board.collectAsState()
    Button(onClick = {viewModel.addItem()}) {

    }
    state.items.forEach {
        println(it.value.toString())
        val children = it.value.children
        DraggableTextLowLevel(it.value, {offset -> viewModel.onDragItemEnd(it.key, offset)}, onDrag = {offset -> viewModel.updateChildren(children, offset)})
    }
}
@Composable
fun DraggableTextLowLevel(state: DraggableItem, onDragEnd: (Offset) -> Unit = {}, onDrag: (Offset) -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        var localOffset by remember { mutableStateOf(state.position) }
        var isDragging by remember { mutableStateOf(false) }

        LaunchedEffect(state.position) {
            if (!isDragging) {
                localOffset = state.position
            }
        }

        ResizableContainer(modifier =
            Modifier
                .offset { IntOffset(localOffset.x.toInt(), localOffset.y.toInt()) }
                .background(Color.Green)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                        },
                        onDragCancel = {
                            isDragging = false
                        },
                        onDragEnd = {
                            isDragging = false
                            onDragEnd(localOffset)
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        localOffset += dragAmount
                        onDrag(dragAmount)
                    }
//                    onUpdate(DraggableBoardIntent.EndDragging)

                }
        ) {
            Text(state.text)
        }
    }
}

@Composable
fun ResizableContainer(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier) {
        content()
    }
}
