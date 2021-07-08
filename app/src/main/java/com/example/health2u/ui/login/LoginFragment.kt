package com.example.health2u.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.health2u.BaseFragment
import com.example.health2u.R
import com.google.firebase.auth.FirebaseAuth
import com.healthcarelocator.custom.IOneKeyView
import customization.map.CustomCurrentLocationOverlay

class LoginFragment : BaseFragment() {

    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    override fun onBackPressed(): Boolean {
        findNavController().popBackStack()
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton=view.findViewById(R.id.register_button)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        auth = FirebaseAuth.getInstance()


        email=view.findViewById(R.id.email_edit_text_login)
        password=view.findViewById(R.id.password_edit_text_login)
        registerButton=view.findViewById(R.id.register_button)
        loginButton=view.findViewById(R.id.button_two_button)


        loginButton.setOnClickListener {
            findNavController().navigate(R.id.destination_home)

           // SignUser(email.text.toString(),password.text.toString())

        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.destination_register)

        }

    }


    fun SignUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("user", "createUserWithEmail:success")
                val user = auth.currentUser
               findNavController().navigate(R.id.destination_home)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("user", "createUserWithEmail:failure", task.exception)
//                Toast.makeText(baseContext, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show()
//                updateUI(null)



            }

        }

    }

}