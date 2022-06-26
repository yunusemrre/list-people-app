package com.sccase.listpeople.scene.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sccase.listpeople.R
import com.sccase.listpeople.adapter.ListPeopleAdapter
import com.sccase.listpeople.data.model.Person
import com.sccase.listpeople.data.model.Resource
import com.sccase.listpeople.databinding.FragmentListPeopleBinding
import com.sccase.listpeople.util.adapter.SlideUpItemAnimator
import com.sccase.listpeople.util.extension.createAlertDialog
import com.sccase.listpeople.util.extension.getStringResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPeopleFragment : Fragment() {

    private val viewModel: ListPeopleViewModel by viewModels()
    private lateinit var binding: FragmentListPeopleBinding

    private lateinit var adapter: ListPeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListPeopleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initObservers()
        initListeners()
        getList()
    }

    /**
     * Initialize list adapter [ListPeopleAdapter]
     */
    private fun initAdapter() {
        adapter = ListPeopleAdapter()
        binding.rvListPeople.adapter = adapter
        binding.rvListPeople.itemAnimator = SlideUpItemAnimator()
        binding.pbListPeople.hide()
    }

    /**
     * Initialize observers of [ListPeopleViewModel]
     */
    private fun initObservers() {
        viewModel.userListData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.let { fetchResponse ->
                        val addResult =
                            adapter.setDataList(fetchResponse.people as ArrayList<Person>)
                        binding.swpListPeople.isRefreshing = false
                        binding.pbListPeople.hide()

                        if (!addResult) {
                            Snackbar.make(
                                binding.swpListPeople,
                                requireContext().getStringResource(R.string.msg_no_new_data),
                                Snackbar.LENGTH_SHORT
                            ).show()
                            requireContext().createAlertDialog(
                                text = requireContext().getStringResource(R.string.msg_no_one_listing),
                                title = requireContext().getStringResource(R.string.msg_no_new_data),
                                yesAction = {
                                    getList()
                                }
                            )
                        }
                    }
                }

                Resource.Status.ERROR -> {
                    binding.swpListPeople.isRefreshing = false
                    binding.pbListPeople.hide()
                    requireContext().createAlertDialog(
                        text = requireContext().getStringResource(R.string.msg_error_occurred),
                        yesAction = {
                            getList()
                        }
                    )
                }
            }
        }

        /**
         * show/hide progress depends on current state
         */
        viewModel.processState.observe(viewLifecycleOwner) { processing ->
            if (processing) {
                binding.pbListPeople.show()
            } else {
                binding.pbListPeople.hide()
            }
        }
    }

    /**
     * Initialize Listeners
     */
    private fun initListeners() {

        /**
         * Get new data when swipe
         */
        binding.swpListPeople.setOnRefreshListener {
            getList()
        }

        /**
         * Get new data when reaching bottom of list
         */
        binding.rvListPeople.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getList()
                }
            }
        })
    }

    /**
     * Get new list
     */
    private fun getList() {
        viewModel.listUser()
    }
}