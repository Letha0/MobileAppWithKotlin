package book.store.ui.author

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.AuthorAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Author
import book.store.ui.user.UserAddFragment
import kotlinx.android.synthetic.main.fragment_author.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import retrofit2.Call
import retrofit2.Response

class AuthorFragment : Fragment() {

    private var authors = listOf<Author>()
    lateinit var session: SessionManager
    private lateinit var authorAdapter: AuthorAdapter

    private lateinit var viewModel: AuthorViewModel

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
