package com.example.databindingdemo1.subscribers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo1.R
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import kotlinx.android.synthetic.main.list_subscreibers_fragment.*

class ListSubscreibersFragment : Fragment() {

    private lateinit var viewModel: ListSubscreibersViewModel
    var controller = SubscriberListController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.list_subscreibers_fragment, container, false)
        val dao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(dao)
        val factory = ListSubscriberViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(ListSubscreibersViewModel::class.java)
        return view
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
    }

    private fun updateSubscriberList(list: List<Subscriber>) {
        controller.setData(list)
    }

    private fun initViews() {
        subscribersRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        subscribersRecyclerView.adapter = controller.adapter
    }


}