package com.example.gitscout.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gitscout.R
import com.example.gitscout.databinding.SearchFragmentBinding


class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            if (username.isEmpty()) {
                binding.editTextUsername.error = "Please enter a username"
            } else {
                val bundle = Bundle()
                bundle.putString("username", username)
                val userProfileFragment = UserProfileFragment()
                userProfileFragment.arguments = bundle

                // Navigate to UserProfileFragment
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainer, userProfileFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
