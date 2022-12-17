package com.example.noteapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ItemCountBinding
import com.example.noteapp.model.NoteModel

class NoteAdapter(private val setupListener: ((NoteModel) -> Unit?))
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var list: List<NoteModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<NoteModel>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(string: NoteModel){
            binding.txtDescriptionNoteFragment.text = string.description
            binding.txtTitleNoteFragment.text = string.title
            binding.txtDateLinerNoteFragment.text = string.date
        }

        init {
            itemView.setOnClickListener {
                setupListener(list[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCountBinding.inflate(LayoutInflater.from(
            parent.context),
        parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}