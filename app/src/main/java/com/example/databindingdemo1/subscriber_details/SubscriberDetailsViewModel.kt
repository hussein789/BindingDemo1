package com.example.databindingdemo1.subscriber_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberDetailsViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val finishActivity = MutableLiveData<Boolean>()

    private var subscriber: SubscriberData? = null

    fun init(subscriber: SubscriberData?) {
        this.subscriber = subscriber?.copy()
        inputName.value = subscriber?.name
        inputEmail.value = subscriber?.email
    }

    fun onNameTextChanged(name: String) {
        inputName.value = name
    }

    fun onEmailTextChanged(email: String) {
        inputEmail.value = email
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            subscriber?.let {
                val updatedSubscriber =
                    Subscriber(subscriber?.id ?: 0, subscriber?.name ?: "", subscriber?.email ?: "")
                repository.delete(updatedSubscriber)
                finishActivity.value = true
            }
        }
    }

    fun onUpdateClicked() {
        viewModelScope.launch {
            val updatedSubscriber = Subscriber(subscriber?.id?:0, inputName.value ?: "", inputEmail.value ?: "")
            repository.update(updatedSubscriber)
            finishActivity.value = true
        }
    }


}