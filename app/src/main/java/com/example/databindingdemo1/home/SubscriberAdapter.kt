package com.example.databindingdemo1.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databindingdemo1.R
import com.example.databindingdemo1.db.Subscriber
import kotlinx.android.synthetic.main.subscriber_item_layout.view.*

class SubscriberAdapter :  RecyclerView.Adapter<SubscriberViewHolder>(){

    private var subscriberList = mutableListOf<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.subscriber_item_layout,parent,false)
        return SubscriberViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        val subscriber = subscriberList[position]
        holder.bind(subscriber)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

    fun setData(subscribers:List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(subscribers)
        notifyDataSetChanged()
    }
}

class SubscriberViewHolder(val view: View) : RecyclerView.ViewHolder(view){

    fun bind(subscriber: Subscriber){
        with(view){
            subscriberNameTextView.text = subscriber.name
            subscriberEmailTextView.text = subscriber.email
        }
    }
}