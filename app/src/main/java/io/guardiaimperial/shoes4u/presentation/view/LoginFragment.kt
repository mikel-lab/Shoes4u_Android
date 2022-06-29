package io.guardiaimperial.shoes4u.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentLoginBinding
import io.guardiaimperial.shoes4u.databinding.FragmentRegisterBinding
import io.guardiaimperial.shoes4u.presentation.viewmodel.AuthViewModel
import io.guardiaimperial.shoes4u.utils.UiState
import io.guardiaimperial.shoes4u.utils.isValidEmail
import io.guardiaimperial.shoes4u.utils.toast

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val TAG: String = "LoginFragment"
    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.btnLogin.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString(),
                )
            }
        }

        binding.forgotPasswordTv.setOnClickListener {

        }

        binding.registerTv.setOnClickListener {
            //findNavController().navigate(R.id.registerFragment)
        }
    }

    fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnLogin.text = ""
                    binding.pbLogin.isVisible = true
                }
                is UiState.Failure -> {
                    binding.btnLogin.text = "Register"
                    binding.pbLogin.isVisible = false
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnLogin.text = "Register"
                    binding.pbLogin.isVisible = false
                    toast(state.data)
                    //findNavController().navigate(R.id.registerFragment)
                }
            }
        }
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

        return isValid
    }
}