package com.example.databindingdemo1.subscribers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo1.R
import com.example.databindingdemo1.databinding.ListSubscreibersFragmentBinding
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import com.example.databindingdemo1.subscriber_details.SubscriberData
import com.example.databindingdemo1.subscriber_details.SubscriberDetailsFragment
import kotlinx.android.synthetic.main.list_subscreibers_fragment.*

class ListSubscreibersFragment : Fragment() {

    private lateinit var viewModel: ListSubscreibersViewModel
    lateinit var controller:SubscriberListController
    private lateinit var binding:ListSubscreibersFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = ListSubscreibersFragmentBinding.inflate(inflater,container,false)
        val dao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(dao)
        val factory = ListSubscriberViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(ListSubscreibersViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.subscribers.observe(viewLifecycleOwner, Observer { list ->
            list?.let { updateSubscriberList(list) }
        })
        viewModel.navigateToOrderDetails.observe(viewLifecycleOwner, Observer { navigation ->
            navigation?.let { navigateToOrderDetails(it) }
        })
        viewModel.showMainLayout.observe(viewLifecycleOwner, Observer { show ->
            show?.let { handleMainLayout(show) }
        })
    }

    private fun handleMainLayout(show: Boolean) {
        binding.emptyIncludeLayout.emptyContainer.isVisible = !show
    }

    private fun navigateToOrderDetails(subscriber: Subscriber) {
        val updatedSubscriber = SubscriberData(subscriber.id,subscriber.name,subscriber.email)
        val bundle = bundleOf(SubscriberDetailsFragment.SUBSCRIBER_KEY to updatedSubscriber)
        this.findNavController().navigate(R.id.action_homeFragment_to_subscriberDetailsFragment,bundle)
    }

    private fun updateSubscriberList(list: List<Subscriber>) {
        controller.setData(list)
    }

    private fun initViews() {
        controller = SubscriberListController()
        controller.viewModel = viewModel
        subscribersRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        subscribersRecyclerView.adapter = controller.adapter
    }


}