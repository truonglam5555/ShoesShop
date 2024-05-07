package com.example.shoesshop.features.auth

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shoesshop.R
import com.example.shoesshop.base.BaseFragment
import com.example.shoesshop.data.fetch.ActionCallback
import com.example.shoesshop.data.fetch.FetchDataFirebase
import com.example.shoesshop.data.fetch.KeyDataFireBase
import com.example.shoesshop.databinding.FragmentLoginBinding
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.activity.HomeActivity
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.Employee
import com.example.shoesshop.utils.ImageUtils.setImage
import com.example.shoesshop.utils.ViewUtils.navigateTo

class LoginFragment : BaseFragment<FragmentLoginBinding>() {


    override val _binding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun onViewCreated() {

    }

    override fun initAction() {
        binding.tvRegister.setOnClickListener {
            it.navigateTo(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvForgotPassword.setOnClickListener {
            it.navigateTo(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.btLogin.setOnClickListener {
            FetchDataFirebase.share.auth.signInWithEmailAndPassword(binding.layoutEmail.edtEmail.text.toString(),binding.layoutPassword.edtPassword.text.toString()).addOnSuccessListener {
                if (it.user != null)
                {
                    val user =  getEmployeeById(binding.layoutEmail.edtEmail.text.toString())
                    user!!.id?.let { value ->
                        MySharedPreferences.shared.putStringValue(KeyDataFireBase.keyUser, value)
                    }
                    requireActivity().finish()
                    requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
                }
            }.addOnFailureListener{
                Log.d("Login",it.message.toString())
            }
//            val user =  getEmployeeById(binding.layoutEmail.edtEmail.text.toString());
//            if (user != null && user.pass == binding.layoutPassword.edtPassword.text.toString())
//            {
//                user.id?.let { value ->
//                    MySharedPreferences.shared.putStringValue(KeyDataFireBase.keyUser, value)
//                }
//                requireActivity().finish()
//                requireActivity().startActivity(Intent(requireActivity(), HomeActivity::class.java))
//
//            }else
//            {
//                Log.d("Login",user.toString())
//            }
        }
        addProductTest()
    }

    private fun addAdmindData() {
        val list = ArrayList<Int>()
        list.add(1) //  Đọc
        list.add(1) // Viết
        list.add(1) // Xoá => tuỳ bác muốn set vị trí
        val user = Employee(
            FetchDataFirebase.share.dataUser.push().key!!, "Administrator",
            0, list, "", "admind@gmail.com", "1111"
        )

        FetchDataFirebase.share.addUser(user, object : ActionCallback {
            override fun onActionComplete(isSuccess: Boolean) {
                if (isSuccess) {
                    Toast.makeText(this@LoginFragment.activity, "Sucesss!", Toast.LENGTH_SHORT)
                        .show().let {
                        findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(this@LoginFragment.activity, "Fail!", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun addProductTest() {

        val list = ArrayList<Product>()
        list.add(
            Product(
                1,
                "",
                "Nike Jordan 1",
                302.00,
                false,
                isBestSeller = true,
                type = "Shoes",
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 1 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."
            )
        )
        list.add(
            Product(
                2,
                "",
                "Nike Jordan 2",
                302.00,
                false,
                isBestSeller = true,
                type = "Shoes",
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 2 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."
            )
        )
        list.add(
            Product(
                3,
                "",
                "Nike Jordan 3",
                302.00,
                false,
                isBestSeller = true,
                type = "Shoes",
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 3 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."

            )
        )
        list.add(
            Product(
                4,
                "",
                "Nike Jordan 4",
                302.00,
                false,
                isBestSeller = true,
                type = "Shoes",
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 4 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."

            )
        )
        list.add(
            Product(
                5,
                "",
                "Nike Jordan 5",
                302.00,
                false,
                isBestSeller = true,
                type = "Shoes",
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 5 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."

            )
        )
        list.add(
            Product(
                6,
                "",
                "Nike Jordan 6",
                302.00,
                false,
                isBestSeller = true,
                sizes = listOf(36.0, 36.5, 37.0, 37.5, 38.0, 38.5, 40.0, 40.5, 41.0),
                description = "Nike Air Jordan 6 Low 'Panda' Shoes DC0774-101 is a special version of the Air Jordan 1 product line, designed with a delicate combination of white and black, creating a simple, sophisticated style. elegant and classic."

            )
        )

        list.forEach {
            FetchDataFirebase.share.database.getReference(KeyDataFireBase.keyProduct)
                .child(it.id.toString()).setValue(it)
        }
    }

    private fun getEmployeeById(id: String): Employee? {
        for (employee in FetchDataFirebase.share.listUser) {
            if (employee.email == id) {
                return employee
            }
        }
        return null // If no employee found with the given Email
    }

    override fun initView() {
        binding.layoutHeader.imgBack.setImage(R.drawable.ic_back_auth)
        binding.layoutTitleAuthScreen.tvHeading.text = getString(R.string.text_hello_again)
        binding.layoutTitleAuthScreen.tvDescription.text =
            getString(R.string.text_description_login)
    }

}