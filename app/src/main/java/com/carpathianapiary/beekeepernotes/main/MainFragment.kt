package com.carpathianapiary.beekeepernotes.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.carpathianapiary.beekeepernotes.BaseFragment

import com.carpathianapiary.beekeepernotes.R
import com.carpathianapiary.beekeepernotes.databinding.MainFragmentBinding
import com.carpathianapiary.beekeepernotes.login.RegistrationActivity

class MainFragment : BaseFragment<MainViewModel>() {
    companion object {

        fun newInstance() = MainFragment()
    }
    private lateinit var binding: MainFragmentBinding

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.floatingActionButton.setOnClickListener { addNewBeehave() }
    }

    private fun addNewBeehave() {
        RegistrationActivity.show(requireContext())
    }

}
