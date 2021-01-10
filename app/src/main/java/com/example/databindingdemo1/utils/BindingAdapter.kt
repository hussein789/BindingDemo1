package com.example.databindingdemo1.utils

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("android:isVisible")
fun isVisible(view:View,isVisible:Boolean){
    view.visibility = if(isVisible) View.VISIBLE else View.GONE
}