package com.example.databindingdemo1.subscribers

import android.view.View
import com.airbnb.epoxy.*
import com.example.databindingdemo1.R
import com.example.databindingdemo1.db.Subscriber
import kotlinx.android.synthetic.main.subscriber_item_layout.view.*

@EpoxyModelClass(layout = R.layout.subscriber_item_layout)
abstract class SubscriberItemEpoxy : EpoxyModelWithHolder<SubscriberItemEpoxy.Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var subscriber: Subscriber

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.view){
            subscriberNameTextView.text = subscriber.name
            subscriberEmailTextView.text = subscriber.email
        }
    }

    inner class Holder : EpoxyHolder(){
        lateinit var view:View
        override fun bindView(itemView: View) {
            view = itemView
        }

    }

}