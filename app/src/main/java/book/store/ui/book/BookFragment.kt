package book.store.ui.book

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.AddBookAdapter
import book.store.AuthorAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Author
import book.store.models.Book
import book.store.ui.author.AuthorAddFragment
import kotlinx.android.synthetic.main.fragment_book.*
import retrofit2.Call
import retrofit2.Response

class BookFragment : Fragment() {

    private var books = listOf<Book>()
    lateinit var session: SessionManager
    private lateinit var addBookAdapter: AddBookAdapter

    private lateinit var viewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchBooks()
        }

        book_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchBooks()

        add_book.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, BookAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

    private fun fetchBooks(){

        session = SessionManager(requireContext())


        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getBooks()
            .enqueue(object : retrofit2.Callback<List<Book>>{
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                    if(response.code()==200){
                        refreshLayout.isRefreshing = false
                        books = response.body()!!
                        addBookAdapter = AddBookAdapter(requireContext(), books)

                        book_recyclerview.adapter = addBookAdapter
                        addBookAdapter.notifyDataSetChanged()

                    }
                }

            })

    }

}
