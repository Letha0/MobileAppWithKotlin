package book.store.ui.serie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import book.store.R
import book.store.SeriesAdapter
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Serie
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.fragment_series.*
import kotlinx.android.synthetic.main.fragment_series.add_author
import retrofit2.Call
import retrofit2.Response

class SeriesFragment : Fragment() {

    private var series = listOf<Serie>()
    lateinit var session: SessionManager
    private lateinit var serieAdapter: SeriesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchSeries()
        }

        series_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchSeries()

        add_author.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, SerieAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

    }

    private fun fetchSeries(){
        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllSeries(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Serie>>{
                override fun onFailure(call: Call<List<Serie>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Serie>>, response: Response<List<Serie>>) {
                    refreshLayout.isRefreshing = false
                    series = response.body()!!

                    serieAdapter = SeriesAdapter(requireContext(),series)

                    series_recyclerview.adapter = serieAdapter
                    serieAdapter.notifyDataSetChanged()
                }

            })
    }


}
