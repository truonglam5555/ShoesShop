package com.example.shoesshop.features.admin.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentHomeManagerBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.auth.AuthActivity

class HomeManagerFragment : BaseFragment<FragmentHomeManagerBinding>() {
    override fun initAction() {

    }

    override fun initView() {
        binding.tvLogOut.clickWithAnimationDebounce{
            MySharedPreferences.shared.putStringValue(KeyDataFireBase.keyUser,"").let {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeManagerBinding
        get() = FragmentHomeManagerBinding::inflate

    override fun onViewCreated() {
        binding.tvTab.text = FetchDataFirebase.share.getCurrentUser().fullName
        this.binding.tvCountAcount.text = FetchDataFirebase.share.listUser.size.toString()
        resetCount()
    }

    fun resetCount()
    {
        this.binding.tvCountOders.text = countPending()
        this.binding.tvCountOdersComfirm.text = countConfirm()
        this.binding.tvCountOderscaneled.text = countCancel()
    }


    private fun countPending() : String
    {
        var count : Int = 0
        FetchDataFirebase.share.listBillOder.forEach {
            if (it.status == 0)
            {
                count +=1
            }
        }
        return count.toString()
    }

    private fun countConfirm() : String
    {
        var count : Int = 0
        FetchDataFirebase.share.listBillOder.forEach {
            if (it.status == 1)
            {
                count +=1
            }
        }
        return count.toString()
    }

    private fun countCancel() : String
    {
        var count : Int = 0
        FetchDataFirebase.share.listBillOder.forEach {
            if (it.status == 2)
            {
                count +=1
            }
        }
        return count.toString()
    }
}