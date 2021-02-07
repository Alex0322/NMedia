package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.nId)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.nId)
            }
        })

        binding.rvList.adapter = adapter
        viewModel.ldPosts.observe(this) { lsPosts ->
            adapter.submitList(lsPosts)
        }

        viewModel.ldEdited.observe(this) { post ->
            if (post.nId == 0L) {
                return@observe
            }
            with(binding.etContent) {
                requestFocus()
                setText(post.sContent)
            }
        }

        binding.ibSave.setOnClickListener {
            with(binding.etContent) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.sErrEmptyContent),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.ibCancel.setOnClickListener {
            binding.etContent.setText("")
            binding.etContent.clearFocus()
            AndroidUtils.hideKeyboard(binding.etContent)
        }

        binding.etContent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.ibCancel.visibility  = View.VISIBLE
                binding.ibSave.visibility  = View.VISIBLE
                binding.grButtons.visibility = View.VISIBLE
            } else {
                binding.ibCancel.visibility = View.INVISIBLE
                binding.ibSave.visibility = View.INVISIBLE
                binding.grButtons.visibility = View.INVISIBLE
            }
        }
    }
}