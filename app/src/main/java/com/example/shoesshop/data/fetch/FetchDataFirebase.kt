package com.example.shoesshop.data.fetch

import android.net.Uri
import android.webkit.ConsoleMessage
import com.example.shoesshop.R
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.CardUser
import com.example.shoesshop.model.Employee
import com.google.android.gms.tasks.SuccessContinuation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FetchDataFirebase {

    var listUser : ArrayList<Employee> = ArrayList()
    var listProduct : ArrayList<Product> = ArrayList()
    var listBillOder : ArrayList<BillOder> = ArrayList()
    val database = Firebase.database
    val storage = Firebase.storage
    lateinit var dataUser : DatabaseReference
    lateinit var dataProduct : DatabaseReference
    lateinit var dataBillOder : DatabaseReference
    companion object{
        val share = FetchDataFirebase()
    }

    fun init()
    {
        fetchUserData()
        fetchProductData()
        getListBillOder()
    }

    private fun  fetchUserData()
    {
       dataUser = database.getReference(KeyDataFireBase.keyUser)
        dataUser.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    listUser.clear()
                    snapshot.children.forEach {
                        val user = it.getValue(Employee::class.java)
                        listUser.add(user!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun  fetchProductData()
    {
        dataProduct = database.getReference(KeyDataFireBase.keyProduct)
        dataProduct.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    listProduct.clear()
                    snapshot.children.forEach {
                        val product = it.getValue(Product::class.java)
                        product!!.img_list = setImage(product.id)
                        listProduct.add(product!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getEmployeeById(id: String): Employee? {
        for (employee in listUser) {
            if (employee.id == id) {
                return employee
            }
        }
        return null // If no employee found with the given ID
    }

    private fun setImage(id : Int) : List<Int>
    {
        when(id)
        {
            1 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )
            2 ->  listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )
            3 ->  listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )
            4 ->  listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )
            5 -> listOf(
            R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder)
            6 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder)
        }
        return listOf(
            R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
        )
    }

    fun getListBillOder()
    {
        dataBillOder =  database.getReference(KeyDataFireBase.keyBillOders)
        dataBillOder.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists())
               {
                   listBillOder.clear()
                   snapshot.children.forEach{
                       listBillOder.add(it.getValue(BillOder::class.java)!!)
                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    // CheckOut
    fun addCheckOut(item:BillOder,callback: ActionCallback)
    {
       dataBillOder.child(item.id).setValue(item).addOnSuccessListener {
           callback.onActionComplete(true)
       }.addOnFailureListener{
           callback.onActionComplete(false)
       }
    }

    fun deleteCheckOut(item:BillOder,callback: ActionCallback)
    {
        dataBillOder.child(item.id).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener{
            callback.onActionComplete(false)
        }
    }

    fun UpdateCheckOut(item:BillOder,callback: ActionCallback)
    {
        dataBillOder.child(item.id).removeValue().let {
            dataBillOder.child(item.id).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener{
                callback.onActionComplete(false)
            }
        }
    }


    //User
    fun addUser(item:Employee,callback: ActionCallback)
    {
        dataUser.child(item.id!!).setValue(item).addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener{
            callback.onActionComplete(false)
        }
    }

    fun deleteCheckOut(item:Employee,callback: ActionCallback)
    {
        dataUser.child(item.id!!).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener{
            callback.onActionComplete(false)
        }
    }

    fun UpdateCheckOut(item:Employee,callback: ActionCallback)
    {
        dataUser.child(item.id!!).removeValue().let {
            dataUser.child(item.id!!).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener{
                callback.onActionComplete(false)
            }
        }
    }

    //
    fun addProduct(item:Product,callback: ActionCallback)
    {
        dataProduct.child(item.id.toString()).setValue(item).addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener{
            callback.onActionComplete(false)
        }
    }

    fun deleteCheckOut(item:Product,callback: ActionCallback)
    {
        dataUser.child(item.id.toString()).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener{
            callback.onActionComplete(false)
        }
    }

    fun UpdateCheckOut(item:Product,callback: ActionCallback)
    {
        dataUser.child(item.id.toString()).removeValue().let {
            dataUser.child(item.id.toString()).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener{
                callback.onActionComplete(false)
            }
        }
    }

    fun uploadFile(file: Uri,nameFile : String,callback: UpFileCallback)
    {
        storage.getReference().child("image/" + nameFile).putFile(file).addOnSuccessListener {
            storage.getReference().child("image/" + nameFile).downloadUrl.addOnSuccessListener {
                callback.onActionComplete(it)
            }.addOnFailureListener {
                callback.onActionComplete(null,it.message)
            }
        }.addOnFailureListener {
            callback.onActionComplete(null,it.message)
        }
    }
}

interface ActionCallback {
    fun onActionComplete(isSuccess:Boolean)
}

interface UpFileCallback {
    fun onActionComplete(url:Uri? = null,message: String? = null)
}

