package com.febrian.sharekmm.android.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.febrian.sharekmm.android.AuthViewModel
import com.febrian.sharekmm.android.MainActivity
import com.febrian.sharekmm.android.databinding.ActivityRegisterBinding
import com.febrian.sharekmm.android.tweet.TweetActivity
import com.febrian.sharekmm.android.utils.Constant
import com.febrian.sharekmm.android.utils.Helper
import com.febrian.sharekmm.android.utils.PreferenceManager
import com.febrian.sharekmm.response.AuthState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var helper: Helper

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (preferenceManager.getString(Constant.AUTH_ID).isNotEmpty()) helper.moveActivity(
            this,
            TweetActivity()
        )

        binding.btnRegister.setOnClickListener {
            if (getEmail().isEmpty() || getPassword().isEmpty() || getPassword().length < 6 || !Patterns.EMAIL_ADDRESS.matcher(
                    getEmail()
                ).matches()
            ) return@setOnClickListener

            authViewModel.register(getEmail(), getPassword())
            authViewModel.authState.observe(this) {
                when (it) {
                    is AuthState.Success -> {
                        helper.showLog(it.data.data?.id.toString())
                        helper.showToast(it.data.data?.id.toString())

                        preferenceManager.putString(Constant.AUTH_ID, it.data.data?.id.toString())
                        helper.moveActivity(this, TweetActivity())
                    }

                    is AuthState.Error -> {
                        helper.showLog(it.message)
                        helper.showToast(it.message)
                    }

                    is AuthState.Loading -> {
                        helper.showLog("Loading")
                        helper.showToast("Loading")
                    }
                }
            }

            //authViewModel.sendCode(getEmail())

            /*  val intent = Intent(applicationContext, OtpVerificationActivity::class.java)
              intent.putExtra(AuthConstant.EMAIL, getEmail())
              intent.putExtra(AuthConstant.PASSWORD, getPassword())
              startActivity(intent)*/
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun getEmail(): String {
        val email = binding.email.text.toString()
        if (email.isEmpty()) {
            binding.email.error = "Email must be filled"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Email is not valid"
        }

        return email
    }

    private fun getPassword(): String {

        val password = binding.password.text.toString()
        if (password.isEmpty()) {
            binding.password.error = "Password must be filled"
        } else if (password.length < 6) {
            binding.password.error = "Password must be consist of at least 6 characters"
        }

        return password
    }
}