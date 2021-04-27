package ru.itis.regme.presenter.sign.`in`

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.passwordInput
import kotlinx.android.synthetic.main.login_fragment.usernameInput
import ru.itis.regme.App
import ru.itis.regme.R
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSubscribes()
        initListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.signInComponentFactory()
            .create(this)
            .inject(this)
        navController = Navigation.findNavController(view)
    }

    private fun initListeners() {
        registerText.setOnClickListener { navController.navigate(R.id.action_to_registration) }
        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(usernameInput.text.toString())) {
                usernameInput.error = "Please enter username"
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(passwordInput.text.toString())) {
                usernameInput.error = "Please enter password"
                return@setOnClickListener
            }
            viewModel.loginStatus(usernameInput.text.toString(), passwordInput.text.toString())
        }
    }

    private fun initSubscribes() {
        viewModel.mainStatus().observe(viewLifecycleOwner, {
            if (it) navController.navigate(R.id.action_to_profile)
        })
    }

}