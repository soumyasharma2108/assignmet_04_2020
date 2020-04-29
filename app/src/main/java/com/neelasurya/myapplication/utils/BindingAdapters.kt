package com.neelasurya.myapplication.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neelasurya.myapplication.R
import com.neelasurya.myapplication.post.PostViewModel
import com.neelasurya.myapplication.utils.extension.getParentActivity

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value ->
            view.visibility = value ?: View.VISIBLE
        })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}

@BindingAdapter("mutableImage")
fun setMutableImage(view: ImageView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer {
            Glide.with(parentActivity).load(it).into(view)
        })
    }
}
@BindingAdapter("isForAccepted")
fun isForAccepted(view: TextView, post: PostViewModel?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && post?.getPostIsAccepted() != null && post?.getPostIsRejected() != null) {
        post?.getPostIsAccepted().observe(parentActivity, Observer { value -> view.text = if (value) parentActivity?.resources?.getString(R.string.accepted) else parentActivity?.resources?.getString(R.string.accept) })
        post?.getPostIsRejected().observe(parentActivity, Observer { value -> view.visibility = if (value) View.GONE else View.VISIBLE })

    }}
@BindingAdapter("isForRejected")
fun isForRejected(view: TextView, post: PostViewModel?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && post?.getPostIsAccepted() != null && post?.getPostIsRejected() != null) {
        post?.getPostIsRejected().observe(parentActivity, Observer { value -> view.text = if (value) parentActivity?.resources?.getString(R.string.decline) else parentActivity?.resources?.getString(R.string.reject) })
        post?.getPostIsAccepted().observe(parentActivity, Observer { value -> view.visibility = if (value) View.GONE else View.VISIBLE })
    }
}

