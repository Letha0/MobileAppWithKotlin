package book.store.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.api.SuccessResponse
import kotlinx.android.synthetic.main.activity_see_book.*
import retrofit2.Call
import retrofit2.Response

class SeeBookActivity: AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_book)

        session = SessionManager(applicationContext)

        titlebook.text = session.title
        author.text = session.authorName + " " + session.authorSurname
        genre.text = session.genre
        description.text = session.description
        price.text = session.price.toString() + "$"

        cart.setOnClickListener{

            var cookie = session.cookie
            if(cookie==null)  cookie = "abc"

            RetrofitClient.instance.addToCart(cookie, session.TOKEN, session.ID)
                .enqueue(object : retrofit2.Callback<SuccessResponse>{
                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                        Toast.makeText(applicationContext, "Added to cart", Toast.LENGTH_SHORT).show()
                        session.createCookieSession(response.headers()["Set-Cookie"]!!)
                    }

                })
        }


        comment.setOnClickListener {

            val i = Intent (applicationContext, CommentsActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()

        }
        }



}