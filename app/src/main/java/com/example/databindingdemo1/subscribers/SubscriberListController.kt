package com.example.databindingdemo1.subscribers

import com.airbnb.epoxy.EpoxyController
import com.example.databindingdemo1.db.Subscriber

class SubscriberListController : EpoxyController() {

    private  var subscribers = mutableListOf<Subscriber>()
    lateinit var viewModel:ListSubscreibersViewModel

    fun setData(subscribeList:List<Subscriber>){
        subscribers.clear()
        subscribers.addAll(subscribeList)
        requestModelBuild()
    }

    override fun buildModels() {
        subscribers?.let {
            subscribers.forEachIndexed { index, subscriber ->
                subscriberItem {
                    id(subscriber.id.toString(),subscriber.name,subscriber.email)
                    subscriber(subscriber)
                    viewModel(viewModel)
                }
            }
        }
    }
}