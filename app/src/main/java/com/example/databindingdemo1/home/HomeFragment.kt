package com.example.databindingdemo1.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo1.R
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import com.example.databindingdemo1.subscriber_details.SubscriberData
import com.example.databindingdemo1.subscriber_details.SubscriberDetailsFragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var factory: SubscriberViewModelFactory
    private lateinit var adapter:SubscriberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.home_fragment, container, false)
        val subscriberDao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(subscriberDao)
        factory = SubscriberViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickedListener()
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.subscribers.observe(viewLifecycleOwner, Observer { list ->
            list?.let { updateSubscriberList(it) }
        })
        viewModel.clearName.observe(viewLifecycleOwner, Observer {clear ->
            clear?.let { nameEditText.setText("") }
        })
        viewModel.clearEmail.observe(viewLifecycleOwner, Observer { clear ->
            clear?.let { emailEditText.setText("") }
        })
        viewModel.navigateToSubscriberDetails.observe(viewLifecycleOwner, Observer { subscriber ->
            subscriber?.let { navigateToSubscriber(it) }
        })
        viewModel.showMainLayout.observe(viewLifecycleOwner, Observer { show ->
            show?.let { handleMainLayout(it) }
        })
    }

    private fun handleMainLayout(show: Boolean) {
        subscriberListRecyclerView.visibility = if(show) View.VISIBLE else View.GONE
        noSubscribersLayout.visibility = if(show) View.GONE else View.VISIBLE

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
        nameEditText.doOnTextChanged { text, start, before, count ->
            viewModel.updateName(text)
        }
        emailEditText.doOnTextChanged { text, start, before, count ->
            viewModel.updateEmail(text)
        }
        allSubscribersButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_listSubscreibersFragment)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = SubscriberAdapter(viewModel)
        subscriberListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        subscriberListRecyclerView.adapter = adapter
    }

    private fun initClickedListener() {
        saveButton.setOnClickListener {
            viewModel.onSaveOrUpdateClicked()
        }

        clearAllButton.setOnClickListener {
            viewModel.onClearAllOrDeleteClicked()
        }

    }

}