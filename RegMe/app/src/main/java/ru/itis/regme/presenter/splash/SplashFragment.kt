package ru.itis.regme.presenter.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.itis.regme.App
import ru.itis.regme.R
import javax.inject.Inject

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    @Inject
    lateinit var viewModel: SplashViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.splashComponentFactory()
            .create(this)
            .inject(this)
        navController = Navigation.findNavController(view)
        viewModel.isLogin()
        initSubscribes()
    }

    private fun initSubscribes() {
        viewModel.mainIsLogin().observe(viewLifecycleOwner, {
            if (it) navController.navigate(R.id.action_to_profile)
            else navController.navigate(R.id.action_to_sign)
        })
    }

}