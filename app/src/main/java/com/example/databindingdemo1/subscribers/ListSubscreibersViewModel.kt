package com.example.databindingdemo1.subscribers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberRepository

class ListSubscreibersViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers

    val navigateToOrderDetails = MutableLiveData<Subscriber>()
    fun onItemClicked(subscriber: Subscriber) {
        navigateToOrderDetails.value = subscriber
    }


}