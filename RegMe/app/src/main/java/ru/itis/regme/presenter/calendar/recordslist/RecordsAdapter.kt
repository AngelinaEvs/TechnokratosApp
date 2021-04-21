package ru.itis.regme.presenter.calendar.recordslist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecordsAdapter (
    private val list: List<Pair<String, String>>
) : RecyclerView.Adapter<RecordsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsHolder = RecordsHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecordsHolder, position: Int) = holder.bind(list[position])

//    override fun onBindViewHolder(holder: RecordsHolder, position: Int, payloads: MutableList<Any>) {
//        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
//        else {
//            (payloads[0] as? Bundle)?.also {
//                holder.updateFromBundle(it)
//            } ?: run { super.onBindViewHolder(holder, position, payloads) }
//        }
//    }

}