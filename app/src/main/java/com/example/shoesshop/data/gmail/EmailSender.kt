//package com.example.shoesshop.data.gmail
//
//import java.util.Properties
//import javax.mail.*
//import javax.mail.internet.InternetAddress
//import javax.mail.internet.MimeMessage
//
//class EmailSender(private val username: String, private val password: String) {
//
//    fun sendEmail(subject: String, body: String, recipientEmail: String) {
//        val properties = Properties().apply {
//            put("mail.smtp.host", "smtp.gmail.com")
//            put("mail.smtp.socketFactory.port", "465")
//            put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
//            put("mail.smtp.auth", "true")
//            put("mail.smtp.port", "465")
//        }
//
//        val session = Session.getDefaultInstance(properties, object : Authenticator() {
//            override fun getPasswordAuthentication(): PasswordAuthentication {
//                return PasswordAuthentication(username, password)
//            }
//        })
//
//        val message = MimeMessage(session).apply {
//            setFrom(InternetAddress(username))
//            addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
//            setSubject(subject)
//            setText(body)
//        }
//
//        try {
//            Transport.send(message)
//        } catch (ex: MessagingException) {
//            ex.printStackTrace()
//        }
//    }
//}
