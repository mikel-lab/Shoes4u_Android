package io.guardiaimperial.shoes4u.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentForgotPasswordBinding
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.utils.isValidEmail
import io.guardiaimperial.shoes4u.utils.toast

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    val TAG: String = "ForgotPasswordFragment"
    lateinit var binding: FragmentForgotPasswordBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.btnForgotPassword.setOnClickListener {
            if (validation()) {
                viewModel.forgotPassword(
                    email = binding.etEmail.text.toString()
                )
            }
        }
    }

    fun observer() {
        viewModel.forgotPassword.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Response.Loading -> {
                    binding.btnForgotPassword.text = ""
                    binding.pbForgotPassword.visibility = View.VISIBLE
                }
                is Response.Failure -> {
                    binding.btnForgotPassword.text = "Send"
                    binding.pbForgotPassword.visibility = View.GONE
                    toast(state.error)
                }
                is Response.Success -> {
                    binding.btnForgotPassword.text = "Send"
                    binding.pbForgotPassword.visibility = View.GONE
                    toast(state.data)
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
        return isValid
    }
}