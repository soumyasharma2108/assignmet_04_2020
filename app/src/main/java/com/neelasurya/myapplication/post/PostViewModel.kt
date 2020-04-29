package com.neelasurya.myapplication.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.neelasurya.myapplication.base.BaseViewModel
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results

class PostViewModel(private val postDao: PostDao) : BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postInfo = MutableLiveData<String>()
    private val postAddress = MutableLiveData<String>()
    private val postNumber = MutableLiveData<String>()
    private val postEmail = MutableLiveData<String>()
    private val postImage = MutableLiveData<String>()
    private var postIsAccepted = MutableLiveData<Boolean>()
    private var postIsRejected = MutableLiveData<Boolean>()


    fun bind(post: Results) {
        post?.apply {
            postInfo.value = gender + ", " + registered?.age
            name?.apply { postTitle.value = "$title $first $last" }
            location?.apply { postAddress.value = "$city , $country" }
            postNumber.value = "$cell, $phone"
            postEmail.value = email
            postImage.value = picture?.large
            postIsRejected.value = isRejected
            postIsAccepted.value = isAccepted
        }
    }

    fun getPostIsRejected(): MutableLiveData<Boolean> {
        return postIsRejected
    }

    fun getPostIsAccepted(): MutableLiveData<Boolean> {
        return postIsAccepted
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostImage(): MutableLiveData<String> {
        return postImage
    }

    fun getPostInfo(): MutableLiveData<String> {
        return postInfo
    }

    fun getPostAddress(): MutableLiveData<String> {
        return postAddress
    }

    fun getPostNumber(): MutableLiveData<String> {
        return postNumber
    }

    fun getPostEmail(): MutableLiveData<String> {
        return postEmail
    }


    fun onAcceptUpdate(results: Results, pos: Int) {
        try {
            results.isAccepted = true
            postDao.updateResult(results)
            Log.d("meeeeeeeeeeeeee", "" + postDao.all[pos].isAccepted)
        } catch (e: Exception) {
            Log.d("test", e.message)
        }
    }

    fun onRejectUpdate(results: Results) = results.apply {
        isRejected = true
        postDao.updateResult(this)
    }
}