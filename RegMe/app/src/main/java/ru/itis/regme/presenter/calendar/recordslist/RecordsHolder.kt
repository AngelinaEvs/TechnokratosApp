package ru.itis.regme.presenter.calendar.recordslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_record_item.*
import ru.itis.regme.R

class RecordsHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(recordItem: Pair<String, String>) {
        with (recordItem) {
            time_records.text = first
            client_name_records.text = second
        }
    }

//    fun updateFromBundle(bundle: Bundle) {
//        if (bundle.containsKey("ARG_NAME")) {
//            bundle.getString("ARG_NAME").also {
//                search_text.text = it
//            }
//        }
//    }

    companion object {
        fun create(parent: ViewGroup): RecordsHolder
            = RecordsHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_record_item, parent, false))
    }

}