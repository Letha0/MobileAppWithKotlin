package book.store.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.R
import book.store.api.SessionManager
import book.store.UserOrdersAdapter
import book.store.api.RetrofitClient
import book.store.models.Order
import kotlinx.android.synthetic.main.fragment_order.refreshLayout
import kotlinx.android.synthetic.main.fragment_users_orders.*
import retrofit2.Call
import retrofit2.Response

class SeeOrdersFragment : Fragment() {

    private var orders = listOf<Order>()
    lateinit var session: SessionManager
    private lateinit var orderAdapter: UserOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchOrders()
        }

        user_orders_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchOrders()
    }

    private fun fetchOrders(){
        session = SessionManager(requireContext())

        RetrofitClient.instance.getUserOrders(session.TOKEN)
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

                        orderAdapter = UserOrdersAdapter(requireContext(), orders)

                        user_orders_recyclerview.adapter = orderAdapter
                        orderAdapter.notifyDataSetChanged()

                    }
                }

            })
    }


}
