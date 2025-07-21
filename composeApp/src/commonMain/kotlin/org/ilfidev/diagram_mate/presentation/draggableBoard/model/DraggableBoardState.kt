package org.ilfidev.diagram_mate.presentation.draggableBoard.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

interface BaseItem {
    val id: String
    val position: Offset
    val size: Size
    val isDraggable: Boolean
    val children: MutableList<String>

}

sealed class BoardItem : BaseItem {
    data class TextItem(
        override val id: String,
        override val position: Offset,
        override val size: Size,
        override val isDraggable: Boolean = true,
        override val children: MutableList<String> = mutableListOf<String>(),
        val text: String
    ) : BoardItem()
    data class BlockItem(
        override val id: String,
        override val position: Offset,
        override val size: Size,
        override val isDraggable: Boolean = true,
        override val children: MutableList<String> = mutableListOf<String>(),
    ) : BoardItem()
}
//data class DraggableItem(
//    val id: String,
//    val relativePosition: Offset,
//    val position: Offset,
//    val size: Size,
//    val text: String = "",
//    val isEditing: Boolean = false,
//    val children: MutableList<String> = mutableListOf<String>()
//)

data class DraggableBoard(
    val items: MutableMap<String, BoardItem> = mutableMapOf<String, BoardItem>(),
)