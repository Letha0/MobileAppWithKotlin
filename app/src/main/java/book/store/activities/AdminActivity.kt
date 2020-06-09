package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.SessionManager
import book.store.ui.admin.AdminFragment
import book.store.ui.author.AuthorFragment
import book.store.ui.book.BookFragment
import book.store.ui.coverType.CoverTypeFragment
import book.store.ui.genre.GenreFragment
import book.store.ui.opinion.OpinionFragment
import book.store.ui.order.OrderFragment
import book.store.ui.payment.PaymentFragment
import book.store.ui.publHouse.PublHouseFragment
import book.store.ui.serie.SeriesFragment
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

    fun btnOpinions(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, OpinionFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnGenres(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, GenreFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnPayments(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, PaymentFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnPublHouse(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, PublHouseFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnSeries(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, SeriesFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnCoverType(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, CoverTypeFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun btnOrders(v:View){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, OrderFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun logout(v: View) {

        SessionManager.getInstance(applicationContext).Logout()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun home(v: View) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    override fun onResume() {
        if(System.currentTimeMillis()>=session.ExpiredDate){
            session.Logout()}

            super.onResume()
    }

}
