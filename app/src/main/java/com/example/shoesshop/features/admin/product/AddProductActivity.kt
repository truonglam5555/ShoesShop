package com.example.shoesshop.features.admin.product

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.shoesshop.base.BaseActivity
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.UpFileCallback
import com.example.shoesshop.databinding.ActivityAddProductBinding
import com.example.shoesshop.features.main.detail.adapter.AdapterSize
import com.example.shoesshop.features.main.home.model.Product
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AddProductActivity :
    BaseActivity<ActivityAddProductBinding>(ActivityAddProductBinding::inflate) {
    private val listPhoto: MutableList<Bitmap> = mutableListOf()
    private val listPhotoUri: MutableList<Uri> = mutableListOf()

    lateinit var adapterPhoto: AdapterPhoto
    private val listSize: ArrayList<Double> = ArrayList()
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
        //listPhoto.add("https://firebasestorage.googleapis.com/v0/b/theme-pack-android.appspot.com/o/Label%2Fgallery%2F1.1.png?alt=media&token=9d12831e-a6be-42ab-bdfe-eb7b189eea95")
        adapterPhoto.init(listPhoto)
        adapterPhoto.subjectAddImg = {

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

    private fun resetAdapterSize() {
        binding.recyclerViewSize .adapter = adapterSize.apply {
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
        if ( resultCode == RESULT_OK) {
            when(requestCode)
            {
                REQUEST_CODE_PICK_IMAGE -> {
                    val imageUri = data?.data
                    imageUri?.let {
                        listPhotoUri.add(it)
                        val inputStream = contentResolver.openInputStream(it)
                        listPhoto.add(BitmapFactory.decodeStream(inputStream))
                        adapterPhoto.init(listPhoto)
                    }
                }
            }

        }
    }

    fun setLietener() {

        binding.btnAddImage.setOnClickListener {
                    val intent =
            Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }
        binding.btnAddProductDone.setOnClickListener {

            if (binding.edtProduct.editText!!.text.toString().isNotEmpty() &&
                binding.edtProductType.editText!!.text.isNotEmpty() &&
                binding.edtDiscription.editText!!.text.toString()
                    .isNotEmpty() && binding.edtPrice.editText!!.text.toString().isNotEmpty()
                && listSize.isNotEmpty()
            ) {

                val listImages : ArrayList<String> = ArrayList()
                listPhotoUri.forEach {
                    FetchDataFirebase.share.uploadFile(it,FetchDataFirebase.share.dataUser.push().key!!,object :UpFileCallback{
                        override fun onActionComplete(url: Uri?, message: String?) {
                            if (url != null)
                            {
                                listImages.add(url.toString())
                                if (listImages.size == listPhotoUri.size)
                                {
                                    val item = Product(
                                        id = if (FetchDataFirebase.share.listProduct.size > 0 )  FetchDataFirebase.share.listProduct.last().id +1 else 0,
                                        name = binding.edtProduct.editText!!.text.toString(),
                                        image = "",
                                        price = binding.edtPrice.editText!!.text.toString().toDouble(),
                                        isFav = false,
                                        description = binding.edtDiscription.editText!!.text.toString(),
                                        isBestSeller = false,
                                        type = binding.edtProductType.editText!!.text.toString(),
                                        sizes = listSize,
                                        img_listString = listImages
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
                            }else{
                                Log.d("AAAA", message.toString())
                            }
                        }
                    },)
                }
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