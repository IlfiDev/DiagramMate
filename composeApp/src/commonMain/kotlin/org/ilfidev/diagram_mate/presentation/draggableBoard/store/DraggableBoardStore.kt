package org.ilfidev.diagram_mate.presentation.draggableBoard.store

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardState
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableItem
import org.ilfidev.diagram_mate.presentation.draggableBoard.reducer.DraggableBoardReducer
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent

class DraggableBoardStore {
    private val _state = MutableStateFlow(
        DraggableBoardState(
            items = listOf(
                DraggableItem("1", Offset(100f, 100f), Size(100f, 100f), children = listOf(
                    DraggableItem("1.1", Offset(20f, 20f), Size(20f, 20f), text = "Child 1.1"),
                    DraggableItem("1.2", Offset(60f, 60f), Size(30f, 30f), text = "Child 1.2")
                )),
                DraggableItem("2", Offset(200f, 300f), Size(20f, 20f), text = "Item 2")
            )
        )
    )
    val state: StateFlow<DraggableBoardState> = _state

    fun dispatch(intent: DraggableBoardIntent) {
        _state.value = DraggableBoardReducer.reduce(_state.value, intent)
    }
}