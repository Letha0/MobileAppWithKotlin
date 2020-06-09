package book.store.ui.author


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.AuthorAdapter
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Author
import kotlinx.android.synthetic.main.fragment_author.*
import retrofit2.Call
import retrofit2.Response


class AuthorFragment : Fragment() {

    private var authors = listOf<Author>()
    lateinit var session: SessionManager
    private lateinit var authorAdapter: AuthorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchAuthors()
        }

        authors_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchAuthors()

        add_author.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, AuthorAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }


    }

    private fun fetchAuthors(){

        session = SessionManager(requireContext())


        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAllAuthors(TOKEN)
            .enqueue(object : retrofit2.Callback<List<Author>>{
                override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                    refreshLayout.isRefreshing = false
                    authors = response.body()!!

                    authorAdapter = AuthorAdapter(requireContext(), authors)

                    authors_recyclerview.adapter = authorAdapter
                    authorAdapter.notifyDataSetChanged()
                }

            })

    }

}
