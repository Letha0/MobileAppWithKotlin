package book.store

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.RetrofitClient
import book.store.api.SessionManager
import book.store.models.Book
import book.store.ui.book.BookEditFragment
import kotlinx.android.synthetic.main.book_row_item_admin.view.*
import retrofit2.Call
import retrofit2.Response

class CrudBookAdapter (var context: Context, var books: List<Book> = arrayListOf()) :
    RecyclerView.Adapter<CrudBookAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CrudBookAdapter.ViewHolder {
        // The layout design used for each list item
        val view = LayoutInflater.from(context).inflate(R.layout.book_row_item_admin, null)
        return CrudBookAdapter.ViewHolder(view)

    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindBooks(books[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var session: SessionManager

        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)

        fun bindBooks(book: Book) {
            session = SessionManager(context)

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Book details")
                    setMessage("Title: " + book.title + System.lineSeparator() +
                            "Description: " + book.description + System.lineSeparator() +
                            "Author: " + book.author.name + " " + book.author.surname + System.lineSeparator() +
                            "Genre: " + book.genre?.title + System.lineSeparator() +
                            "Serie: " + book.serie.name+ System.lineSeparator() +
                            "Price: " + book.price+ System.lineSeparator())
                }
                    .setCancelable(false)
                    .setPositiveButton("Ok"){dialog, id ->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()



            }

            editBtn.setOnClickListener {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, BookEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditBook(book.id, book.title, book.author.id, book.genre?.id, book.description, book.serie.id, book.release,
                book.covertype.id, book.publHouse?.id, book.price, book.coverImage)
            }

            deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("Are you sure you wan to delete " + book.title +"?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deleteBook(session.TOKEN, book.id)
                            .enqueue(object : retrofit2.Callback<Book>{
                                override fun onFailure(call: Call<Book>, t: Throwable) {
                                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun onResponse(call: Call<Book>,response: Response<Book>) {
                                    if(response.code()==200){
                                    Toast.makeText(context, "Book deleted", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                    }else {
                                        Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    }
                                }

                            })

                    }
                    .setNegativeButton("Dismiss"){dialog, id->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()

                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)

            }


            itemView.main_info.text = book.author.name
            itemView.add_info.text = book.author.surname
            itemView.title.text = book.title
        }


    }
}