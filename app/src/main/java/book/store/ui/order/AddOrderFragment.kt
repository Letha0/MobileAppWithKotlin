package book.store.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.activities.LoginActivity
import book.store.api.RetrofitClient
import book.store.api.SuccessResponse
import book.store.requests.OrderRequest
import kotlinx.android.synthetic.main.fragment_add_order.*
import kotlinx.android.synthetic.main.fragment_payment_and_publhouse_crud.send_data
import retrofit2.Call
import retrofit2.Response

class AddOrderFragment: Fragment() {
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())


        send_data.setOnClickListener {

            val address = input_address.text.toString().trim()
            val city = input_city.text.toString().trim()


            if (address.isEmpty()) {
                input_address.error = "Address required"
                input_address.requestFocus()
                return@setOnClickListener
            }

            if (city.isEmpty()) {
                input_city.error = "Address required"
                input_city.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addOrder(session.cookie, session.TOKEN, OrderRequest(address,city))
                .enqueue(object : retrofit2.Callback<SuccessResponse>{
                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                       if(response.code()==201)
                           Toast.makeText(requireContext(), "Order saved", Toast.LENGTH_SHORT).show()
                        else if(response.code()==200)
                           Toast.makeText(requireContext(), "Cart is empty. Action dismiss", Toast.LENGTH_SHORT).show()
                        else if(response.code()==403){
                           Toast.makeText(requireContext(), "You need to login first", Toast.LENGTH_SHORT).show()
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)}
                        else Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }

                })

        }


    }


}