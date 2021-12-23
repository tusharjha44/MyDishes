package com.example.mydishes.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mydishes.R
import com.example.mydishes.databinding.FragmentRandomDishesBinding

class RandomDishFragment : Fragment() {

    private lateinit var mBinding: FragmentRandomDishesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRandomDishesBinding.inflate(inflater,container,false)
        return mBinding.root
    }
}
