package book.store.ui.author

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Author
import book.store.requests.AuthorRequest
import kotlinx.android.synthetic.main.fragment_author_crud.*
import retrofit2.Call
import retrofit2.Response

class AuthorEditFragment : Fragment() {
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_author_crud, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())

        name.text = "Edit author"

        val name = session.name
        input_name.setText(name)
        val surname = session.surname
        input_surname.setText(surname)
        val dateBirth = session.dateBirth.toString()
        input_dateBirth.setText(dateBirth)
        val dateDeath = session.dateDeath.toString()
        input_dateDeath.setText(dateDeath)
        val description = session.description
        input_description.setText(description)

    author_send_data.setOnClickListener{
        val name = input_name.text.toString().trim()
        val surname = input_surname.text.toString().trim()
        val dateBirth = input_dateBirth.text.toString().trim()
        val dateDeath = input_dateDeath.text.toString().trim()
        val description = input_description.text.toString().trim()

        if(name.isEmpty()) {
            input_name.error = "Name required"
            input_name.requestFocus()
            return@setOnClickListener
        }

        if(surname.isEmpty()) {
            input_surname.error = "Surname required"
            input_surname.requestFocus()
            return@setOnClickListener
        }

        if(dateBirth.isEmpty()) {
            input_dateBirth.error = "Date of birth required"
            input_dateBirth.requestFocus()
            return@setOnClickListener
        }

        RetrofitClient.instance.editAuthor(session.TOKEN, session.ID, AuthorRequest(name,surname,dateBirth,dateDeath,description))
            .enqueue(object: retrofit2.Callback<Author>{
                override fun onFailure(call: Call<Author>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Author>, response: Response<Author>) {

                    if(response.code()==200){
                    Toast.makeText(requireContext(), "Author updated!", Toast.LENGTH_SHORT).show()}
                    else Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            })

    }

    }
}