package com.example.monsterhunterarmor.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monsterhunterarmor.ArmorApplication
import com.example.monsterhunterarmor.adapter.ArmorAdapter
import com.example.monsterhunterarmor.data.local.ArmorEntity
import com.example.monsterhunterarmor.data.remote.ApiResponse
import com.example.monsterhunterarmor.databinding.FragmentArmorListBinding
import com.example.monsterhunterarmor.utils.ViewModelFactory
import kotlinx.coroutines.launch

class ArmorListFragment : Fragment() {

    private var _binding: FragmentArmorListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArmorViewModel by viewModels {
        ViewModelFactory((requireActivity().application as ArmorApplication).appContainer.armorRepository)
    }

    private val armorAdapter = ArmorAdapter(object : ArmorAdapter.OnArmorClickListener {
        override fun onDetailClick(armor: ArmorEntity) {
            viewModel.onDetailButtonClicked(armor)
        }

        override fun onSearchClick(armor: ArmorEntity) {
            viewModel.onSearchButtonClicked(armor)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArmorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeArmorData()
        observeViewEvents()
    }

    private fun setupRecyclerView() {
        binding.rvArmor.apply {
            adapter = armorAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeArmorData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.armorState.collect { response ->
                    when (response) {
                        is ApiResponse.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        is ApiResponse.Success -> {
                            response.data.collect { armorList ->
                                binding.progressBar.isVisible = false
                                armorAdapter.submitList(armorList)
                                if (armorList.isNotEmpty()) {
                                    Log.d("ArmorListFragment", "${armorList.size} items submitted.")
                                }
                            }
                        }
                        is ApiResponse.Error -> {
                            binding.progressBar.isVisible = false
                            Toast.makeText(context, response.errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeViewEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect { event ->
                    when (event) {
                        is ArmorViewModel.ViewEvent.NavigateToDetail -> {
                            Log.d("ArmorListFragment", "Navigating to detail for: ${event.armor.name} (ID: ${event.armor.id})")
                            val action = ArmorListFragmentDirections.actionArmorListFragmentToArmorDetailFragment(event.armor)
                            findNavController().navigate(action)
                        }
                        is ArmorViewModel.ViewEvent.OpenBrowser -> {
                            val query = "https://monsterhunterworld.wiki.fextralife.com/${event.query.replace(" ", "+")}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvArmor.adapter = null
        _binding = null
    }
}