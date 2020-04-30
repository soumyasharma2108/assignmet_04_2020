package com.neelasurya.myapplication.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neelasurya.myapplication.R
import com.neelasurya.myapplication.databinding.ItemPostBinding
import com.neelasurya.myapplication.model.PostDao
import com.neelasurya.myapplication.model.Results


class PostListAdapter(private val postDao: PostDao) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList: List<Results>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position], position, postDao)
    }

    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(list:List<Results>) {
        postList = list
        notifyDataSetChanged()
    }
    
    inner class ViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var viewModel: PostViewModel
        fun bind(post: Results, position: Int, postDao: PostDao) {
            viewModel = PostViewModel(postDao)
            viewModel.bind(post)
            binding.viewModel = viewModel
            binding.position = position
            binding.callback = this@PostListAdapter
            binding.executePendingBindings()
        }

    }

    fun onAcceptClick(view: View?, position: Int, viewModel: PostViewModel) {
        viewModel.onAcceptUpdate(postList[position])
        notifyItemChanged(position)
    }

    fun onRejectClick(view: View?, position: Int, viewModel: PostViewModel) {
        viewModel.onRejectUpdate(postList[position])
        notifyItemChanged(position)
    }

}
