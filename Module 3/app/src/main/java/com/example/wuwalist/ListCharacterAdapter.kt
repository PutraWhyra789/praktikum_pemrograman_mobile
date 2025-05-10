package com.example.wuwalist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListCharacterAdapter(
    private val listCharacter: ArrayList<Character>,
    private val onWikiClick: (String) -> Unit,
    private val onDetailClick: (String, Int, String) -> Unit)
    : RecyclerView.Adapter<ListCharacterAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_character)
        val tvName: TextView = itemView.findViewById(R.id.tv_character)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tv_deskripsi)
        val tvProfile: TextView = itemView.findViewById(R.id.profile_detail_character)
        val btnWiki: Button = itemView.findViewById(R.id.btn_link)
        val btnDetail: Button = itemView.findViewById(R.id.btn_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listCharacter.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo, link, description, profile) = listCharacter[position]
        holder.tvName.text = name
        holder.imgPhoto.setImageResource(photo)
        holder.tvDeskripsi.text = description
        holder.tvProfile.text = profile
        holder.btnWiki.setOnClickListener { onWikiClick(link) }
        holder.btnDetail.setOnClickListener { onDetailClick(name, photo, profile) }
    }
}