package com.example.databindingdemo1.subscriber_details

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databindingdemo1.R

class SubscriberDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subscriber_details_layout)
        val subscriber:SubscriberData? = intent.getParcelableExtra(SubscriberDetailsFragment.SUBSCRIBER_KEY)
        val fragment = SubscriberDetailsFragment.newInstance(subscriber)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer,fragment)
            .commit()
    }

}