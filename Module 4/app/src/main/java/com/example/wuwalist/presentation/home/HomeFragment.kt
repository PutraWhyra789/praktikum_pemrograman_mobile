package com.example.wuwalist.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wuwalist.R
import com.example.wuwalist.adapter.ListCharacterAdapter
import com.example.wuwalist.databinding.FragmentHomeBinding
import com.example.wuwalist.utils.HomeViewModelFactory
import com.example.wuwalist.presentation.detail.DetailFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import java.util.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterAdapter: ListCharacterAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(resources)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCharacterList()

        viewModel.loadCharacters()
    }

    private fun setupRecyclerView() {
        characterAdapter = ListCharacterAdapter(
            ArrayList(),
            onWikiClick = { link ->

                Log.e("Explicit intent to Web","Going to $link")

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)

            },
            onDetailClick = { name, photo, description ->
                val detailFragment = DetailFragment().apply {



                    Log.e("Move to detail page","move to $name detail page")

                    arguments = Bundle().apply {
                        putString("EXTRA_NAME", name)
                        putInt("EXTRA_PHOTO", photo)
                        putString("EXTRA_DESCRIPTION", description)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.rvCharacter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeCharacterList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characterList.collectLatest { list ->
                characterAdapter.setData(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}