package com.example.monsterhunterarmor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.monsterhunterarmor.R
import com.example.monsterhunterarmor.data.local.ArmorEntity
import com.example.monsterhunterarmor.databinding.ItemArmorBinding

class ArmorAdapter(
    private val listener: OnArmorClickListener
) : ListAdapter<ArmorEntity, ArmorAdapter.ArmorViewHolder>(DIFF_CALLBACK) {

    interface OnArmorClickListener {
        fun onDetailClick(armor: ArmorEntity)
        fun onSearchClick(armor: ArmorEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArmorViewHolder {
        val binding = ItemArmorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArmorViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ArmorViewHolder, position: Int) {
        val armor = getItem(position)
        holder.bind(armor)
    }

    class ArmorViewHolder(
        private val binding: ItemArmorBinding,
        private val listener: OnArmorClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(armor: ArmorEntity) {
            binding.tvArmorName.text = armor.name
            binding.tvArmorInfo.text = "Rank: ${armor.rank} | Type: ${armor.type}"

            Glide.with(itemView.context)
                .load(armor.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivArmorPhoto)

            binding.btnDetail.setOnClickListener {
                listener.onDetailClick(armor)
            }

            binding.btnSearch.setOnClickListener {
                listener.onSearchClick(armor)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArmorEntity>() {
            override fun areItemsTheSame(oldItem: ArmorEntity, newItem: ArmorEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArmorEntity, newItem: ArmorEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}