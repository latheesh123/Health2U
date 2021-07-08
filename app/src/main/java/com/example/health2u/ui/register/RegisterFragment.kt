package com.example.health2u.ui.register

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
import com.example.health2u.R
import com.example.health2u.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import kotlin.math.log

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        email=view.findViewById(R.id.email_edit_text)
        password=view.findViewById(R.id.password_edit_text)
        registerButton=view.findViewById(R.id.register_button)

        registerButton.setOnClickListener {

            CreateUser(email.text.toString(),password.text.toString())
        }

    }

    fun CreateUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("user", "createUserWithEmail:success")
                val user = auth.currentUser
                findNavController().navigate(R.id.destination_login)
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