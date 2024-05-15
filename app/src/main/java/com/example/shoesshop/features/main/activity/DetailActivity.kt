package com.example.shoesshop.features.main.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityDetailBinding
import com.example.shoesshop.features.main.activity.HomeActivity.Companion.REPLACE_DRAWER
import com.example.shoesshop.features.main.cart.CartDetailFragment
import com.example.shoesshop.features.main.detail.ProductDetailFragment
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.features.main.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(ActivityDetailBinding::inflate) {
    override fun onCreateView() {
        super.onCreateView()
        receivedData()
    }

    private fun receivedData() {
        val receiverData = intent.getIntExtra(REPLACE_DRAWER, 0)
        val itemData = intent.getSerializableExtra(HomeActivity.REPLACE_DRAWER) as? Product
        when (receiverData) {
            1 -> replaceFragment(ProductDetailFragment())
//            R.id.user -> replaceFragment(ProfileFragment())
//            R.id.order -> replaceFragment(OrdersFragment())
            R.id.setting -> replaceFragment(SettingFragment())
            4 -> replaceFragment(CartDetailFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        val itemData = FetchDataFirebase.share.productSelect
        intent.putExtra(HomeActivity.REPLACE_DRAWER, itemData)

    }
}
