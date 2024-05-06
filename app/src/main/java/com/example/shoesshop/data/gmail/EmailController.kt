//package com.example.shoesshop.data.gmail
//
//import com.example.shoesshop.data.fetch.FetchDataFirebase
//
//class EmailController {
//
//    lateinit var sender: EmailSender
//    companion object{
//       val  shared = EmailController();
//    }
//
//    fun init(yourEmai :String,yourPass:String)
//    {
//        sender = EmailSender(yourEmai, yourPass)
//    }
//
//    fun sendMail(subject:String ,body: String,recipientEmail: String)
//    {
//        sender.sendEmail(subject, body, recipientEmail)
//    }
//}