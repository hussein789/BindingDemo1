package com.example.databindingdemo1.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo1.R
import com.example.databindingdemo1.databinding.HomeFragmentBinding
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import com.example.databindingdemo1.subscriber_details.SubscriberData
import com.example.databindingdemo1.subscriber_details.SubscriberDetailsFragment

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var factory: SubscriberViewModelFactory
    private lateinit var adapter:SubscriberAdapter

    private lateinit var binding:HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        val subscriberDao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(subscriberDao)
        factory = SubscriberViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.subscribers.observe(viewLifecycleOwner, Observer { list ->
            list?.let { updateSubscriberList(it) }
        })
        viewModel.navigateToSubscriberDetails.observe(viewLifecycleOwner, Observer { subscriber ->
            subscriber?.let { navigateToSubscriber(it) }
        })
        viewModel.navigateToSubscribersList.observe(viewLifecycleOwner,{
            it?.let { navigateToSubscribersList(it) }
        })
        viewModel.showMainLayout.observe(viewLifecycleOwner, Observer { show ->
            show?.let {
                handleEmptyListVisibility(show)
            }
        })
    }

    private fun handleEmptyListVisibility(show: Boolean) {
        binding.emptyIncludeLayout.emptyContainer.isVisible = !show
    }

    private fun navigateToSubscribersList(navigate: Boolean) {
        if(navigate){
            this.findNavController().navigate(R.id.action_homeFragment_to_listSubscreibersFragment)
        }
    }

    private fun navigateToSubscriber(subscriber: Subscriber) {
        val updatedSubscriber = SubscriberData(subscriber.id,subscriber.name,subscriber.email)
        val bundle = bundleOf(SubscriberDetailsFragment.SUBSCRIBER_KEY to updatedSubscriber)
        this.findNavController().navigate(R.id.action_homeFragment_to_subscriberDetailsFragment,bundle)
    }

    private fun updateSubscriberList(list: List<Subscriber>) {
        adapter.setData(list)
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SubscriberAdapter(viewModel)
        binding.subscriberListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.subscriberListRecyclerView.adapter = adapter
    }
}