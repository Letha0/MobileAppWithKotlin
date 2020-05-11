package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.RetrofitClient
import book.store.api.RegisterResponse
import book.store.api.Validation
import book.store.requests.RegisterRequest
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btn_register.setOnClickListener {

            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()
            val passwordConfirmation = input_pass_conf.text.toString().trim()
            val name = input_name.text.toString().trim()
            val surname = input_surname.text.toString().trim()

            if(email.isEmpty()) {
                input_email.error = "Email required"
                input_email.requestFocus()
                return@setOnClickListener
            }


            if(!Validation.isValidEmail(email)){
                input_email.error = "Enter correct email"
                input_email.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                input_password.error = "Password required"
                input_password.requestFocus()
                return@setOnClickListener
            }

            if(password.length < 10) {
                input_password.error = "Password has to be at least 10 char long"
                input_password.requestFocus()
                return@setOnClickListener
            }


            if(passwordConfirmation.isEmpty()) {
                input_pass_conf.error = "Password confirmation required"
                input_pass_conf.requestFocus()
                return@setOnClickListener
            }

            if(passwordConfirmation!=password) {
                input_pass_conf.error = "Passwords fields are not equals"
                input_pass_conf.requestFocus()
                return@setOnClickListener
            }

            if(name.isEmpty()) {
                input_name.error = "Name required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            if(surname.isEmpty()) {
                input_surname.error = "Surname required"
                input_surname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.register( RegisterRequest(email, password, passwordConfirmation, name, surname))
                .enqueue(object: Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.code() == 200)
                        Toast.makeText(applicationContext, "Register successful. Check your email and confirm your account", Toast.LENGTH_LONG).show()
                        if (response.code() == 422)
                            Toast.makeText(applicationContext, "Registration failed. Please, make sure you put in a correct credential and you already have not account with this email.", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                })//response.code().toString()

        }

        btn_signIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}