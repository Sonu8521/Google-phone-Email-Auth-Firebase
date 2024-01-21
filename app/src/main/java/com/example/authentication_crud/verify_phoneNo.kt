package com.example.authentication_crud

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class verify_phoneNo : AppCompatActivity() {

    private var progressStatus = 0
    private lateinit var auth: FirebaseAuth
    private lateinit var verificationId: String
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone_no)

        auth = FirebaseAuth.getInstance()

        var phoneNumberEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        var otpEditText = findViewById<EditText>(R.id.otpEditText)
        var verifyButton = findViewById<Button>(R.id.verifyButton)
        var submitOtpButton = findViewById<Button>(R.id.submitOtpButton)

//        val prg = ProgressDialog(this)
//        prg.setMessage("Please Wait...")
//        prg.setCancelable(false)
//        Handler().postDelayed({prg.dismiss()},5000)
//        prg.show()


        verifyButton.setOnClickListener {

            val prg = ProgressDialog(this)
            prg.setMessage("Send OTP Please Wait..")
            prg.setCancelable(false)
            Handler().postDelayed({prg.dismiss()},5000)
            prg.show()

            val phoneNumber = phoneNumberEditText.text.toString().trim()

            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, java.util.concurrent.TimeUnit.SECONDS, this,
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // Handle verification failure
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@verify_phoneNo.verificationId = verificationId
                    }
                })

        }

        submitOtpButton.setOnClickListener {

            val otp = otpEditText.text.toString().trim()
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            signInWithPhoneAuthCredential(credential)

            val intent = Intent(this, dashboard::class.java)
            startActivity(intent)

    }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Phone number authentication successful
                    Toast.makeText(this@verify_phoneNo, "verify Number:", Toast.LENGTH_SHORT).show()

                    val user = task.result?.user
                    // Proceed with logged-in user

                } else {
                    // Phone number authentication failed
                    Toast.makeText(this@verify_phoneNo, "verify field:", Toast.LENGTH_SHORT).show()

                }
            }

    }
}