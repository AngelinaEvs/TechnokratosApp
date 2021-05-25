package ru.itis.regme.presenter.clients.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.itis.regme.data.db.model.Client
import ru.itis.regme.presenter.ContactModel

class PhoneNumbersAdapter(
        private val list: List<ContactModel>,
        private val action: (ContactModel) -> Unit
) : RecyclerView.Adapter<PhoneNumbersHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneNumbersHolder = PhoneNumbersHolder.create(parent, action)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PhoneNumbersHolder, position: Int) = holder.bind(list[position])

}