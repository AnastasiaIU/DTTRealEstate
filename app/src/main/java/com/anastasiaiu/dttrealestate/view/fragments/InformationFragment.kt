package com.anastasiaiu.dttrealestate.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anastasiaiu.dttrealestate.R
import com.anastasiaiu.dttrealestate.databinding.FragmentInformationBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.infoWebsite.setOnClickListener {
            openWebsite(getString(R.string.info_website_link))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Opens an URL in a browser using implicit [Intent].
     */
    private fun openWebsite(link: String) {

        // Parse the passed string into a URI object
        val uri = Uri.parse(link)

        // Create a new Intent for viewing the URI object
        val viewIntent = Intent(Intent.ACTION_VIEW, uri)

        // Start an Activity
        startActivity(viewIntent)
    }
}