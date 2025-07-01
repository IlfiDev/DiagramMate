package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

sealed interface DraggableBoardEvent {
    data class OnItemDragged(val id: String, val newPosition: Offset) : DraggableBoardEvent
    data class onItemResize(val id: String, val newSize: Size) : DraggableBoardEvent
    data class OnDragStarted(val id: String) : DraggableBoardEvent
    object onItemDragEnded : DraggableBoardEvent
}