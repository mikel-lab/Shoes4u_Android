package io.guardiaimperial.shoes4u.presentation.products

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.guardiaimperial.shoes4u.R
import io.guardiaimperial.shoes4u.databinding.FragmentProductListBinding
import io.guardiaimperial.shoes4u.presentation.auth.AuthViewModel
import io.guardiaimperial.shoes4u.utils.Response
import io.guardiaimperial.shoes4u.utils.toast


@AndroidEntryPoint
class ProductListFragment : Fragment(){

    val TAG: String = "ProductListFragment"

    lateinit var binding: FragmentProductListBinding
    val viewModel: ProductViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val adapter by lazy {
        ProductListAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment,
                    Bundle().apply {
                        putParcelable("product", item)
                    })
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentProductListBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_logout -> {
                        authViewModel.logout {
                            findNavController().navigate(R.id.action_productListFragment_to_loginFragment)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        observer()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(
            2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
        binding.recyclerView.adapter = adapter

        binding.btnLogout.setOnClickListener {

        }
        authViewModel.getSession {
            viewModel.getProducts(it)
        }
    }

    private fun observer() {
        viewModel.getProduct.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Response.Loading -> {
                    binding.pbProductList.visibility = View.VISIBLE
                }
                is Response.Failure -> {
                    binding.pbProductList.visibility = View.GONE
                    toast(state.error)
                }
                is Response.Success -> {
                    binding.pbProductList.visibility = View.GONE
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }
}
