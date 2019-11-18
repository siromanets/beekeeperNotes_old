package com.carpathianapiary.beekeepernotes.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.carpathianapiary.beekeepernotes.BaseFragment
import com.carpathianapiary.beekeepernotes.R
import com.carpathianapiary.beekeepernotes.apiary.ApiaryActivity
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
        binding.testButton.setOnClickListener { addApiary() }
        binding.loginButton.setOnClickListener { login() }
    }

    private fun login() {
        RegistrationActivity.show(requireContext())
    }

    private fun addApiary() {
        val button = Button(requireContext())
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        button.text = "Apiary"
        button.setOnClickListener { showApiary() }
        binding.mainLayout.addView(button)
    }

    private fun showApiary() {
        ApiaryActivity.show(requireContext())
    }

}
