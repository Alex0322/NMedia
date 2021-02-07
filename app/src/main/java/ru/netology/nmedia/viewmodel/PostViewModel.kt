package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    nId = 0,
    sContent = "",
    sAuthor = "",
    isLikedByMe = false,
    sPublished = "",
    nLikesCount = 0,
    nSharesCount = 0
)

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val ldPosts = repository.getAll()
    val ldEdited = MutableLiveData(empty)

    fun likeById(nId: Long) = repository.likeById(nId)

    fun shareById(nId: Long) = repository.shareById(nId)

    fun removeById(nId: Long) = repository.removeById(nId)

    fun save (){
        ldEdited.value?.let {
            repository.save(it)
        }
        ldEdited.value = empty
    }

    fun edit(post: Post) {
        ldEdited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (ldEdited.value?.sContent == text) {
            return
        }
        ldEdited.value = ldEdited.value?.copy(sContent = text)
    }
}
