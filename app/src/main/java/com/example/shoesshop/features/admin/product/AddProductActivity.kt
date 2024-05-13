package com.example.shoesshop.features.admin.product

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.databinding.ActivityAddProductBinding
import com.example.shoesshop.features.main.detail.adapter.AdapterSize
import com.example.shoesshop.features.main.home.model.Product
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddProductActivity :
    BaseActivity<ActivityAddProductBinding>(ActivityAddProductBinding::inflate) {
    private val listPhoto: MutableList<String> = mutableListOf()

    lateinit var adapterPhoto: AdapterPhoto
    private val listSize : ArrayList<Double> = ArrayList()
    lateinit var adapterSize: AdapterSizeProductDelete

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 101
        private const val REQUEST_CODE_PICK_IMAGE = 102
    }

    override fun onCreateView() {
        super.onCreateView()
        adapterPhoto = AdapterPhoto(this@AddProductActivity)
        adapterSize = AdapterSizeProductDelete()
        initViews()
    }

    private fun initViews() {
        setUpRecyclerPhoto()
        setLietener()
    }

    private fun setUpRecyclerPhoto() {
        binding.recyclerView.adapter = adapterPhoto
        listPhoto.add("https://firebasestorage.googleapis.com/v0/b/theme-pack-android.appspot.com/o/Label%2Fgallery%2F1.1.png?alt=media&token=9d12831e-a6be-42ab-bdfe-eb7b189eea95")
        adapterPhoto.init(listPhoto)
        adapterPhoto.subjectAddImg = {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

//        adapterPhoto.subjectCallBackChoosePosition = { position ->
//            val path = listPhoto[position]
//        }

        adapterPhoto.subjectCallBackDeletePosition = {
            listPhoto.remove(listPhoto[it])
//            if (listPhoto.size == 1) {
//                Glide.with(this).load(photo.urlImage).into(binding.imageView)
//            }
            adapterPhoto.notifyItemRemoved(it)
            adapterPhoto.init(listPhoto)
        }
    }

    private fun resetAdapterSize()
    {
        binding.recyclerView.adapter = adapterSize.apply {
            this.data = listSize.map { it }.toMutableList()
        }
        adapterSize.sizeRemove = {
            listSize.remove(it)
            resetAdapterSize()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val uri = data.data

        }
    }


    fun setLietener() {
        binding.btnAddProductDone.setOnClickListener {
            if (binding.edtProduct.editText!!.text.toString().isNotEmpty() &&
                binding.edtProductType.editText!!.text.isNotEmpty() &&
                binding.edtDiscription.editText!!.text.toString()
                    .isNotEmpty() && binding.edtPrice.editText!!.text.toString().isNotEmpty()
                && listSize.isNotEmpty()
            ) {
                val item = Product(
                    id = FetchDataFirebase.share.listProduct.size,
                    name = binding.edtProduct.editText!!.text.toString(),
                    image = "",
                    price = binding.edtPrice.editText!!.text.toString().toDouble(),
                    isFav = false,
                    description = binding.edtDiscription.editText!!.text.toString(),
                    isBestSeller = false,
                    type = binding.edtProductType.editText!!.text.toString(),
                    sizes = listSize
                )
                FetchDataFirebase.share.addProduct(item, object : ActionCallback {
                    override fun onActionComplete(isSuccess: Boolean) {
                        if (isSuccess) {
                            setResult(101)
                            this@AddProductActivity.finish()
                        } else {

                        }
                    }
                })
            }
        }
        binding.btnAddSize.setOnClickListener {
            if (binding.edtSize.editText!!.text.toString().isNotEmpty() && binding.edtSize.editText!!.text.toString().length >= 2)
            {

                listSize.add(binding.edtSize.editText!!.text.toString().toDouble())
                binding.edtSize.editText!!.setText("")
                resetAdapterSize()

            }
        }
    }
}