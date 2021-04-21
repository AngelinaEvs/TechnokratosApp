package ru.itis.regme.presenter.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_fragment.*
import ru.itis.regme.App
import ru.itis.regme.R
import javax.inject.Inject

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    @Inject lateinit var viewModel: ProfileViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.profileComponentFactory()
                .create(this)
                .inject(this)
        navController = Navigation.findNavController(view)
        loadProfile()
    }

    private fun loadProfile() {
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        var database = FirebaseDatabase.getInstance()
        var databaseReference: DatabaseReference? = database.reference.child("profile")
        val userreference = databaseReference?.child(user?.uid!!)

        tv1.text = "Email: " + user?.email
        tv2.text = "1111"
        tv3.text = "Date: " + user?.uid.toString()
        tv4.text = "22222"

//        userreference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                //tv2.text = "Firstname: " + snapshot.child("firstname").value.toString()
////                lastnameText.text = "Last name: " + snapshot.child("lastname").value.toString()
//                //tv2.text = "Date: " + snapshot.child("year").child("resords").child("date").value.toString()
//
////                var a = snapshot.children.iterator()
////                while (a.hasNext()) {
////                    var k = a.next()
////                    var key = k.key
////                    if (key == "year") a = k.children.iterator()
////                    if (key == "resords") a = k.children.iterator()
////                    Log.e("ITERATOR", key.toString())
////                    if (key == "date") {
////                        tv2.text = "OAOAO: " + k.value.toString()
////                        break
////                    }
////                }
//                tv4.text = "hjkl"
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
    }

    private fun initListeners() {
        phoneNumbers.setOnClickListener { navController.navigate(R.id.action_profile_to_numbers) }
        sign_out_but.setOnClickListener {
            viewModel.signOut()
            navController.navigate(R.id.action_profile_to_login)
        }
        myGraphic.setOnClickListener {
            navController.navigate(R.id.action_profile_to_calendar)
        }
    }

}