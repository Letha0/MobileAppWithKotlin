package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.SessionManager
import book.store.ui.admin.AdminFragment
import book.store.ui.author.AuthorFragment
import book.store.ui.book.BookFragment
import book.store.ui.user.UsersFragment


class AdminActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val adminFragment = AdminFragment()
    private val userFragment = UsersFragment()
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, adminFragment)
        fragmentTransaction.commit()

        session = SessionManager(applicationContext)

        val timer: CountDownTimer = object : CountDownTimer(3 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //Some code
            }

            override fun onFinish() {
                session.Logout()
            }
        }


    }

    fun btnUsers(v: View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, userFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


    }

    fun btnAuthors(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, AuthorFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnBooks(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, BookFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun logout(v: View) {

        SessionManager.getInstance(applicationContext).Logout()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    override fun onResume() {
        if(System.currentTimeMillis()>=session.ExpiredDate){
            session.Logout()}

            super.onResume()
    }

}
