package com.example.wuwalist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wuwalist.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val name = arguments?.getString("EXTRA_NAME")
        val photo = arguments?.getInt("EXTRA_PHOTO")
        val description = arguments?.getString("EXTRA_DESCRIPTION")


        binding.tvCharacter.text = name
        binding.tvDeskripsi.text = description
        photo?.let {
            binding.imgCharacter.setImageResource(it)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}