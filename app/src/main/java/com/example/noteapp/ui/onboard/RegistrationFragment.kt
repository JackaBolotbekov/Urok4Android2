package com.example.noteapp.ui.onboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentRegistrationBinding
import com.example.noteapp.utils.PreferenceHelper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private var auth: FirebaseAuth? = null
    private var storedVerificationId: String? = " "
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initial()
        setUpListener()
    }
    private fun setUpListener() = with(binding) {
        btnGetTheCode.setOnClickListener {
            startPhoneNumberVerification(etInputNumber.text.toString())
            btnGetTheCode.isVisible = false
            btnAccessTheCode.isVisible = true
        }
        btnAccessTheCode.setOnClickListener {
            verifyPhoneNumberWithCode(storedVerificationId, etInputCode.text.toString())
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth!!).setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setActivity(requireActivity())
            .setCallbacks(callbacks).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.e("tag", "error")
        }

        override fun onCodeSent(
            verificationId: String, token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth?.signInWithCredential(credential)?.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                PreferenceHelper.showOnBoard = true
                findNavController().navigate(R.id.noteAppFragment)
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireActivity(), " is not registration", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}