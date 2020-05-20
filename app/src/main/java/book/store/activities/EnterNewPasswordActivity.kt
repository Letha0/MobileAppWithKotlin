package book.store.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.RetrofitClient
import book.store.requests.EnterNewPasswordRequest
import book.store.api.EnterNewPasswordResponse
import kotlinx.android.synthetic.main.activity_enternewpass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterNewPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enternewpass)

        val email = intent.getStringExtra("email")
        val token = intent.getStringExtra("token")

        btn_submit.setOnClickListener {


            val password = input_password.text.toString().trim()
            val passwordConfirmation = input_passwordconf.text.toString().trim()



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
                input_passwordconf.error = "Password confirmation required"
                input_passwordconf.requestFocus()
                return@setOnClickListener
            }

            if(passwordConfirmation!=password) {
                input_passwordconf.error = "Passwords fields are not equals"
                input_passwordconf.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.resetPass( EnterNewPasswordRequest(email, password, passwordConfirmation, token))
                .enqueue(object: Callback<EnterNewPasswordResponse> {
                    override fun onFailure(call: Call<EnterNewPasswordResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<EnterNewPasswordResponse>, response: Response<EnterNewPasswordResponse>) {
                        if (response.code() == 200)
                            Toast.makeText(applicationContext, "Ok!", Toast.LENGTH_LONG).show()
                        if (response.code() == 404)
                            Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                })
        }

    }


}