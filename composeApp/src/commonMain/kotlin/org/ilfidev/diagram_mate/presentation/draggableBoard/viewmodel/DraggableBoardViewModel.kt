package org.ilfidev.diagram_mate.presentation.draggableBoard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardState
import org.ilfidev.diagram_mate.presentation.draggableBoard.store.DraggableBoardStore

class DraggableBoardViewModel : ViewModel() {
    private val store = DraggableBoardStore()
    val state: StateFlow<DraggableBoardState> = store.state

    fun dispatch(intent: DraggableBoardIntent) {
        store.dispatch(intent)
    }
}