package com.example.databindingdemo1.subscriber_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.databindingdemo1.db.SubscriberRepository
import java.lang.IllegalArgumentException

class SubscriberDetailsViewModelFactory(private val repository: SubscriberRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberDetailsViewModel::class.java))
            return SubscriberDetailsViewModel(repository) as T
        throw IllegalArgumentException("Unknown view model found")
    }
}