package ru.itis.regme.presenter.clients.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.number_item.name_client
import kotlinx.android.synthetic.main.number_item.phone_number
import kotlinx.android.synthetic.main.send_message_item.*
import ru.itis.regme.R
import ru.itis.regme.presenter.calendar.ContactModel

class PhoneNumbersHolder(
        override val containerView: View,
        private val action: (ContactModel) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: ContactModel) {
        with (item) {
            name_client.text = "$time, $name"
            phone_number.text = phone
            send_message.setOnClickListener { action(this) }
//            itemView.setOnClickListener { action(this) }
        }
    }

    companion object {
        fun create(parent: ViewGroup, action: (ContactModel) -> Unit): PhoneNumbersHolder
                = PhoneNumbersHolder(LayoutInflater.from(parent.context).inflate(R.layout.send_message_item, parent, false), action)
    }

}