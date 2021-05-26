package ru.itis.regme.presenter.clients

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ru.itis.regme.App
import ru.itis.regme.R
import ru.itis.regme.presenter.calendar.ContactModel
import ru.itis.regme.presenter.clients.rv.PhoneNumbersAdapter
import javax.inject.Inject

class PhoneNumbersFragment : Fragment() {

    companion object {
        fun newInstance() = PhoneNumbersFragment()
    }

    @Inject
    lateinit var viewModel: PhoneNumbersViewModel
    private lateinit var navController: NavController
    private lateinit var phoneNumbersAdapter: PhoneNumbersAdapter
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.phone_numbers_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.phoneNumberComponentFactory()
            .create(this)
            .inject(this)
        rv = view.findViewById(R.id.rv_numbers)
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
        phoneNumbersAdapter = PhoneNumbersAdapter(list) {
            val mess = "Здравствуйте, напоминаю, что Вы записаны завтра на ${it.time}"
            var num = it.phone
            if (num.startsWith("8")) num = "7" + num.substring(1)
            val isInstalled = isInstalled()
            if (isInstalled) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send/?phone=$num&text=$mess")
                startActivity(intent)
            } else Toast.makeText(
                requireContext(),
                "Whats app не установлен на этом устройстве",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (list.isEmpty()) view?.findViewById<TextView>(R.id.mess)?.isVisible = true
        else rv.adapter = phoneNumbersAdapter
    }

    private fun isInstalled(): Boolean {
        val packageManager = requireContext().packageManager
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            return true
        } catch (notFound: PackageManager.NameNotFoundException) {
            return false
        }
    }

}