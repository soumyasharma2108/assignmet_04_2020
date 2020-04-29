package com.neelasurya.myapplication.injection


import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room.databaseBuilder
import com.neelasurya.myapplication.model.database.AppDatabase
import com.neelasurya.myapplication.ui.post.PostListViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            val db = databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").allowMainThreadQueries().build()
            @Suppress("UNCHECKED_CAST")
            return PostListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}