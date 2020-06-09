package book.store.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.OrderAdapter

import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Order
import kotlinx.android.synthetic.main.fragment_author.refreshLayout
import kotlinx.android.synthetic.main.fragment_order.*
import retrofit2.Call
import retrofit2.Response

class OrderFragment : Fragment() {

    private var orders = listOf<Order>()
    lateinit var session: SessionManager
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchOrders()
        }

        orders_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchOrders()
    }

    private fun fetchOrders(){
        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllOrders(session.cookie, session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Order>>{
                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    if(refreshLayout == null)
                        Toast.makeText(requireContext(), "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    else {
                        refreshLayout.isRefreshing = false
                        orders = response.body()!!

                        orderAdapter = OrderAdapter(requireContext(), orders)

                        orders_recyclerview.adapter = orderAdapter
                        orderAdapter.notifyDataSetChanged()
                    }
                }
            })
    }

}
