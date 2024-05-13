package com.example.shoesshop.data.fetch

import android.net.Uri
import android.util.Log
import com.example.shoesshop.R
import com.example.shoesshop.datastore.MySharedPreferences
import com.example.shoesshop.features.main.home.model.Product
import com.example.shoesshop.model.BillOder
import com.example.shoesshop.model.Employee
import com.example.shoesshop.model.MessageChat
import com.example.shoesshop.model.MessageUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FetchDataFirebase {

    var listUser: ArrayList<Employee> = ArrayList()
    var listProduct: ArrayList<Product> = ArrayList()
    var listBillOder: ArrayList<BillOder> = ArrayList()
    val database = Firebase.database
    private val storage = Firebase.storage
    val auth: FirebaseAuth
    lateinit var dataUser: DatabaseReference
    lateinit var dataProduct: DatabaseReference
    lateinit var dataBillOder: DatabaseReference
    lateinit var dataChats: DatabaseReference
//    lateinit var listImage: DatabaseReference
    private var userChat: MessageUser? = null
    private var listUserChat: ArrayList<MessageUser> = ArrayList()
    var productSelect: Product? = null

    companion object {
        val share = FetchDataFirebase()
    }

    init {
        auth = Firebase.auth
    }

    fun init() {
        fetchUserData()
        fetchProductData()
        getListBillOder()
    }

    fun getCurrentUser(): Employee {
        val idUser = MySharedPreferences.shared.pullStringValue(KeyDataFireBase.keyUser)
        return getEmployeeById(idUser!!)!!
    }

    private fun fetchUserData() {
        dataUser = database.getReference(KeyDataFireBase.keyUser)
        dataUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
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

    private fun fetchProductData() {
        dataProduct = database.getReference(KeyDataFireBase.keyProduct)
        dataProduct.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
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

    fun fetchChatDataUser(callback: UserChatCallback) {
        dataChats = database.getReference(KeyDataFireBase.keyChat)
        dataChats.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = getCurrentUser()
                    snapshot.children.forEach {
                        val item = it.getValue(MessageUser::class.java)
                        if (item != null && user.isadmid == 0 && item.idUser == user.id) {
                            userChat = item
                            userChat?.messages?.let { it1 -> callback.onActionChat(it1) };
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun fetchChatDataAdmin(callback: AdminChatCallback) {
        dataChats = database.getReference(KeyDataFireBase.keyChat)
        dataChats.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listUserChat.clear();
                    //val list : ArrayList<MessageUser> = ArrayList()
                    snapshot.children.forEach {
                        val item = it.getValue(MessageUser::class.java)
                        listUserChat.add(item!!)
                    }
                    callback.onActionChat(listUserChat)
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

    fun getEmployeeByEmail(email: String): Employee? {
        for (employee in listUser) {
            if (employee.email == email) {
                return employee
            }
        }
        return null // If no employee found with the given ID
    }

    fun getProductByID(id: Int): Product? {
        for (pro in listProduct) {
            if (pro.id == id) {
                return pro
            }
        }
        return null // If no employee found with the given ID
    }

    private fun setImage(id: Int): List<Int> {
        when (id) {
            1 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )

            2 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )

            3 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )

            4 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )

            5 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )

            6 -> listOf(
                R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
            )
        }
        return listOf(
            R.drawable._img_shoe_1, R.drawable._img_shoe_2, R.drawable.img_placeholder
        )
    }

    fun getListBillOder() {
        dataBillOder = database.getReference(KeyDataFireBase.keyBillOders)
        dataBillOder.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listBillOder.clear()
                    snapshot.children.forEach {
                        Log.d("Bill", it.toString())
                        listBillOder.add(it.getValue(BillOder::class.java)!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    // CheckOut
    fun addCheckOut(item: BillOder, callback: ActionCallback) {
        dataBillOder.child(item.id).setValue(item).addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun deleteBill(item: BillOder, callback: ActionCallback) {
        dataBillOder.child(item.id).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun UpdateCheckOut(item: BillOder, callback: ActionCallback) {
        dataBillOder.child(item.id).removeValue().let {
            dataBillOder.child(item.id).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener {
                callback.onActionComplete(false)
            }
        }
    }


    //User
    fun addUser(item: Employee, callback: ActionCallback) {
        dataUser.child(item.id!!).setValue(item).addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun deleteUser(item: Employee, callback: ActionCallback) {
        dataUser.child(item.id!!).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun UpdateUser(item: Employee, callback: ActionCallback) {
        dataUser.child(item.id!!).removeValue().let {
            dataUser.child(item.id!!).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener {
                callback.onActionComplete(false)
            }
        }
    }

    //
    fun addProduct(item: Product, callback: ActionCallback) {
        dataProduct.child(item.id.toString()).setValue(item).addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun deleteProducts(item: Product, callback: ActionCallback) {
        Log.d("Item",item.id.toString())
        dataProduct.child(item.id.toString()).removeValue().addOnSuccessListener {
            callback.onActionComplete(true)
        }.addOnFailureListener {
            callback.onActionComplete(false)
        }
    }

    fun UpdateProduct(item: Product, callback: ActionCallback) {
        dataProduct.child(item.id.toString()).removeValue().let {
            dataProduct.child(item.id.toString()).setValue(item).addOnSuccessListener {
                callback.onActionComplete(true)
            }.addOnFailureListener {
                callback.onActionComplete(false)
            }
        }
    }

    fun uploadFile(file: Uri, nameFile: String, callback: UpFileCallback) {
        storage.getReference().child("image/" + nameFile).putFile(file).addOnSuccessListener {
            storage.getReference().child("image/" + nameFile).downloadUrl.addOnSuccessListener {
                callback.onActionComplete(it)
            }.addOnFailureListener {
                callback.onActionComplete(null, it.message)
            }
        }.addOnFailureListener {
            callback.onActionComplete(null, it.message)
        }
    }

    fun addUserChat(message: String) {
        if (userChat == null) {
            val user = getCurrentUser()
            val messages = ArrayList<MessageChat>()
            messages.add(
                MessageChat(
                    true,
                    message
                )
            )
            val chatUser = MessageUser(idUser = user.id, messages = messages)
            dataChats.child(user.id!!).setValue(chatUser)
        } else {
            dataChats.child(userChat!!.idUser!!).removeValue().addOnSuccessListener {
                userChat!!.messages!!.add(
                    MessageChat(
                        true,
                        message
                    )
                )
                dataChats.child(userChat!!.idUser!!).setValue(userChat)
            }.addOnFailureListener {

            }
        }
    }

    fun addAdminChat(message: String, idUser: String) {
        if (listUserChat.isNotEmpty()) {
            listUserChat.forEach {
                if (it.idUser == idUser) {
                    userChat = it
                }
            }
            if (userChat != null) {
                dataChats.child(userChat!!.idUser!!).removeValue().addOnSuccessListener {
                    userChat!!.messages!!.add(
                        MessageChat(
                            true,
                            message
                        )
                    )
                    dataChats.child(userChat!!.idUser!!).setValue(userChat)
                }.addOnFailureListener {

                }
            }
        }
    }
}

interface ActionCallback {
    fun onActionComplete(isSuccess: Boolean)
}

interface UpFileCallback {
    fun onActionComplete(url: Uri? = null, message: String? = null)
}

interface UserChatCallback {
    fun onActionChat(list: ArrayList<MessageChat>)
}

interface AdminChatCallback {
    fun onActionChat(list: ArrayList<MessageUser>)
}

