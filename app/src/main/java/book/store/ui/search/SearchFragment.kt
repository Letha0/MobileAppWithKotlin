package book.store.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.R
import book.store.SearchAdapter
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Book
import book.store.requests.SearchRequest
import kotlinx.android.synthetic.main.fragment_book.refreshLayout
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Response


class SearchFragment:Fragment(){

    private var books = listOf<Book>()
    lateinit var session: SessionManager
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchBooks()
        }

        search_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchBooks()

    }

    private fun fetchBooks(){

        session = SessionManager(requireContext())


        RetrofitClient.instance.search(SearchRequest(session.search!!))
            .enqueue(object : retrofit2.Callback<List<Book>>{
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                    if(response.code()==200){
                        refreshLayout.isRefreshing = false
                        books = response.body()!!
                        searchAdapter = SearchAdapter(requireContext(), books)

                        search_recyclerview.adapter = searchAdapter
                        searchAdapter.notifyDataSetChanged()

                    }
                }

            })

    }

}