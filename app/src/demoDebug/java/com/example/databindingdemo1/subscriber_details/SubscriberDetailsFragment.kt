package com.example.databindingdemo1.subscriber_details

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.databindingdemo1.R
import com.example.databindingdemo1.databinding.SubscriberDetailsFragmentBinding
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import kotlinx.android.parcel.Parcelize


class SubscriberDetailsFragment : Fragment() {
   companion object{
        const val SUBSCRIBER_KEY = "SUBSCRIBER_KEY"
   }

    private lateinit var viewModel: SubscriberDetailsViewModel
    private lateinit var binding:SubscriberDetailsFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SubscriberDetailsFragmentBinding.inflate(inflater,container,false)
        val dao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(dao)
        val factory = SubscriberDetailsViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(SubscriberDetailsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscriber:SubscriberData? = arguments?.getParcelable(SUBSCRIBER_KEY)
        viewModel.init(subscriber)
        observeViewModel()

    }


    private fun observeViewModel() {
        viewModel.finishActivity.observe(viewLifecycleOwner, Observer { finish ->
            finish?.let { if(finish) finishActivity() }
        })
    }

    private fun finishActivity() {
        this.findNavController().navigateUp()
    }

}

@Parcelize
data class SubscriberData(
    val id:Int,
    val name:String,
    val email:String
):Parcelable

