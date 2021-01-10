package com.example.databindingdemo1.subscribers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberRepository
import com.example.databindingdemo1.utils.SingleLiveEvent

class ListSubscreibersViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    val navigateToOrderDetails = SingleLiveEvent<Subscriber>()

    val showMainLayout = Transformations.map(subscribers){
        it.isNotEmpty()
    }

    fun onItemClicked(subscriber: Subscriber) {
        navigateToOrderDetails.value = subscriber
    }


}