package book.store.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import book.store.BookAdapter
import book.store.R
import book.store.SessionManager
import book.store.api.Api
import book.store.models.Book
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    private var books = listOf<Book>()
    private lateinit var apiService: Api
    private lateinit var bookAdapter: BookAdapter
    lateinit var btnLogout:Button
    lateinit var session: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(
            R.id.nav_view
        )

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_account
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)







        }

    /* fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        refreshLayout.setOnRefreshListener {
            fetchBooks()
        }
        books_recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)



        fetchBooks()
    }



    private fun fetchBooks(){

        apiService.getBooks()
            .enqueue(object : retrofit2.Callback<List<Book>>{
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message)
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {

                refreshLayout.isRefreshing = false
                books = response.body()!!

                bookAdapter = BookAdapter(this@MainActivity, books)

                books_recyclerview.adapter = bookAdapter
                bookAdapter.notifyDataSetChanged()
            }

            })

            }

   /* private fun fetchBooks(){
        refreshLayout.isRefreshing = true

        RetrofitClient.instance.getBooks()
            .enqueue(object : Callback<BookResponse> {
                override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<BookResponse>,
                    response: Response<BookResponse>
                ) {
                    refreshLayout.isRefreshing = false
                    val books = response.body()

                    books?.let {
                        showBooks(it)
                    }
                }

            })
    }
     private fun showBooks(movies: List<Book>) {
        books_recyclerview.layoutManager = LinearLayoutManager(this)
        books_recyclerview.adapter = BookAdapter(this@MainActivity, books)
    }*/


*/
}


