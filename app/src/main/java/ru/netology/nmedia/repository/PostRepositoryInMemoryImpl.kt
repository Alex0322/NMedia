package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nNextId = 1L

    private var lsPosts = listOf(
        Post(
            nId = nNextId++,
            sAuthor = "Автор1",
            sContent = "Текст1",
            sPublished = "Дата1",
            isLikedByMe = true,
            nLikesCount = 1000,
            nSharesCount = 999
        ),
        Post(
            nId = nNextId++,
            sAuthor = "Автор2",
            sContent = "Текст2",
            sPublished = "Дата2",
            isLikedByMe = true,
            nLikesCount = 1000,
            nSharesCount = 999
        ),
        Post(
            nId = nNextId++,
            sAuthor = "Автор3",
            sContent = "Текст3",
            sPublished = "Дата3",
            isLikedByMe = true,
            nLikesCount = 1000,
            nSharesCount = 999
        ),
        Post(
            nId = nNextId++,
            sAuthor = "Автор4",
            sContent = "Текст4",
            sPublished = "Дата4",
            isLikedByMe = true,
            nLikesCount = 1000,
            nSharesCount = 999
        ),
        Post(
            nId = nNextId++,
            sAuthor = "Автор5",
            sContent = "Текст5",
            sPublished = "Дата5",
            isLikedByMe = true,
            nLikesCount = 1000,
            nSharesCount = 999
        )
    ).reversed()

    private val ldPosts = MutableLiveData(lsPosts)

    override fun getAll(): LiveData<List<Post>> = ldPosts

    override fun likeById(nId: Long) {
        lsPosts = lsPosts.map {
            if (it.nId == nId) {
                val newLikesCount =
                    if (!it.isLikedByMe)
                        it.nLikesCount + 1
                    else
                        if (it.nLikesCount > 0)
                            it.nLikesCount - 1
                        else
                            it.nLikesCount
                it.copy(isLikedByMe = !it.isLikedByMe, nLikesCount = newLikesCount)
            } else it
        }
        ldPosts.value = lsPosts
    }

    override fun shareById(nId: Long) {
        lsPosts = lsPosts.map {
            if (it.nId == nId) it.copy(nSharesCount = it.nSharesCount + 10) else it
        }
        ldPosts.value = lsPosts
    }

    override fun removeById(nId: Long) {
        lsPosts = lsPosts.filter { it.nId != nId }
        ldPosts.value = lsPosts
    }

    override fun save(post: Post) {
        if (post.nId == 0L) {
            lsPosts = listOf(
                post.copy(
                    nId = nNextId++,
                    sAuthor = "Me",
                    isLikedByMe = false,
                    sPublished = "now"
                )
            ) + lsPosts
            ldPosts.value = lsPosts
            return
        }

        lsPosts = lsPosts.map {
            if (it.nId != post.nId) it else it.copy(sContent = post.sContent)
        }
        ldPosts.value = lsPosts
    }
}
