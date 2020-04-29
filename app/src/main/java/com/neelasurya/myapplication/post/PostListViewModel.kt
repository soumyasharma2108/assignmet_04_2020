package com.neelasurya.myapplication.ui.post

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.neelasurya.myapplication.R
import com.neelasurya.myapplication.base.BaseViewModel
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results
import com.neelasurya.myapplication.network.PostApi
import com.neelasurya.myapplication.utils.MAX_RESULTS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import javax.inject.Inject


class PostListViewModel(private val postDao: PostDao) : BaseViewModel() {
    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: PostListAdapter = PostListAdapter(postDao)
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    private var result = MAX_RESULTS
    val errorClickListener = View.OnClickListener {
        loadPosts(result)
    }

    init {
        loadPosts(result)
    }


    /**
     *
     */

    private fun loadPosts(page: Int) {
        CompositeDisposable().add(
                postApi.getPosts(10)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).doOnSubscribe { onRetrievePostListStart() }
                        .doOnTerminate { onRetrievePostListFinish() }
                        .subscribe(
                                { results -> onRetrievePostListSuccess(results.results) },
                                { exception -> onRetrievePostListError(exception) }))
    }

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePostListSuccess(postList: ArrayList<Results>) {
        updateDatabase(postList)
        postListAdapter.updatePostList()
    }

    private fun updateDatabase(postList: ArrayList<Results>) = postDao.apply {
        deleteAll()
        insertAll(postList)
    }

    private fun onRetrievePostListError(exception: Throwable) {
        Log.d("text", exception.localizedMessage)
        postListAdapter.updatePostList()
        if (exception is UnknownHostException) {
            errorMessage.value = R.string.server_error
        } else {
            errorMessage.value = R.string.post_error
        }
    }
}
