package com.carpathianapiary.beekeepernotes.apiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.carpathianapiary.beekeepernotes.R

class ApiaryFragment : Fragment() {

    companion object {
        fun newInstance() = ApiaryFragment()
    }

    private lateinit var viewModel: ApiaryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.apiary_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ApiaryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
