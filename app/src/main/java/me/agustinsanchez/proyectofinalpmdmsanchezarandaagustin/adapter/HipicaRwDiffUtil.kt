package me.agustinsanchez.proyectofinalpmdmsanchezarandaagustin.adapter

import androidx.recyclerview.widget.DiffUtil

class HipicaRwDiffUtil (
    private val oldList: List<ReservaModel>,
    private val newList: List<ReservaModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int  = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}