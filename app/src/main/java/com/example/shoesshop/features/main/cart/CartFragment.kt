package com.example.shoesshop.features.main.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
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
import com.example.shoesshop.data.fetch.ActionCallback
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
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.utils.RecyclerViewUtils
import com.example.shoesshop.utils.ViewUtils.hideView
import com.example.shoesshop.utils.ViewUtils.navigateTo
import com.example.shoesshop.utils.ViewUtils.showView
import com.example.shoesshop.widgets.dialog.BasePopupSuccessFragment

class CartFragment : BaseFragment<FragmentCartBinding>() {
    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = FragmentCartBinding::inflate

    private lateinit var productCartAdapter: ProductCartAdapter

    var listProductCartt: MutableList<CartProduct> = mutableListOf()
    var listCard : ArrayList<CardUser> = ArrayList()
    var total : Int =0;
    var shipCod : Int = 50;

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

        val user = FetchDataFirebase.share.getCurrentUser()
        if (user.listCard != null)
        {
            listCard = user.listCard!!
            setListAdapter()
        }
    }

    fun refreshCard()
    {
        val user = FetchDataFirebase.share.getCurrentUser()
        if (user.listCard != null)
        {
            listCard = user.listCard!!
            setListAdapter()
        }
    }


    private fun getProduct( id : Int?) :Product?  {
        if (FetchDataFirebase.share.listProduct.isNotEmpty())
        {
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

        productCartAdapter.onItemDeleteButtonClick = {
            if (listCard.isNotEmpty())
            {
                val  item = getUserCard(it.product!!.id)
                if (item != null)
                {
                    val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
                    FetchDataFirebase.share.getEmployeeById(idUser!!).let {
                        if (it != null)
                        {
                            listCard.remove(item)
                            it.listCard = listCard
                            FetchDataFirebase.share.UpdateUser(it,object :ActionCallback{
                                override fun onActionComplete(isSuccess: Boolean) {
                                    if (isSuccess)
                                    {
                                        setListAdapter()
                                    }
                                }

                            })
                        }
                    }


                }
            }
        }

        productCartAdapter.onItemAddButtonClick ={
            val pro = it
            if (listCard.isNotEmpty())
            {
                    val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
                    FetchDataFirebase.share.getEmployeeById(idUser!!).let {
                        if (it != null)
                        {
                            listCard.forEach{
                                if (it.idPruduct == pro.product!!.id )
                                {
                                    it.total = it.total!! +1
                                }

                            }
                            it.listCard = listCard
                            FetchDataFirebase.share.UpdateUser(it,object :ActionCallback{
                                override fun onActionComplete(isSuccess: Boolean) {
                                    if (isSuccess)
                                    {
                                        setListAdapter()
                                    }
                                }

                            })
                        }
                }
            }
        }

        productCartAdapter.onItemSubButtonClick = {
            val pro = it
            if (listCard.isNotEmpty())
            {
                val idUser =  MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
                FetchDataFirebase.share.getEmployeeById(idUser!!).let {
                    if (it != null)
                    {
                        listCard.forEach{
                            if (it.idPruduct == pro.product!!.id )
                            {
                                if (it.total!! > 1)
                                {
                                    it.total = it.total!! -1
                                }
                            }

                        }
                        it.listCard = listCard
                        FetchDataFirebase.share.UpdateUser(it,object :ActionCallback{
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess)
                                {
                                    setListAdapter()
                                }
                            }

                        })
                    }
                }
            }
        }
    }

    fun  setListAdapter()
    {
        listProductCartt.clear()
        listCard.forEach {
            val pro = getProduct(it.idPruduct)
            if (pro != null)
            {
                listProductCartt.add(
                    CartProduct(
                        Product(id = it.idPruduct!!, image = "", pro.name,
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
    private fun getUserCard(id:Int) : CardUser?
    {
        for (card in listCard)
        {
            if (id == card.idPruduct)
            {
                return card
            }
        }
        return null
    }


    override fun initAction() {

        binding.layoutBottomCheckout.btCheckOut.setOnClickListener {
            val user = FetchDataFirebase.share.getCurrentUser()
            val curentTime = System.currentTimeMillis();
            val checkOut = BillOder(FetchDataFirebase.share.dataBillOder.push().key!!,user.id!!, curentTime,0,curentTime,total,shipCod,user!!.listCard!!)
            user.listCard = ArrayList()
            FetchDataFirebase.share.UpdateUser(user,object : ActionCallback{
                override fun onActionComplete(isSuccess: Boolean) {
                    if (isSuccess)
                    {
                        FetchDataFirebase.share.addCheckOut(checkOut,object : ActionCallback{
                            override fun onActionComplete(isSuccess: Boolean) {
                                if (isSuccess)
                                {
                                    val popupSuccess = BasePopupSuccessFragment(
                                        title = getString(R.string.text_your_payment_is_successful),
                                        btText = getString(R.string.text_back_to_shopping)
                                    )
                                    popupSuccess.show(childFragmentManager,"")

                                    popupSuccess.onCallback = {
                                        findNavController().popBackStack(R.id.homeFragment, false)
                                    }
                                }
                            }

                        })
                    }
                }
            })
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