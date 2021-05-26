package ru.itis.regme.presenter.sign.up

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.registration_fragment.*
import kotlinx.android.synthetic.main.registration_fragment.firstnameInput
import kotlinx.android.synthetic.main.registration_fragment.lastnameInput
import kotlinx.android.synthetic.main.registration_fragment.passwordInput
import kotlinx.android.synthetic.main.registration_fragment.registerButton
import kotlinx.android.synthetic.main.registration_fragment.usernameInput
import ru.itis.regme.App
import ru.itis.regme.R
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationFragment()
    }

    @Inject lateinit var viewModel: RegistrationViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.registrationComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
        initListeners()
    }

    private fun initListeners() {
        loginText.setOnClickListener { navController.navigate(R.id.action_to_login) }
        registerButton.setOnClickListener {
            if (TextUtils.isEmpty(firstnameInput.text.toString())) {
                firstnameInput.error = "Введите имя"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(lastnameInput.text.toString())) {
                firstnameInput.error = "Введите фамилию"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(usernameInput.text.toString()) && isEmail(usernameInput.text.toString())) {
                firstnameInput.error = "Введите почту"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                firstnameInput.error = "Введите пароль"
                return@setOnClickListener
            }
            viewModel.register(usernameInput.text.toString(), passwordInput.text.toString(), firstnameInput.text.toString(), lastnameInput.text.toString())
            navController.navigate(R.id.action_to_login)
        }
    }

    fun isEmail(email: String): Boolean {
        val regexEmail = Regex(pattern = "^([a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9].)*[a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9]@([a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9].)+[a-zA-Z]{2,6}$")
        return regexEmail.containsMatchIn(email)
    }

}