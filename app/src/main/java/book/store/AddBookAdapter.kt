package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.RetrofitClient
import book.store.models.Author
import book.store.models.Book
import book.store.ui.author.AuthorEditFragment
import book.store.ui.book.BookEditFragment
import kotlinx.android.synthetic.main.book_row_item_admin.view.*
import retrofit2.Call
import retrofit2.Response

class AddBookAdapter (var context: Context, var books: List<Book> = arrayListOf()) :
    RecyclerView.Adapter<AddBookAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AddBookAdapter.ViewHolder {
        // The layout design used for each list item
        val view = LayoutInflater.from(context).inflate(R.layout.book_row_item_admin, null)
        return AddBookAdapter.ViewHolder(view)

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

        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)

        fun bindBooks(book: Book) {
            session = SessionManager(context)

            editBtn.setOnClickListener {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, BookEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditBook(book.id, book.title, book.authorId, book.genreId, book.description, book.seriesId, book.release,
                book.coverTypeId, book.publishingHouseId, book.price, book.coverImage)
            }


            itemView.main_info.text = book.author.name
            itemView.add_info.text = book.author.surname
            itemView.title.text = book.title
        }


    }
}