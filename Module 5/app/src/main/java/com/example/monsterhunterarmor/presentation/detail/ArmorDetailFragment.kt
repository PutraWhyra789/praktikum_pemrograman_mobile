package com.example.monsterhunterarmor.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.monsterhunterarmor.data.model.ArmorSkill
import com.example.monsterhunterarmor.databinding.FragmentArmorDetailBinding

class ArmorDetailFragment : Fragment() {

    private var _binding: FragmentArmorDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ArmorDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArmorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val armor = args.armor

        Glide.with(this)
            .load(armor.imageUrl)
            .into(binding.ivArmorDetailPhoto)

        binding.tvArmorDetailName.text = armor.name

        val defense = armor.defense
        binding.tvDefenseStats.text = "Base: ${defense.base} | Max: ${defense.max} | Augmented: ${defense.augmented}"

        val res = armor.resistances
        binding.tvResistancesStats.text = "Fire: ${res.fire}, Water: ${res.water}, Thunder: ${res.thunder}, Ice: ${res.ice}, Dragon: ${res.dragon}"

        addSkillsToLayout(armor.skills)
    }

    private fun addSkillsToLayout(skills: List<ArmorSkill>) {
        if (skills.isEmpty()) {
            val noSkillsText = TextView(context).apply {
                text = "No skills available."
                setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Body2)
            }
            binding.llSkillsContainer.addView(noSkillsText)
            return
        }

        skills.forEach { skill ->
            val skillTitle = TextView(context).apply {
                text = "${skill.skillName} (Lv. ${skill.level})"
                setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Subtitle1)
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = if (binding.llSkillsContainer.childCount > 1) 24 else 8
                }
            }

            val skillDescription = TextView(context).apply {
                text = skill.description
                setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Body2)
            }

            binding.llSkillsContainer.addView(skillTitle)
            binding.llSkillsContainer.addView(skillDescription)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}