package book.store.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.activities.AdminActivity
import book.store.api.AddUserResponse
import book.store.api.RetrofitClient
import book.store.api.Validation
import book.store.requests.NewUserRequest
import kotlinx.android.synthetic.main.fragment_add_user.*
import retrofit2.Call
import retrofit2.Response

class UserAddFragment: Fragment() {

    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_add_send.setOnClickListener{


            val email = add_email.text.toString().trim()
            val password = add_password.text.toString().trim()
            val name = add_name.text.toString().trim()
            val surname = add_surname.text.toString().trim()
            val role = add_role.text.toString().trim()


            if(!dataValid(email, password,name,surname,role)) return@setOnClickListener

            session = SessionManager(requireContext())


            val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

            RetrofitClient.instance.addUser(TOKEN, NewUserRequest(email,password,name,surname,role))
                .enqueue(object : retrofit2.Callback<AddUserResponse>{
                    override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddUserResponse>,
                        response: Response<AddUserResponse>
                    ) {
                        if(response.code()==200){
                        val i = Intent(requireContext(), AdminActivity::class.java)
                        startActivity(i)
                        Toast.makeText(activity, "Success! User added.", Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(activity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }





private fun dataValid(email:String, password:String, name:String, surname:String, role:String) :Boolean
{
    if(name.isEmpty()) {
        add_name.error = "Name required"
        add_name.requestFocus()
        return false
    }

    if(surname.isEmpty()) {
        add_surname.error = "Surname required"
        add_surname.requestFocus()
        return false
    }

    if(email.isEmpty()) {
        add_email.error = "Email required"
        add_email.requestFocus()
        return false
    }

    if(!Validation.isValidEmail(email)){
        add_email.error = "Enter correct email"
        add_email.requestFocus()
        return false
    }

    if(password.isEmpty()) {
        add_password.error = "Password required"
        add_password.requestFocus()
        return false
    }

    if(password.length < 10) {
        add_password.error = "Password has to be at least 10 char long"
        add_password.requestFocus()
        return false
    }



    if(role.isEmpty())
    {
        add_role.error="Role required"
        add_role.requestFocus()
        return false
    }

    if(role!="admin" && role!="customer")
    {
        add_role.error="Enter proper role"
        add_role.requestFocus()
        return false
    }


    return true
}













}