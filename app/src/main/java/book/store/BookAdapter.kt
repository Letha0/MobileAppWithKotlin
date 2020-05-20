package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import book.store.models.Book
import kotlinx.android.synthetic.main.book_row_item.view.*
import book.store.ui.home.HomeFragment
import android.annotation.SuppressLint

import com.squareup.picasso.Picasso

class BookAdapter(var context: Context, var books: List<Book> = arrayListOf()) :
        RecyclerView.Adapter<BookAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BookAdapter.ViewHolder {
            // The layout design used for each list item
            val view = LayoutInflater.from(context).inflate(R.layout.book_row_item, null)
            return ViewHolder(view)

        }
        // This returns the size of the list.
        override fun getItemCount(): Int = books.size

        override fun onBindViewHolder(viewHolder: BookAdapter.ViewHolder, position: Int) {
            //we simply call the `bindProduct` function here
            viewHolder.bindBook(books[position])
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            // This displays the product information for each item
            fun bindBook(book: Book) {

                itemView.book_name.text = book.title
                itemView.book_price.text = "$${book.price.toString()}"
               // Picasso.get().load(book.photos).fit().into(itemView.book_image)
            }

        }


}