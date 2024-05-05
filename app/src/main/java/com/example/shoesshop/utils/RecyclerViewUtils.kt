package com.example.shoesshop.utils

import android.annotation.SuppressLint
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoesshop.constants.RecyclerValue

object RecyclerViewUtils {
    @SuppressLint("WrongConstant")
    fun initAdapter(
        mAdapter: RecyclerView.Adapter<*>,
        rev: RecyclerView,
        orientation: Int = LinearLayoutManager.VERTICAL,
        spanCount: Int = 1,
        type: RecyclerValue = RecyclerValue.GIRD_LAYOUT_MANAGER,
        isSetScrollMiddle: Boolean = true
    ) {
        rev.apply {
            adapter = mAdapter
            layoutManager = if (type == RecyclerValue.LINEAR_LAYOUT_MANAGER)
                CenterLayoutManager(context, orientation, false)
            else
                GridLayoutManager(context, spanCount, orientation, false)
            isNestedScrollingEnabled = false
            setHasFixedSize(false)
//            isNestedScrollingEnabled = true
        }

        if (orientation == LinearLayoutManager.HORIZONTAL && isSetScrollMiddle)
            rev.onScrolledToMiddlePosition()

    }

    fun RecyclerView.onScrolledToMiddlePosition() {
        val snapHelper = LinearSnapHelper()
        if (onFlingListener == null)
            snapHelper.attachToRecyclerView(this)
        setOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var view = recyclerView[0]
            }
        })
    }

}