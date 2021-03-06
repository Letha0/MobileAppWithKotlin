package book.store.ui.publHouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.api.SuccessResponse
import book.store.requests.PublHouseRequest
import kotlinx.android.synthetic.main.fragment_payment_and_publhouse_crud.*
import retrofit2.Call
import retrofit2.Response

class PublHouseAddFragment: Fragment() {
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

        name.text ="Add new publishing house"

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

            RetrofitClient.instance.addPublHouse(session.TOKEN, PublHouseRequest(name, description))
                .enqueue(object : retrofit2.Callback<SuccessResponse> {
                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                        if (response.code() == 200)
                            Toast.makeText(
                                requireContext(),
                                "Publishing house added!",
                                Toast.LENGTH_SHORT
                            ).show()
                        else
                            Toast.makeText(
                                requireContext(),
                                response.code().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                    }

                })
        }
    }
}