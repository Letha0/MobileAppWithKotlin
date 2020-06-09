package book.store.ui.coverType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.CoverType
import book.store.requests.CoverTypeRequest
import kotlinx.android.synthetic.main.fragment_payment_and_publhouse_crud.*
import retrofit2.Call
import retrofit2.Response

class CoverTypeAddFragment:Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_covertype_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        name.text ="Add cover type"

        //add fragment
        send_data.setOnClickListener {
            val name = input_name.text.toString().trim()

            if (name.isEmpty()) {
                input_name.error = "Name required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addCoverType(session.TOKEN, CoverTypeRequest(name))
                .enqueue(object : retrofit2.Callback<CoverType> {
                    override fun onFailure(call: Call<CoverType>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<CoverType>, response: Response<CoverType>) {
                        if (response.code() == 200)
                            Toast.makeText(requireContext(),"Cover type added!",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(requireContext(),response.code().toString(),Toast.LENGTH_SHORT).show()

                    }
                })
        }




    }
}