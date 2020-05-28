package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.SessionManager
import book.store.ui.admin.AdminFragment
import book.store.ui.user.UsersFragment


class AdminActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val adminFragment = AdminFragment()
    private val userFragment = UsersFragment()
    lateinit var backToast: Toast
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, adminFragment)
        fragmentTransaction.commit()


    }

    fun btnUsers(v: View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, userFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }

    fun logout(v: View) {

        SessionManager.getInstance(applicationContext).Logout()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}
