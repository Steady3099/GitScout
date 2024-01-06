package com.example.gitscout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitscout.R
import com.example.gitscout.data.model.User
import com.example.gitscout.databinding.FragmentFollowerFollowingsBinding
import com.example.gitscout.ui.adapter.ConnectionsAdapter
import com.example.gitscout.ui.adapter.ItemClickListener
import com.example.gitscout.viewmodel.UserProfileViewModel

class FollowerFollowingFragment: Fragment(), ItemClickListener {

    private var _binding: FragmentFollowerFollowingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var adapter: ConnectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerFollowingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString("username") ?: ""
        val isFollowers = arguments?.getBoolean("IsFollowers") ?: false
        viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]


        binding.followerRv.layoutManager = LinearLayoutManager(requireContext())
        val itemAnimator = DefaultItemAnimator()
        binding.followerRv.itemAnimator = itemAnimator

        if(isFollowers) binding.headerTitle.text = "$username Followers" else binding.headerTitle.text = "$username Following"

        viewModel.userFollowers.observe(viewLifecycleOwner) { userFollowers ->
            userFollowers?.let {
                if (userFollowers.isNotEmpty()) {
                    adapter = ConnectionsAdapter(userFollowers, requireContext(), this)
                    binding.followerRv.adapter = adapter
                }
            }
        }

        viewModel.userFollowings.observe(viewLifecycleOwner) { userFollowings ->
            userFollowings?.let {
                if (userFollowings.isNotEmpty()) {
                    adapter = ConnectionsAdapter(userFollowings, requireContext(), this)
                    binding.followerRv.adapter = adapter
                }
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        if (isFollowers) {
            viewModel.fetchUserFollowers(username)
        }else{
            viewModel.fetchUserFollowings(username)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(userName: String) {
        val bundle = Bundle()
        bundle.putString("username", userName)
        val userProfileFragment = UserProfileFragment()
        userProfileFragment.arguments = bundle

        // Navigate to UserProfileFragment
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, userProfileFragment)
            ?.addToBackStack(null)
            ?.commit()
    }

}