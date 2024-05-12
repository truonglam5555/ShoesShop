package com.example.shoesshop.features.admin.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoesshop.base.BaseAdapter
import com.example.shoesshop.common.extension.clickWithAnimationDebounce
import com.example.shoesshop.databinding.ItemUserManagerBinding
import com.example.shoesshop.model.Employee
import javax.inject.Inject

class AdapterUserManager @Inject constructor() : BaseAdapter<Employee, ItemUserManagerBinding>() {
    var onClickItem: ((obj: Employee) -> Unit)? = null
    var onClickDeleteItem: ((obj: Employee) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemUserManagerBinding
        get() = ItemUserManagerBinding::inflate

    override fun bindItem(item: Employee, binding: ItemUserManagerBinding, position: Int) {
        binding.run {
//            imgUser.setImage()
            tvName.text = item.fullName
            tvGmail.text = item.email
            cardViewUser.clickWithAnimationDebounce {
                onClickItem?.invoke(item)
            }
            icRemove.clickWithAnimationDebounce {
                onClickDeleteItem?.invoke(item)
            }
        }
    }
}