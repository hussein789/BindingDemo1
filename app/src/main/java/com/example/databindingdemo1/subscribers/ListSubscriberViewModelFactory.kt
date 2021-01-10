package com.example.databindingdemo1.subscribers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.databindingdemo1.db.SubscriberRepository
import java.lang.IllegalArgumentException

class ListSubscriberViewModelFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListSubscreibersViewModel::class.java))
            return ListSubscreibersViewModel(repository) as T
        throw IllegalArgumentException("")
    }
}