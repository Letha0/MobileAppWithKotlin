package book.store.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.SessionManager
import book.store.ui.comments.CommentsFragment

class CommentsActivity: AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    lateinit var session: SessionManager
    private val commentFragment = CommentsFragment()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myCommentFragment, commentFragment)
        fragmentTransaction.commit()

        session = SessionManager(applicationContext)


}}

