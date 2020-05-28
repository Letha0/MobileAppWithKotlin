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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import book.store.BookAdapter
import book.store.R
import book.store.api.RetrofitClient
import book.store.models.Book
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {


    private lateinit var homeViewModel: HomeViewModel

    private var isFirstBackPressed = false

    private var books = listOf<Book>()
    private lateinit var apiService: RetrofitClient
    private lateinit var bookAdapter: BookAdapter

   /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if(isFirstBackPressed){
                        finish()
                    } else {
                        isFirstBackPressed = true
                        Toast.makeText(applicationContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            isFirstBackPressed = false

                        }, 3000)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }*/
   open fun onBackPressed() = true

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
       //val textView: TextView = root.findViewById(R.id.text_home)
      // homeViewModel.text.observe(viewLifecycleOwner, Observer {
       //    textView.text = it
       // })
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

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

                    refreshLayout.isRefreshing = false
                    books = response.body()!!

                    bookAdapter = BookAdapter(requireContext(), books)

                    books_recyclerview.adapter = bookAdapter
                    bookAdapter.notifyDataSetChanged()
                }

            })

    }



}
