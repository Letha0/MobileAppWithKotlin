package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import book.store.models.Book
import kotlinx.android.synthetic.main.book_row_item.view.*
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import book.store.activities.SeeBookActivity
import book.store.api.RetrofitClient
import book.store.api.SessionManager
import book.store.api.SuccessResponse

import retrofit2.Call
import retrofit2.Response

class BookAdapter(var context: Context, var books: List<Book> = arrayListOf()) :
        RecyclerView.Adapter<BookAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookAdapter.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.book_row_item, null)
            return ViewHolder(view)

        }
        override fun getItemCount(): Int = books.size


    override fun onBindViewHolder(viewHolder: BookAdapter.ViewHolder, position: Int) {
            viewHolder.bindBook(books[position])
        }



        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
           // public var listOfBookInCart: ArrayList<Book> = ArrayList<Book>()

            lateinit var bookincart :Book
            lateinit var session: SessionManager

            var context: Context = itemView.context

            var fragmentManager =
                (view.context as FragmentActivity).supportFragmentManager //to handle context

            val addToCart: View = view.findViewById(R.id.addToCart)
            val see: View = view.findViewById(R.id.seebook)

            fun bindBook(book: Book) {

                session = SessionManager(context)

                see.setOnClickListener{
                    val i = Intent (context, SeeBookActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(i)

                    //Navigation.createNavigateOnClickListener(R.id.navigate_to_book)
                    session.getDataToSeeBook(book.id, book.title, book.author.surname, book.author.name, book.genre?.title, book.description, book.price)
                }

                addToCart.setOnClickListener{

                    var cookie = session.cookie
                    if(cookie==null)  cookie = "abc"

                    RetrofitClient.instance.addToCart(cookie, session.TOKEN, book.id)
                        .enqueue(object : retrofit2.Callback<SuccessResponse>{
                            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(
                                call: Call<SuccessResponse>,
                                response: Response<SuccessResponse>
                            ) {
                                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                                session.createCookieSession(response.headers()["Set-Cookie"]!!)
                            }

                        })
                }


                itemView.book_name.text = book.title
                itemView.book_price.text = "$${book.price.toString()}"
            }

        }


}