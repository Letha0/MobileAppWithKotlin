package book.store

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.RetrofitClient
import book.store.api.SessionManager
import book.store.api.SuccessResponse
import book.store.models.Book
import kotlinx.android.synthetic.main.cart_list_products.view.*
import retrofit2.Call
import retrofit2.Response

class SearchAdapter(var context: Context, var books: List<Book> = arrayListOf()) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(viewHolder: SearchAdapter.ViewHolder, position: Int) {
        viewHolder.bindBook(books[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        lateinit var session: SessionManager

        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context
        val addToCart: View = view.findViewById(R.id.btn_add_to_cart)
        var seeBtn: View = view.findViewById(R.id.btn_see)
        fun bindBook(book: Book) {

            session = SessionManager(context)

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

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Book details")
                    setMessage("Title: " + book.title + System.lineSeparator() +
                            "Description: " + book.description + System.lineSeparator() +
                            "Price: " + book.price+ System.lineSeparator())
                }
                    .setCancelable(false)
                    .setPositiveButton("Ok"){dialog, id ->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()

            }



            itemView.main_info.text = book.title

        }

    }

    }