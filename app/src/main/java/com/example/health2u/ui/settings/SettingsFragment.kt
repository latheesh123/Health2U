package com.example.health2u.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.health2u.R
import com.healthcarelocator.model.HCLLocation

class SettingsFragment : Fragment() {

    private lateinit var emergency: EditText
    private lateinit var saveButton: Button

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emergency = view.findViewById(R.id.enter_emergency_contact)
        saveButton = view.findViewById(R.id.save_button)

        HCLLocation("","steven","dentist")

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        emergency.setText(sharedPref.getString("phone_number",""))

        saveButton.setOnClickListener {
            with(sharedPref.edit()) {
                putString("phone_number", emergency.text.toString())
                apply()
            }
        }


    }
}