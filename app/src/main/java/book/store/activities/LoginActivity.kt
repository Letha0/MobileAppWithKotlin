package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.SessionManager
import book.store.api.*
import book.store.requests.LoginRequest
import book.store.api.Validation
import book.store.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.input_email
import kotlinx.android.synthetic.main.activity_login.input_password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var session: SessionManager
    private val profileFragment = ProfileFragment()
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = SessionManager(applicationContext)

        if(session.isLoggedIn())
        {
            val i = Intent (applicationContext, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
        }

        btn_login.setOnClickListener {


            val email = input_email.text.toString().trim()
            val password = input_password.text.toString().trim()


            if (email.isEmpty()) {
                input_email.error = "Email required"
                input_email.requestFocus()
                return@setOnClickListener
            }


            if (!Validation.isValidEmail(email)) {
                input_email.error = "Enter correct email"
                input_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                input_password.error = "Password required"
                input_password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.login( LoginRequest(email, password))
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {


                        if (response.code() == 200)
                            {Toast.makeText(applicationContext, "You are logged in", Toast.LENGTH_LONG).show()
                                session.createLoginSession(response.body()?.token!!)

                                session.getDetailOfUser(email)
                                val i = Intent (applicationContext, MainActivity::class.java)
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(i)
                            }

                        if (response.code() == 200 && email == "admin@bookstore.io")
                        {Toast.makeText(applicationContext, "You are logged in", Toast.LENGTH_LONG).show()
                            session.createLoginSession(response.body()?.token!!)

                            val i = Intent (applicationContext, AdminActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(i)
                            finish()
                        }

                        if (response.code() == 401)
                            Toast.makeText(applicationContext, "Something went wrong. Check your email and password and try again.", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                })

             }

        btn_signUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_forget_pass.setOnClickListener{
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }


    }
}