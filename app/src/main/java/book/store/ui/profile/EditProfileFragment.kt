package book.store.ui.profile

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
import book.store.api.Validation
import book.store.requests.AccUpdateRequest
import kotlinx.android.synthetic.main.fragment_edit_user.*
import retrofit2.Call
import retrofit2.Response

class EditProfileFragment: Fragment(){

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())

        val name = session.userName
        edit_name.setText(name)
        val surname = session.userSurname
        edit_surname.setText(surname)
        val email = session.userEmail
        edit_email.setText(email)

        user_edit_send.setOnClickListener {

            val email = edit_email.text.toString().trim()
            val name = edit_name.text.toString().trim()
            val surname = edit_surname.text.toString().trim()

            if (email.isEmpty()) {
                edit_email.error = "Email required"
                edit_email.requestFocus()
                return@setOnClickListener
            }


            if (!Validation.isValidEmail(email)) {
                edit_email.error = "Enter correct email"
                edit_email.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                edit_name.error = "Name required"
                edit_name.requestFocus()
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                edit_surname.error = "Surname required"
                edit_surname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.updateSelfAcc(session.TOKEN, session.userID, AccUpdateRequest(email,name,surname))
                .enqueue(object : retrofit2.Callback<SuccessResponse>{
                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                        if(response.code()==200){
                            Toast.makeText(requireContext(), "You edited your account!", Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }


}