package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.FindTokenToResetPasswordResponse
import book.store.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_enterresetcode.*
import kotlinx.android.synthetic.main.activity_enterresetcode.btn_submit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterResetPasswordTokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterresetcode)

        val email = intent.getStringExtra("email")

        btn_submit.setOnClickListener {

            val token = input_resetcode.text.toString().trim()

            if (token.isEmpty()) {
                input_resetcode.error = "Input reset code!"
                input_resetcode.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.findToken(token)
                .enqueue(object : Callback<FindTokenToResetPasswordResponse> {
                    override fun onFailure(
                        call: Call<FindTokenToResetPasswordResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<FindTokenToResetPasswordResponse>, response: Response<FindTokenToResetPasswordResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent =
                                Intent(applicationContext, EnterNewPasswordActivity::class.java)
                            intent.putExtra("email", email)
                            intent.putExtra("token", token)
                            startActivity(intent)
                        }
                        else
                         Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()

                    }

                })


        }

    }
}