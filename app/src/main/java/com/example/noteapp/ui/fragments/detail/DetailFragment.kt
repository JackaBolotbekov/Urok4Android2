package com.example.noteapp.ui.fragments.detail

import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.App
import com.example.noteapp.databinding.FragmentDetailBinding
import com.example.noteapp.model.NoteModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var model: NoteModel
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Говорит о том что роботает с версией андроида выше 8ми
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date()
        editText()
        setUpListener()
        getModel()
    }

    //Говорит о том что роботает с версией андроида выше 8ми
    @RequiresApi(Build.VERSION_CODES.O)
    private fun date() {
        // Следующие 4 стр нужны для того чтобы сделать DATE
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM HH:mm")
        val date = current.format(formatter)
        binding.txtDateInDetailFragment.text = date.toString()
    }

    // Видимость нашего "Готово"
    private fun editText() = with(binding) {
        etInDetailFragment.addTextChangedListener{
            txtFinish.isInvisible = etInDetailFragment.text.trim().isEmpty()
        }
    }

    //Говорит о том что роботает с версией андроида выше 8ми
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpListener() = with(binding) {
        viewBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Нажатия на кнопки, что по идее должны менять цвет
        btnGreyColor.setOnClickListener { orange1.isVisible = true
            orange2.isInvisible = true
            orange3.isInvisible = true}
        btnWhiteColor.setOnClickListener { orange2.isVisible = true
            orange1.isInvisible = true
            orange3.isInvisible = true}
        btnRedColor.setOnClickListener { orange3.isVisible = true
            orange1.isInvisible = true
            orange2.isInvisible = true}

        // Нажатие на "Готово"
        txtFinish.setOnClickListener {
            if (!args.modelChanged){
                val title = txtTitle.text.toString()
                val description = etInDetailFragment.text.toString()
                val date = txtDateInDetailFragment.text.toString()
                // Здесь он создает
                App.getInstance( )?.noteDao()?.insertNote(NoteModel(title, description, date))
                findNavController().navigateUp()
            }else{
                val title = txtTitle.text.toString()
                val description = etInDetailFragment.text.toString()
                val date = txtDateInDetailFragment.text.toString()
                args.model?.title = title
                args.model?.description = description
                args.model?.date = date
                model = args.model!!
                // Здесь он меняет
                App.getInstance( )?.noteDao()?.changedItem(model)
                findNavController().navigateUp()
            }
        }
    }

    // Метод что принимает нашу Model с NoteAppFragment на DetailFragment
    private fun getModel() = with(binding) {
        txtTitle.setText(args.model?.title)
        etInDetailFragment.setText(args.model?.description)
    }
}