package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.SessionManager

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({

            if(SessionManager.getInstance(applicationContext).isLoggedIn()){
            val email = SessionManager.getInstance(applicationContext).EMAIL
            if(email=="admin@bookstore.io") {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
            else {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }}
            else
            {startActivity(Intent(this,MainActivity::class.java))
            finish()}
        }, 3000)


    }
}