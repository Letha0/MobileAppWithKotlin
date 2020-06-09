package book.store.ui.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Payment
import book.store.requests.PaymentRequest
import kotlinx.android.synthetic.main.fragment_payment_and_publhouse_crud.*
import retrofit2.Call
import retrofit2.Response

class PaymentEditFragment: Fragment() {
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment_and_publhouse_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        name.text ="Edit payment method"

        val name = session.name
        input_name.setText(name)
        val description = session.description
        input_description.setText(description)

        send_data.setOnClickListener {
            val name = input_name.text.toString().trim()
            val description = input_description.text.toString().trim()



            if (name.isEmpty()) {
                input_name.error = "Name required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                input_description.error = "Description required"
                input_description.requestFocus()
                return@setOnClickListener
            }

            if(description.length < 20) {
                input_description.error = "Description have to have 20 characters minimum!"
                input_description.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.editPayment(session.TOKEN, session.ID, PaymentRequest(name, description))
                .enqueue(object : retrofit2.Callback<Payment> {
                    override fun onFailure(call: Call<Payment>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                        if(response.code()==200)
                            Toast.makeText(requireContext(), "Pay method edited!", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }
}