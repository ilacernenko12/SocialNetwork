package com.example.socialnetwork.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.socialnetwork.R
import com.example.socialnetwork.databinding.FragmentAddPersonalInfoSheetBinding
import com.example.socialnetwork.utils.ViewUtils
import com.example.socialnetwork.viewmodels.AddInfoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class AddPersonalInfoFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddPersonalInfoSheetBinding
    private val viewModel: AddInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerEmptyFields()
        setOnClickListener()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPersonalInfoSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observerEmptyFields() {
        viewModel.isEmptyFields.observe(this) { isEmptyFields ->
            if (!isEmptyFields) {
                dismiss()
            } else {
                onEmptyFields()
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnSave.setOnClickListener {
            val shortBio = binding.etShortBio.text.toString()
            val age = binding.etAge.text.toString()
            val city = binding.etCity.text.toString()

            viewModel.addUserInfo(
                shortBio = shortBio,
                age = age,
                city = city
            )

        }
    }

    // Чтобы не было углов после закругления
    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    private fun onEmptyFields() {
        binding.etShortBio.error = "Fields can be empty"
        binding.etAge.error = "Fields can be empty"
        binding.etCity.error = "Fields can be empty"
        binding.etShortBio.setBackgroundResource(R.drawable.box_stroke_error)
        binding.etAge.setBackgroundResource(R.drawable.box_stroke_error)
        binding.etCity.setBackgroundResource(R.drawable.box_stroke_error)
    }
}