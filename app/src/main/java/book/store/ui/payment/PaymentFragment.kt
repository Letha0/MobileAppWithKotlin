package book.store.ui.payment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.PaymentsAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Payment
import book.store.ui.author.AuthorAddFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.fragment_payment.*
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Response

class PaymentFragment : Fragment() {

    private var payments = listOf<Payment>()
    lateinit var session: SessionManager
    private lateinit var paymentAdapter:PaymentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchPayments()
        }

        payments_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchPayments()

        add.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, PaymentAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        }

    private fun fetchPayments(){
        session = SessionManager(requireContext())

    RetrofitClient.instance.getAllPayments(session.TOKEN)
        .enqueue(object: retrofit2.Callback<List<Payment>>{
            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                refreshLayout.isRefreshing = false
                payments = response.body()!!
                paymentAdapter = PaymentsAdapter(requireContext(),payments)

                payments_recyclerview.adapter = paymentAdapter
                paymentAdapter.notifyDataSetChanged()
            }

        })

    }
}
