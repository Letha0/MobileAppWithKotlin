package book.store.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.AddAuthorResponse
import book.store.api.RetrofitClient
import book.store.requests.NewAuthorRequest
import kotlinx.android.synthetic.main.fragment_author_crud.*
import retrofit2.Call
import retrofit2.Response

class AuthorAddFragment : Fragment() {
    lateinit var session: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_author_crud, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.text = "Add new author"

        author_send_data.setOnClickListener {
            val name = input_name.text.toString().trim()
            val surname = input_surname.text.toString().trim()
            val dateBirth = input_dateBirth.text.toString().trim()
            val dateDeath = input_dateDeath.text.toString().trim()
            val description = input_description.text.toString().trim()

            if (name.isEmpty()) {
                input_name.error = "Name required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                input_surname.error = "Surname required"
                input_surname.requestFocus()
                return@setOnClickListener
            }

            if (dateBirth.isEmpty()) {
                input_dateBirth.error = "Date of birth required"
                input_dateBirth.requestFocus()
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                input_description.error = "Date of birth required"
                input_description.requestFocus()
                return@setOnClickListener
            }

            session = SessionManager(requireContext())

            RetrofitClient.instance.addAuthor(session.TOKEN, NewAuthorRequest(name,surname,dateBirth,dateDeath,description))
                .enqueue(object : retrofit2.Callback<AddAuthorResponse> {
                    override fun onFailure(call: Call<AddAuthorResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddAuthorResponse>, response: Response<AddAuthorResponse>) {
                        Toast.makeText(requireContext(), "Author added!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })


        }

    }
}