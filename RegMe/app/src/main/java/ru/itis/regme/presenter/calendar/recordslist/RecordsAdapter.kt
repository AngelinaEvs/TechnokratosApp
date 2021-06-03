package ru.itis.regme.presenter.calendar.recordslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecordsAdapter (
    private val list: MutableList<Pair<String, String>>
) : RecyclerView.Adapter<RecordsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsHolder = RecordsHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecordsHolder, position: Int) = holder.bind(list[position])

    fun updateData(list: List<Pair<String, String>>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}