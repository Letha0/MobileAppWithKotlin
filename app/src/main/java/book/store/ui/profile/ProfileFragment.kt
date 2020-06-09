package book.store.ui.profile

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
import book.store.activities.LoginActivity
import book.store.api.RetrofitClient
import book.store.api.UserResponse
import book.store.ui.order.SeeOrdersFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Response


class ProfileFragment : Fragment() {

    private val user = "admin@bookstore.io"
    lateinit var session: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())

        if(session.isLoggedIn())
        {
            if(session.EMAIL==user)
                startActivity(Intent(requireContext(), AdminActivity::class.java))
        }
        else{
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)}



        orders.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_host_fragment, SeeOrdersFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        }

        logout.setOnClickListener{
            session.Logout()
            Toast.makeText(activity, "You have been logout", Toast.LENGTH_SHORT).show()
        }

        edit_acc.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_host_fragment, EditProfileFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        user_name.text = session.userEmail


        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAccount(TOKEN)
            .enqueue(object : retrofit2.Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {

                    if (response.body()?.error != null) {
                        session.Logout()
                        val i = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(i)
                        Toast.makeText(activity,"Your session expired. Please login to continue.", Toast.LENGTH_LONG).show()
                    } else {
                        session.getDetailOfUser(response.body()?.email!!)
                        val email = session.EMAIL//response.body()?.email.toString()

                        session.getUserEmail(response.body()?.email!!)
                        session.getIdUser(response.body()?.id!!)
                        session.getUserSurname(response.body()?.surname!!)
                        session.getUserName(response.body()?.name!!)

                    }
                }
            })
    }
}
