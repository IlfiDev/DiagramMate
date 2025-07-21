package org.ilfidev.diagram_mate.presentation.draggableBoard.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.BoardItem
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoard
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardEvent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent

class DraggableBoardViewModel : ViewModel() {
    val firstItem: MutableMap<String, BoardItem> = mutableMapOf(
        "0" to BoardItem.BlockItem(id = "0",
            position = Offset(200f, 200f), size = Size(100f, 100f))
    )
    private val _board = MutableStateFlow(DraggableBoard(items = firstItem))
    val board: StateFlow<DraggableBoard> = _board

    private var idCounter = 1

    fun addItem() {
        val item = BoardItem.BlockItem(id = idCounter.toString(), position = Offset(100f * idCounter, 100f * idCounter), size = Size(100f, 100f), )
//        _board.value.items.put(item.id, item)
        val itemsMap = _board.value.items.toMutableMap()
        itemsMap.put(item.id, item)
        itemsMap[(idCounter - 1).toString()]?.children?.add(item.id)
        _board.update { current ->
            current.copy(items = itemsMap)
        }
        idCounter += 1
//        _board.value.items["0"]?.children?.add(item.id)
    }

//    fun onDragItem(id: String, position: Offset) {
//        val item = _board.value.items[id] ?: return
//        val change = position - item.position
//        val updatedChildren = updateChildren(item.children, change)
//
//        val updatedItem = when (item){
//            is BoardItem.BlockItem -> item.copy(position = position)
//            is BoardItem.TextItem -> item.copy(position = position)
//        }
//
//        val updatedMap = _board.value.items.toMutableMap().apply {
//            this[id] = updatedItem
//            updatedChildren.forEach { child ->
//                this[child.id] = child
//            }
//        }
//        _board.value = _board.value.copy(items = updatedMap)
//
//    }

    fun onDragItemEnd(id: String, position: Offset) {
        val item = _board.value.items[id] ?: return
        val updatedItem = when (item) {
            is BoardItem.BlockItem -> item.copy(position = position)
            is BoardItem.TextItem -> item.copy(position = position)
        }
        val updatedMap = _board.value.items.toMutableMap().apply {
            this[id] = updatedItem
        }
        _board.value = _board.value.copy(items = updatedMap)
    }

//    fun updateChildren(childrenIds: List<String>, updateValue: Offset) : List<DraggableItem> {
//        val listOfUpdatedChildren = mutableListOf<DraggableItem>()
//        childrenIds.forEach { childId ->
//            val child = _board.value.items[childId]
//            val updatedChild = child?.copy(position = child.position + updateValue)
//            updatedChild?.let { listOfUpdatedChildren.add(it) }
//        }
//        return listOfUpdatedChildren
//    }

    fun updateChildren(childrenIds: List<String>, updateValue: Offset) {
        val listOfUpdatedChildren = mutableListOf<BoardItem>()
        childrenIds.forEach { childId ->
            val child = _board.value.items[childId] ?: return@forEach
            val updatedChild = when (child) {
                is BoardItem.BlockItem -> child.copy(position = child.position + updateValue)
                is BoardItem.TextItem -> child.copy(position = child.position + updateValue)
            }
            listOfUpdatedChildren.add(updatedChild)
        }
        val updatedMap = _board.value.items.toMutableMap().apply {
            listOfUpdatedChildren.forEach { item ->
                this[item.id] = item
            }
        }
        _board.value = _board.value.copy(items = updatedMap)
        listOfUpdatedChildren.forEach { child ->
            updatedMap[child.id]?.children?.let {
                updateChildren(it, updateValue)
            }
        }
    }

    private fun onEndDrag() {

    }
    private fun onStartDrag(){

    }
}