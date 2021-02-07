package ru.netology.nmedia.dto

data class Post(
    val nId: Long,
    val sAuthor: String,
    val sContent: String,
    val sPublished: String,
    val isLikedByMe: Boolean,
    val nLikesCount: Int,
    val nSharesCount: Int
)