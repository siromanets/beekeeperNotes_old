package com.carpathianapiary.beekeepernotes.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.carpathianapiary.beekeepernotes.R
import com.carpathianapiary.beekeepernotes.databinding.UserTypeSelectorFragmentBinding

class UserTypeSelectorFragment : Fragment() {

    private var listener: UserTypeSelectionListener? = null

    interface UserTypeSelectionListener {

        fun onSignInSelect()

        fun onAnonymousSelected()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as UserTypeSelectionListener
    }

    companion object {
        fun newInstance() = UserTypeSelectorFragment()
    }

    private lateinit var binding: UserTypeSelectorFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_type_selector_fragment, container, false)
        binding.anonymous.setOnClickListener { listener?.onAnonymousSelected() }
        binding.signIn.setOnClickListener { listener?.onSignInSelect() }
        return binding.root
    }
}
