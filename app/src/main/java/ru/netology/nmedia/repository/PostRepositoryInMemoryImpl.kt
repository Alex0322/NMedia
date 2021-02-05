package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var lsPosts = listOf(
        Post(
            id = 1,
            author = "Автор1",
            content = "Текст1",
            published = "Дата1",
            likedByMe = true,
            likesCount = 1000,
            sharesCount = 999
        ),
        Post(
            id = 2,
            author = "Автор2",
            content = "Текст2",
            published = "Дата2",
            likedByMe = true,
            likesCount = 1000,
            sharesCount = 999
        )
    )

    private val data = MutableLiveData(lsPosts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        lsPosts = lsPosts.map {
            if (it.id == id) {
                val newLikesCount =
                    if (!it.likedByMe)
                        it.likesCount + 1
                    else
                        if (it.likesCount > 0)
                            it.likesCount - 1
                        else
                            it.likesCount
                it.copy(likedByMe = !it.likedByMe, likesCount = newLikesCount)
            } else it
        }
        data.value = lsPosts
    }

    override fun shareById(id: Long) {
        val posts = lsPosts.map {
            if (it.id == id) it.copy(sharesCount = it.sharesCount + 10) else it
        }
        data.value = posts
    }
}
