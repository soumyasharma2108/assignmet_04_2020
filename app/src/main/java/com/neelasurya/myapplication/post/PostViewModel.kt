package com.neelasurya.myapplication.post

import androidx.lifecycle.MutableLiveData
import com.neelasurya.myapplication.base.BaseViewModel
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val postDao: PostDao) : BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postInfo = MutableLiveData<String>()
    private val postAddress = MutableLiveData<String>()
    private val postNumber = MutableLiveData<String>()
    private val postEmail = MutableLiveData<String>()
    private val postImage = MutableLiveData<String>()
    private var postIsAccepted = MutableLiveData<Boolean>()
    private var postIsRejected = MutableLiveData<Boolean>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    fun bind(post: Results) {
        post.apply {
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


    fun onAcceptUpdate(results: Results) {
        results.isAccepted = true
        compositeDisposable.add(Completable.fromCallable { postDao.updateResult(results) }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe())
    }

    fun onRejectUpdate(results: Results) = results.apply {
        isRejected = true
        compositeDisposable.add(Completable.fromCallable { postDao.updateResult(results) }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}