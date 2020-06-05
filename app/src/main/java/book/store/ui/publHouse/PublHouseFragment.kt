package book.store.ui.publHouse

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.PaymentsAdapter
import book.store.PublHouseAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Payment
import book.store.models.PublishingHouse
import book.store.ui.payment.PaymentAddFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_publ_house.*
import kotlinx.android.synthetic.main.fragment_publ_house.add
import retrofit2.Call
import retrofit2.Response

class PublHouseFragment : Fragment() {

    private var publHouses = listOf<PublishingHouse>()
    lateinit var session: SessionManager
    private lateinit var publHouseAdapter: PublHouseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publ_house, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchPublHouses()
        }
        publhouse_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchPublHouses()

        add.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, PublHouseAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

    }

private fun fetchPublHouses(){
    session = SessionManager(requireContext())

    RetrofitClient.instance.getAllPublHouses(session.TOKEN)
        .enqueue(object : retrofit2.Callback<List<PublishingHouse>>{
            override fun onFailure(call: Call<List<PublishingHouse>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<PublishingHouse>>,
                response: Response<List<PublishingHouse>>
            ) {
               refreshLayout.isRefreshing = false
                publHouses = response.body()!!
                publHouseAdapter = PublHouseAdapter(requireContext(), publHouses)

                publhouse_recyclerview.adapter = publHouseAdapter
                publHouseAdapter.notifyDataSetChanged()
            }

        })
}

}
