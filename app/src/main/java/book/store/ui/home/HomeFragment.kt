package book.store.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import book.store.BookAdapter
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Book
import book.store.ui.search.SearchFragment
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {


    private var isFirstBackPressed = false

    lateinit var session: SessionManager

    private var books = listOf<Book>()
    private lateinit var apiService: RetrofitClient
    private lateinit var bookAdapter: BookAdapter


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        session = SessionManager(requireContext())


        refreshLayout.setOnRefreshListener {
            fetchBooks()
        }
        books_recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)


        fetchBooks()

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(isFirstBackPressed){

                        activity?.finish()
                    } else {
                        isFirstBackPressed = true
                        Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            isFirstBackPressed = false

                        }, 3000)
                    }
                }

            })

        btn_search.setOnClickListener{

            val title = search.text.toString().trim()

            if(title.isEmpty()) {
                search.error = "Input title"
                search.requestFocus()
                return@setOnClickListener
            }

            session.searchTitle(title)
            val fragmentTransaction = fragmentManager?.beginTransaction()

            fragmentTransaction?.replace(R.id.nav_host_fragment, SearchFragment())

            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()



        }

    }



    private fun fetchBooks(){

        RetrofitClient.instance.getBooks()
            .enqueue(object : retrofit2.Callback<List<Book>>{
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {

                    print(t.message)
                    Log.d("Data error", t.message)
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {

                    if(refreshLayout==null) Toast.makeText(activity, "Try again", Toast.LENGTH_SHORT).show()
                        else
                    {refreshLayout.isRefreshing = false
                    books = response.body()!!

                    bookAdapter = BookAdapter(requireContext(), books)

                    books_recyclerview.adapter = bookAdapter
                    bookAdapter.notifyDataSetChanged()}
                }

            })

    }



}
