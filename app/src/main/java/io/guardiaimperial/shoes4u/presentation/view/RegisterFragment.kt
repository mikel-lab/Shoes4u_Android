package io.guardiaimperial.shoes4u.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentRegisterBinding
import io.guardiaimperial.shoes4u.domain.model.User
import io.guardiaimperial.shoes4u.presentation.viewmodel.AuthViewModel
import io.guardiaimperial.shoes4u.utils.*

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    val TAG: String = "RegisterFragment"
    lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.btnRegister.setOnClickListener {
            if (validation()) {
                viewModel.register(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString(),
                    user = getUserObj()
                )
            }
        }
    }

    fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnRegister.text = ""
                    binding.pbRegister.isVisible = true
                }
                is UiState.Failure -> {
                    binding.btnRegister.text = "Register"
                    binding.pbRegister.isVisible = false
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnRegister.text = "Register"
                    binding.pbRegister.isVisible = false
                    toast(state.data)
                    //findNavController().navigate(R.id.registerFragment)
                }
            }
        }
    }

    private fun getUserObj(): User {
        return User(
            id = "",
            email = binding.etEmail.text.toString(),
            name = binding.etName.text.toString(),
            surname = binding.etSurnames.text.toString(),
            username = binding.etUsername.text.toString(),
            city = binding.etCity.text.toString(),
            province = binding.etProvince.text.toString()
        )

    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.etEmail.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_email))
        } else {
            if (!binding.etEmail.text.toString().isValidEmail()) {
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }

        if (binding.etPassword.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_password))
        } else {
            if (binding.etPassword.text.toString().length < 8) {
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }

        if (binding.etName.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_name))
        }

        if (binding.etSurnames.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_surnames))
        }

        if (binding.etUsername.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_username))
        }

        if (binding.etCity.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_city))
        }

        if (binding.etProvince.text.isNullOrEmpty()) {
            isValid = false
            toast(getString(R.string.enter_province))
        }

        return isValid
    }
}