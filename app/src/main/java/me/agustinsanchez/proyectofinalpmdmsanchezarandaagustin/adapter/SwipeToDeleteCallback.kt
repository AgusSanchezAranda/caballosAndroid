package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class SwipeToDeleteCallback(private val onItemDeleteListener: OnItemDeleteListener):
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int){
        val position = viewHolder.adapterPosition
        onItemDeleteListener.onDeleteItem(position)
    }

    interface  OnItemDeleteListener{
        fun onDeleteItem(position: Int)
    }
}