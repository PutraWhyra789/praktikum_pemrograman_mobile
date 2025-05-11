package com.example.wuwalist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wuwalist.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterAdapter: ListCharacterAdapter
    private val list = ArrayList<Character>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        list.clear()
        list.addAll(getListCharacter())
        setupRecyclerView()

        return binding.root

    }

    private fun setupRecyclerView() {
        characterAdapter = ListCharacterAdapter(
            list,
            onWikiClick = { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            },
            onDetailClick = { name, photo, description ->
                val detailFragment = DetailFragment().apply {
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

    private fun getListCharacter(): ArrayList<Character> {
        val dataName = resources.getStringArray(R.array.nama_character)
        val dataPhoto = resources.obtainTypedArray(R.array.photo_character)
        val dataLink = resources.getStringArray(R.array.link_character)
        val dataDescription = resources.getStringArray(R.array.deskripsi_character)
        val listCharacter = ArrayList<Character>()
        for (i in dataName.indices) {
            val character = Character(dataName[i], dataLink[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listCharacter.add(character)
        }
        dataPhoto.recycle()
        return listCharacter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}