package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.truncate

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likesCount: Int,
    val sharesCount: Int
)

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Автор1",
        content = "Текст1",
        published = "Дата1",
        likedByMe = true,
        likesCount = 1000,
        sharesCount = 999
    )
    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data
    override fun like() {
        val newLikesCount =
            if (!post.likedByMe)
                post.likesCount + 1
            else
                if (post.likesCount > 0)
                    post.likesCount - 1
                else
                    post.likesCount
        post = post.copy(
            likedByMe = !post.likedByMe,
            likesCount = newLikesCount)
        data.value = post
    }
    override fun share(){
        post = post.copy(sharesCount = post.sharesCount + 10)
        data.value = post
    }
}

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    fun share() = repository.share()
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this, Observer { post ->
            with(binding) {
                tvTitleAuthor.text = post.author
                tvTitleTime.text = post.published
                tvPost.text = post.content
                ivFooterLikes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                tvFooterLikes.text = viewModel.getCountStr(post.likesCount)
                tvFooterShares.text = viewModel.getCountStr(post.sharesCount)
            }
        })

        binding.ivFooterLikes.setOnClickListener {
            viewModel.like()
        }

        binding.ivFooterShares.setOnClickListener {
            viewModel.share()
        }

    }

}
