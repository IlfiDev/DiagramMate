package org.ilfidev.diagram_mate.presentation.draggableBoard.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoard
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardEvent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableBoardIntent
import org.ilfidev.diagram_mate.presentation.draggableBoard.model.DraggableItem

class DraggableBoardViewModel : ViewModel() {
    val firstItem = mutableMapOf(Pair("0", DraggableItem(id = "0", relativePosition = Offset(0f, 0f), position = Offset(200f, 200f), size = Size(100f, 100f))))
    private val _board = MutableStateFlow(DraggableBoard(items = firstItem))
    val board: StateFlow<DraggableBoard> = _board

    private var idCounter = 1


    fun addItem() {
        val item = DraggableItem(id = idCounter.toString(), position = Offset(100f * idCounter, 100f * idCounter), size = Size(100f, 100f), text = idCounter.toString(), relativePosition = Offset(0f, 0f))
//        _board.value.items.put(item.id, item)
        val itemsMap = _board.value.items.toMutableMap()
        itemsMap.put(item.id, item)
        itemsMap["0"]?.children?.add(item.id)
        _board.update { current ->
            current.copy(items = itemsMap)
        }
        idCounter += 1
//        _board.value.items["0"]?.children?.add(item.id)
    }

    fun onDragItem(id: String, position: Offset) {
        val updatedItem = _board.value.items[id]
        updatedItem?.let {
            val change = position - it.position
            val childrenIds = updatedItem.children
            val newValue= updatedItem.copy(position = position)
            val updatedChildren = updateChildren(childrenIds, change)
            val updatedMap = _board.value.items.toMutableMap().apply {
                this[id] = newValue
                updatedChildren.forEach { item ->
                    this[item.id] = item
                }
            }
            _board.value = _board.value.copy(items = updatedMap)
        }

    }
    fun onDragItemEnd(id: String, position: Offset) {
        val updatedItem = _board.value.items[id]
        updatedItem?.let {
            val newValue = updatedItem.copy(position = position)
            val updatedMap = _board.value.items.toMutableMap().apply {
                this[id] = newValue
            }
            _board.value = _board.value.copy(items = updatedMap)
        }
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

    fun updateChildren(childrenIds: List<String>, updateValue: Offset) : List<DraggableItem> {

        val listOfUpdatedChildren = mutableListOf<DraggableItem>()
        childrenIds.forEach { childId ->
            val child = _board.value.items[childId]
            println(child.toString())
            val updatedChild = child?.copy(position = child.position + updateValue)
            updatedChild?.let { listOfUpdatedChildren.add(it) }
        }
        val updatedMap = _board.value.items.toMutableMap().apply {
            listOfUpdatedChildren.forEach { item ->
                this[item.id] = item
            }
        }
        _board.value = _board.value.copy(items = updatedMap)
        return listOfUpdatedChildren
    }
//    private fun recursiveDragItemUpdate(item: DraggableItem, offset: Offset) : List<DraggableItem> {
//        if (item.children.isEmpty()) {
//            val currentOffset = item.position
//            println(Offset(currentOffset.x + offset.x, currentOffset.y + offset.y).toString())
//            return listOf(item.copy(position = Offset(currentOffset.x + offset.x, currentOffset.y + offset.y)))
//        }
//        val listOfUpdatedChildren = mutableListOf<DraggableItem>()
//        item.children.forEach { childItem ->
//            listOfUpdatedChildren += recursiveDragItemUpdate(childItem, offset)
//        }
//        return listOfUpdatedChildren
//
//    }
    private fun onEndDrag() {

    }
    private fun onStartDrag(){

    }
}