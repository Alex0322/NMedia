package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.truncate

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likesCount: Int = 0,
    var sharesCount: Int = 0
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(1, "Нетология1", "Привет1", "Дата1", likesCount = 500, sharesCount = 999)
        var s = getCountStr(1000)
        s = getCountStr(1050)
        s = getCountStr(1500)
        s = getCountStr(9990)
        s = getCountStr(10100)
        s = getCountStr(15000)
        s = getCountStr(900000)
        s = getCountStr(1100000)
        s = getCountStr(5865000)
        s = getCountStr(5865324)

        with(binding) {
            tvTitleAuthor.text = post.author
            tvTitleTime.text = post.published
            tvPost.text = post.content
            if (post.likedByMe) {
                ivFooterLikes?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            tvFooterLikes.text = post.likesCount.toString()
            tvFooterShares.text = post.sharesCount.toString()

            clMain.setOnClickListener {
                var s = 1
            }

            ivFooterLikes.setOnClickListener {
                var t = 1
            }

            ivTitleIcon.setOnClickListener {
                var t = 1
            }

            ivFooterLikes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                ivFooterLikes.setImageResource(
                    if(post.likedByMe) {
                        post.likesCount++
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        if (post.likesCount > 0)
                            post.likesCount--
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
                tvFooterLikes.text = post.likesCount.toString()
            }

            ivFooterShares.setOnClickListener {
                tvFooterShares.text = getCountStr(++post.sharesCount)
            }
        }
        
    }

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