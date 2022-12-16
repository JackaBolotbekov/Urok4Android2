package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.App
import com.example.noteapp.databinding.FragmentNoteAppBinding
import com.example.noteapp.model.NoteModel
import com.example.noteapp.ui.adapter.NoteAdapter

class NoteAppFragment : Fragment() {

    private lateinit var binding: FragmentNoteAppBinding
    private var noteAdapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setUpListener()
        getData()
    }

    private fun initialize() {
        noteAdapter = NoteAdapter(this::onClickListener)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setUpListener() = with(binding) {
        fabAdd.setOnClickListener {
            findNavController().navigate(NoteAppFragmentDirections
                .actionNoteAppFragmentToDetailFragment(null,false))
        }
        viewGrid.setOnClickListener {
            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                viewGrid.isInvisible = true
                viewLinear.isVisible = true
            }
        }
        viewLinear.setOnClickListener {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                viewLinear.isInvisible = true
                viewGrid.isVisible = true
            }
        }
    }

    // Метод что при нажтии на Item передает информацию с помощью Model
    private fun onClickListener(model: NoteModel){
        findNavController().navigate(NoteAppFragmentDirections
            .actionNoteAppFragmentToDetailFragment(model, true))
    }

    private fun getData() {
        App.getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner){
            noteAdapter.setList(it)
        }
    }
}