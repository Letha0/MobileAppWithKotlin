package book.store.ui.opinion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Opinion
import book.store.OpinionAdapter
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.fragment_opinion.*
import retrofit2.Call
import retrofit2.Response

class OpinionFragment : Fragment() {

    private var opinions = listOf<Opinion>()
    lateinit var session: SessionManager
    private lateinit var opinionAdapter: OpinionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_opinion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout.setOnRefreshListener {
            fetchOpinions()
        }

        opinions_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchOpinions()

    }

    fun fetchOpinions()
    {
        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllOpinions(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Opinion>>{
                override fun onFailure(call: Call<List<Opinion>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Opinion>>, response: Response<List<Opinion>>) {
                    refreshLayout.isRefreshing = false
                    opinions = response.body()!!

                    opinionAdapter =
                        OpinionAdapter((requireContext()), opinions)

                    opinions_recyclerview.adapter = opinionAdapter
                    opinionAdapter.notifyDataSetChanged()
                }

            })

    }

}
