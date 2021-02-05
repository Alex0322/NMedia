package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import kotlin.math.truncate

open class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)

    fun shareById(id: Long) = repository.shareById(id)

    companion object {
        fun getCountStr(count: Int): String {
            return when (count) {
                in 0..999 -> count.toString() //100
                in 1000..1099 -> "1K"
                in 1100..9999 -> (truncate(count.toDouble() / 1000 * 10) / 10).toString() + "K" //1.1K
                in 10000..999999 -> (count / 1000).toString() + "K" //10K
                else -> (count / 1000000).toString() + "M" //1.1M
            }
        }
    }
}
