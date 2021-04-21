package ru.itis.regme.presenter.phoneNumbers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.phone_numbers_fragment.*
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.presenter.ContactModel
import ru.itis.regme.presenter.phoneNumbers.rv.PhoneNumbersAdapter
import javax.inject.Inject

class PhoneNumbersFragment : Fragment() {

    companion object {
        fun newInstance() = PhoneNumbersFragment()
    }

    @Inject lateinit var viewModel: PhoneNumbersViewModel
    private lateinit var navController: NavController
    private lateinit var phoneNumbersAdapter: PhoneNumbersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.phone_numbers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         // TODO: Use the ViewModel
    }

    //TODO показывать сообщение, если не дано разрешение на доступ к контактам

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.phoneNumberComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
        viewModel.getNumbers()
        initSubscribes()
    }

    private fun initSubscribes() {
        with(viewModel) {
            mainNumbers().observe(viewLifecycleOwner, {
                initAdapter(it)
            })
        }
    }

    private fun initAdapter(list: List<ContactModel>) {
        phoneNumbersAdapter = PhoneNumbersAdapter(list)
        rv_numbers.adapter = phoneNumbersAdapter
    }


}