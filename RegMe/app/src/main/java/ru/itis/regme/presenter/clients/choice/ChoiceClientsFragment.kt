package ru.itis.regme.presenter.clients.choice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.contains
import androidx.core.util.size
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.data.db.model.Client
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ChoiceClientsFragment : Fragment() {

    companion object {
        fun newInstance() = ChoiceClientsFragment()
    }

    @Inject
    lateinit var viewModel: ChoiceClientsViewModel
    lateinit var listView: ListView
    lateinit var saveTV: TextView
    lateinit var array: List<Client>
    private var dbnumbers = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choice_clients_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.choiceComponentFactory()
            .create(this)
            .inject(this)
        listView = view.findViewById(R.id.choice)
        saveTV = view.findViewById(R.id.saveTV)
        viewModel.findAllFromTelephone(requireContext())
        initSubscribes()
        initListeners()
    }

    private fun initListeners() {
        saveTV.setOnClickListener {
            viewModel.getNumbersFromDb()
            var keys = ArrayList<Int>()
            val sba = listView.checkedItemPositions
            for (i in 0 until sba.size) {
                if (sba[sba.keyAt(i)]) keys.add(sba.keyAt(i))
            }
            for (i in array.indices) {
                if (dbnumbers.contains(array[i].number)) {
                    if (!keys.contains(i)) viewModel.delete(array[i].number!!)
                } else if (keys.contains(i) && !dbnumbers.contains(array[i].number)) viewModel.save(Client(array[i].name, array[i].number))
            }
            viewModel.getNumbersFromDb()
        }
    }

    private fun initSubscribes() {
        with(viewModel) {
            telephNumbers().observe(viewLifecycleOwner, {
                array = it
                var a = ArrayList<String>()
                for (i in array.indices) {
                    a.add(array[i].name)
                }
                var adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    a
                )
                listView.adapter = adapter
            })
            mainNumbers().observe(viewLifecycleOwner, {
                dbnumbers.clear()
                dbnumbers.addAll(it)
            })
        }
    }

}