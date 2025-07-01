package org.ilfidev.diagram_mate.presentation.draggableBoard.reducer

import androidx.compose.ui.geometry.Offset
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardState
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableItem

object DraggableBoardReducer {
    private fun updateItemRecursive(
        item: DraggableItem,
        targetId: String,
        newPosition: Offset
    ): DraggableItem {
        return if (item.id == targetId) {
            item.copy(position = newPosition)
        } else {
            item.copy(children = item.children.map { updateItemRecursive(it, targetId, newPosition) })
        }
    }

    fun reduce(state: DraggableBoardState, intent: DraggableBoardIntent): DraggableBoardState {
        return when (intent) {
            is DraggableBoardIntent.StartDragging -> {
                state.copy(isDragging = true)
            }
            is DraggableBoardIntent.DragItem -> {
                val updatedItems = state.items.map {
                    updateItemRecursive(it, intent.id, intent.position)
                }
                state.copy(items = updatedItems)
            }
            DraggableBoardIntent.EndDragging -> {
                state.copy(isDragging = false)
            }
        }
    }
}