package book.store

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import book.store.activities.AdminActivity
import book.store.activities.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            }}
            else
            {startActivity(Intent(this,MainActivity::class.java))
            finish()}
        }, 3000)


    }
}