package io.guardiaimperial.shoes4u.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentBlankBinding
import io.guardiaimperial.shoes4u.presentation.viewmodel.AuthViewModel

@AndroidEntryPoint
class BlankFragment : Fragment() {

    lateinit var binding: FragmentBlankBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            viewModel.logout {
                findNavController().navigate(R.id.action_blankFragment_to_loginFragment)
            }
        }
    }
}