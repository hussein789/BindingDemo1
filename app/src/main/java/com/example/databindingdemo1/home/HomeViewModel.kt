package com.example.databindingdemo1.home

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: SubscriberRepository) : ViewModel() {

    val subscribers = repo.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val navigateToSubscriberDetails = MutableLiveData<Subscriber>()
    val showMainLayout = Transformations.map(subscribers){
        it.isNotEmpty()
    }

    val saveOrUpdateBtnText = MutableLiveData<String>()
    val clearAllOrDeleteText = MutableLiveData<String>()

    init {
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteText.value = "Clear All"
    }

    fun onSaveOrUpdateClicked(){
        val name = inputName.value ?: ""
        val email = inputEmail.value ?: ""
        if(name.isNotEmpty() && email.isNotEmpty()){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                val subscriber = Subscriber(0,name,email)
                insert(subscriber)
                clearFields()
            }
        }
    }

    fun onClearAllOrDeleteClicked(){
        clearAll()
        clearFields()
    }

    val clearName = MutableLiveData<Boolean>()
    val clearEmail = MutableLiveData<Boolean>()
    private fun clearFields() {
       clearName.value = true
        clearEmail.value = true
    }

    fun clearAll(){
        viewModelScope.launch {
            repo.clearAll()
        }
    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repo.insert(subscriber)
        }
    }

    fun updateName(text: CharSequence?) {
        text?.let {
            if(it.isNotEmpty()){
                inputName.value = it.toString()
            }
        }
    }

    fun updateEmail(text: CharSequence?) {
        text?.let {
            if(it.isNotEmpty()){
                inputEmail.value = it.toString()
            }
        }
    }

    fun onItemClicked(subscriber: Subscriber) {
        navigateToSubscriberDetails.value = subscriber
    }


}