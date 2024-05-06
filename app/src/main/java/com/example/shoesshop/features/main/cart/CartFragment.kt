package com.example.shoesshop.features.main.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.constants.DataShared
import com.example.shoesshop.constants.RecyclerValue
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentCartBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.activity.DetailActivity
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.cart.adapter.ProductCartAdapter
import com.example.shoesshop.features.main.cart.model.CartProduct
import com.example.shoesshop.features.main.home.HomeFragment
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.features.main.home.view_model.HomeViewModel
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.navigateTo
import com.example.shoesshop.utils.ViewUtils.showView

class CartFragment : BaseFragment<FragmentCartBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate

    private lateinit var productCartAdapter: ProductCartAdapter

    var listProductCartt: MutableList<CartProduct> = mutableListOf()


    override fun onViewCreated() {
        initAdapter()
        click()
    }

    private fun initAdapter() {
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(binding.layoutCart.revCommon)
        productCartAdapter = ProductCartAdapter()
        RecyclerViewUtils.initAdapter(
            mAdapter = productCartAdapter,
            rev = binding.layoutCart.revCommon
        )
        val idUser = MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        val user = FetchDataFirebase.share.getEmployeeById(idUser!!)
        if (user?.listCard != null) {
            user.listCard!!.forEach {
                val pro = getProduct(it.idPruduct)
                if (pro != null) {
                    listProductCartt.add(
                        CartProduct(
                            Product(
                                id = it.idPruduct!!, image = "", pro.name,
                                pro.price?.times(it.total!!)
                            ),
                            quantity = it.total
                        )
                    )
                }

            }

            productCartAdapter.submitList(listProductCartt)
            productCartAdapter.notifyDataSetChanged()
        }
    }

    private fun getProduct(id: Int?): Product? {
        if (FetchDataFirebase.share.listProduct.isNotEmpty()) {
            FetchDataFirebase.share.listProduct.forEach {
                if (it.id == id) {
                    return it
                }
            }
        }

        return null
    }

    private fun click() {
        productCartAdapter.onItemClick = {
            homeViewModel.product.value = it.product
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(HomeActivity.REPLACE_DRAWER, 1)
            startActivity(intent)
        }
    }

    override fun initAction() {

        binding.layoutBottomCheckout.btCheckOut.setOnClickListener {

//            it.navigateTo(R.id.action_cartFragment_to_cartDetailFragment)
        }
    }

    @SuppressLint("StringFormatMatches")
    override fun initView() {
        productCartViewModel.listProductCartt.apply {
            binding.layoutCart.tvTitle.text =
                getString(R.string.text_item_cart_quantity, productCartAdapter.itemCount)
            binding.layoutCart.tvNext.hideView()
        }
    }

    private val simpleItemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
//                    viewHolder.productCartAdapter.notifyItemChanged(position)


                } else if (direction == ItemTouchHelper.RIGHT) {
//                notiAdapter.notifyItemChanged(position)
//                Toast.makeText(requireContext(), "Swipe right", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = DataShared.getWidth() ?: 0

                    if (dX < 0) {
                    } else {
                        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    }
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX / 5,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

            }
        }
}