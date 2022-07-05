package io.guardiaimperial.shoes4u.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentProductsBinding
import io.guardiaimperial.shoes4u.presentation.auth.AuthViewModel

@AndroidEntryPoint
class Products : Fragment() {

    lateinit var binding: FragmentProductsBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** Al pulsar sobre el botón de logout, navegamos del
         * fragment catalogo de productos al de login. */
        binding.btnLogout.setOnClickListener {
            viewModel.logout {
                findNavController().navigate(R.id.action_productsFragment_to_loginFragment)
            }
        }
    }
}