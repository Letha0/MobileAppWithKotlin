package book.store.ui.genre

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.GenreAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Genre
import book.store.ui.author.AuthorAddFragment
import kotlinx.android.synthetic.main.fragment_author.*
import kotlinx.android.synthetic.main.fragment_genre.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import retrofit2.Call
import retrofit2.Response

class GenreFragment : Fragment() {

    private var genres = listOf<Genre>()
    lateinit var session: SessionManager
    private lateinit var genreAdapter: GenreAdapter

    private lateinit var viewModel: GenreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchGenre()
        }
        genre_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchGenre()

        add_item.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, GenreAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        }

    private fun fetchGenre()
    {
        session = SessionManager(requireContext())
        RetrofitClient.instance.getAllGenres(session.TOKEN)
            .enqueue(object: retrofit2.Callback<List<Genre>>{
                override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                    refreshLayout.isRefreshing = false
                    genres = response.body()!!
                    genreAdapter = GenreAdapter(requireContext(), genres)
                    genre_recyclerview.adapter = genreAdapter
                    genreAdapter.notifyDataSetChanged()
                }

            })
    }
}
