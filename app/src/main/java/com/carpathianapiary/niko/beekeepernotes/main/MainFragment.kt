package com.carpathianapiary.niko.beekeepernotes.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.carpathianapiary.niko.beekeepernotes.R
import com.carpathianapiary.niko.beekeepernotes.databinding.MainFragmentBinding
import com.carpathianapiary.niko.beekeepernotes.login.RegistrationActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.floatingActionButton.setOnClickListener { addNewBeehave() }
    }

    private fun addNewBeehave() {
        RegistrationActivity.show(requireContext())
    }

}
