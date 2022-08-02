package com.example.solid.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.solid.R
import com.example.solid.presentation.post_view_model.PostEvent
import com.example.solid.presentation.post_view_model.PostViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPostBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var etTitle: EditText
    private lateinit var etBody: EditText
    private lateinit var addPostButton: Button
    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_add_post_bottom_sheet,
            container,
            false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[PostViewModel::class.java]
        etTitle = view.findViewById(R.id.etTitle)
        etBody = view.findViewById(R.id.etBody)

        addPostButton = view.findViewById<Button?>(R.id.btnAdd).apply {
            setOnClickListener { onAddPostClicked() }
        }
    }

    private fun onAddPostClicked() {
        val title = etTitle.text.toString()
        val body = etBody.text.toString()
        viewModel.add(PostEvent.AddPost(title, body))
    }

    private fun clearFields() {
        etTitle.text.clear()
        etBody.text.clear()
    }

    fun closeDialog() {
        if (!isVisible) return
        dismiss()
        clearFields()
    }

}