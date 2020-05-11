package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.PasswordResetResponse
import book.store.api.RetrofitClient
import book.store.api.Validation
import book.store.requests.PasswordResetRequest
import kotlinx.android.synthetic.main.activity_resetpass.*
import kotlinx.android.synthetic.main.activity_resetpass.input_email
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpass)

        btn_submit.setOnClickListener {

            val email = input_email.text.toString().trim()

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


            RetrofitClient.instance.createNewPass( PasswordResetRequest(email))
                .enqueue(object: Callback<PasswordResetResponse> {
                    override fun onFailure(call: Call<PasswordResetResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<PasswordResetResponse>, response: Response<PasswordResetResponse>) {
                        val intent =
                        Intent(applicationContext, EnterResetPasswordTokenActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }

                })




        }



    }
}