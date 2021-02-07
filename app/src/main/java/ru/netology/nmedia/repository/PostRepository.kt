package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(nId: Long)
    fun shareById(nId: Long)
    fun removeById(nId: Long)
    fun save(post: Post)
}

