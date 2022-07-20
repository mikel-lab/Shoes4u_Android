package io.guardiaimperial.shoes4u.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentLoginBinding
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.utils.isValidEmail
import io.guardiaimperial.shoes4u.utils.toast

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val TAG: String = "LoginFragment"
    lateinit var binding: FragmentLoginBinding
    val viewModel: AuthViewModel by viewModels()

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
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.registerTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Response.Loading -> {
                    binding.btnLogin.text = ""
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is Response.Failure -> {
                    binding.btnLogin.text = "Login"
                    binding.pbLogin.visibility = View.GONE
                    toast(state.error)
                }
                is Response.Success -> {
                    binding.btnLogin.text = "Login"
                    binding.pbLogin.visibility = View.GONE
                    toast(state.data)
                    findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                }
            }
        }
    }

    fun validation(): Boolean {
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

    override fun onStart() {
        super.onStart()
        viewModel.getSession { user ->
            if(user != null){
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            }
        }
    }
}