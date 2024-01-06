package com.example.gitscout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gitscout.R
import com.example.gitscout.databinding.UserProfileFragmentBinding
import com.example.gitscout.viewmodel.UserProfileViewModel

class UserProfileFragment : Fragment() {

    private var _binding: UserProfileFragmentBinding? =  null
    private var username = ""
    private val binding get() = _binding!!
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserProfileFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        username = arguments?.getString("username") ?: ""

        viewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            userProfile?.let {
                binding.textViewUsername.text = it.login
                binding.textViewDescription.text = it.bio
                binding.textViewName.text = it.name
                binding.textViewFollowers.text = "Followers: "+it.followers.toString()
                binding.textViewFollowing.text = "Followings: "+it.following.toString()
                if(it.followers == 0) binding.textViewFollowers.isEnabled = false


                Glide.with(this)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imageViewAvatar)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.fetchUserProfile(username)

        binding.textViewFollowers.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putBoolean("IsFollowers",true)
            val connectionsFragment = FollowerFollowingFragment()
            connectionsFragment.arguments = bundle

            // Navigate to UserProfileFragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, connectionsFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        binding.textViewFollowing.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putBoolean("IsFollowers",false)
            val connectionsFragment = FollowerFollowingFragment()
            connectionsFragment.arguments = bundle

            // Navigate to UserProfileFragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, connectionsFragment)
                ?.addToBackStack(null)
                ?.commit()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}