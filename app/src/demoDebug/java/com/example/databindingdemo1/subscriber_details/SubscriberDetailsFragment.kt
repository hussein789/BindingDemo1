package com.example.databindingdemo1.subscriber_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.databindingdemo1.R
import com.example.databindingdemo1.db.Subscriber
import com.example.databindingdemo1.db.SubscriberDatabase
import com.example.databindingdemo1.db.SubscriberRepository
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.subscriber_details_fragment.*

class SubscriberDetailsFragment : Fragment() {

   companion object{
        const val SUBSCRIBER_KEY = "SUBSCRIBER_KEY"
   }

    private lateinit var viewModel: SubscriberDetailsViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.subscriber_details_fragment, container, false)
        val dao = SubscriberDatabase.newInstance(requireActivity()).subscriberDAO
        val repo = SubscriberRepository(dao)
        val factory = SubscriberDetailsViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory).get(SubscriberDetailsViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subscriber:SubscriberData? = arguments?.getParcelable(SUBSCRIBER_KEY)
        nameEditText.setText( subscriber?.name ?: "")
        emailEditText.setText(subscriber?.email ?: "")
        viewModel.init(subscriber)
        initClickListeners()
        initViews()
        observeViewModel()

        Toast.makeText(requireActivity(),"Demo Debug Version",Toast.LENGTH_LONG).show()

    }

    private fun initViews() {
        nameEditText.addTextChangedListener {
            viewModel.onNameTextChanged(it.toString())
        }
        emailEditText.addTextChangedListener {
            viewModel.onEmailTextChanged(it.toString())
        }
    }

    private fun initClickListeners() {
        updateSubscriberButton.setOnClickListener {
            viewModel.onUpdateClicked()
        }
        deleteSubscriberButton.setOnClickListener {
            viewModel.onDeleteClicked()
        }
    }

    private fun observeViewModel() {
        viewModel.finishActivity.observe(viewLifecycleOwner, Observer { finish ->
            finish?.let { if(finish) finishActivity() }
        })
    }

    private fun finishActivity() {
        this.findNavController().navigateUp()
    }


    private fun updateEmail(email: String) {
        emailEditText.setText(email)
    }

    private fun updateName(name: String) {
        nameEditText.setText(name)
    }
}

@Parcelize
data class SubscriberData(
    val id:Int,
    val name:String,
    val email:String
):Parcelable

