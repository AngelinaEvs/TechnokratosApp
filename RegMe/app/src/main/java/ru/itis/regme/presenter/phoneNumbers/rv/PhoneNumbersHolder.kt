package ru.itis.regme.presenter.phoneNumbers.rv

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.number_item.*
import ru.itis.regme.R
import ru.itis.regme.presenter.ContactModel
import ru.itis.regme.presenter.calendar.recordslist.RecordsHolder

class PhoneNumbersHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: ContactModel) {
        with (item) {
            name_client.text = fullname
            phone_number.text = phone
        }
    }

    companion object {
        fun create(parent: ViewGroup): PhoneNumbersHolder
                = PhoneNumbersHolder(LayoutInflater.from(parent.context).inflate(R.layout.number_item, parent, false))
    }

}