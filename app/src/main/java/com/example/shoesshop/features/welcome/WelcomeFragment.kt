package com.example.shoesshop.features.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoesshop.R
import com.example.shoesshop.databinding.FragmentWelcomeBinding
import kotlin.properties.Delegates

class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding
    var background by Delegates.notNull<Int>()

    companion object {
        private const val ARG_PARAM1 = "param1"
        fun newInstance(
            title: Int?,
        ): WelcomeFragment {
            val fragment = WelcomeFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, title ?: R.drawable.img_welcome_1)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            background = requireArguments().getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bgConstraint.setBackgroundResource(background)
    }

}