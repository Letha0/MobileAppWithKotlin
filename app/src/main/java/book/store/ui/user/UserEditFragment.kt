package book.store.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.api.Validation
import kotlinx.android.synthetic.main.fragment_edit_user.*

class UserEditFragment: Fragment() {

lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())

        val name = session.name
        edit_name.setText(name)
        val surname = session.surname
        edit_surname.setText(surname)
        val email = session.emailUser
        edit_email.setText(email)

        user_edit_send.setOnClickListener{

            val email = edit_email.text.toString().trim()
            val password = edit_password.text.toString().trim()
            val name = edit_name.text.toString().trim()
            val surname = edit_surname.text.toString().trim()


            if(email.isEmpty()) {
                edit_email.error = "Email required"
                edit_email.requestFocus()
                return@setOnClickListener
            }


            if(!Validation.isValidEmail(email)){
                edit_email.error = "Enter correct email"
                edit_email.requestFocus()
                return@setOnClickListener
            }

            if(name.isEmpty()) {
                edit_name.error = "Name required"
                edit_name.requestFocus()
                return@setOnClickListener
            }

            if(surname.isEmpty()) {
                edit_surname.error = "Surname required"
                edit_surname.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient //later jak będzie na serwie metoda

        }
    }

}