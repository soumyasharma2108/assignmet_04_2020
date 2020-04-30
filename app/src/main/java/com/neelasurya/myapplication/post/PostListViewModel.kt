package com.neelasurya.myapplication.ui.post

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.neelasurya.myapplication.R
import com.neelasurya.myapplication.base.BaseViewModel
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results
import com.neelasurya.myapplication.network.PostApi
import com.neelasurya.myapplication.post.PostListAdapter
import com.neelasurya.myapplication.utils.MAX_RESULTS
import io.reactivex.Flowable
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
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val errorClickListener = View.OnClickListener {
        loadPosts(result)
    }

    init {
        loadPosts(result)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * We will make the call to the api if it throws error or not able to connect through internet
     * we will show data from the database.
     */

    private fun loadPosts(page: Int) {
        compositeDisposable.add(
                postApi.getPosts(page)
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

    private fun onRetrievePostListSuccess(postList: ArrayList<Results>) =
            postDao.apply {
                compositeDisposable.add(Flowable.fromCallable {
                    deleteAll()
                    insertAll(postList)
                    all
                }.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io()).subscribe({ list -> postListAdapter.updatePostList(list) },
                                { exception -> onRetrievePostListError(exception) }))
            }


    private fun onRetrievePostListError(exception: Throwable) {
        compositeDisposable.add(Flowable.fromCallable {
            postDao.all
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { list -> postListAdapter.updatePostList(list) })
        if (exception is UnknownHostException) {
            errorMessage.value = R.string.server_error
        } else {
            errorMessage.value = R.string.post_error
        }
    }
}
