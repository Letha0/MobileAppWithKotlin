package book.store.ui.profile

import UserRequest
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import book.store.BookAdapter

import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.api.UserResponse
import book.store.models.User
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Response

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    lateinit var session: SessionManager
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        session = SessionManager(requireContext())


        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAccount(TOKEN)
            .enqueue(object : retrofit2.Callback<UserResponse>{
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {

                    //val email = response.body()?.email!!
                   // refreshLayout.isRefreshing = false
                    //val user: HashMap<String,String> = session.showUser()
                   // val email: String = user.get(SessionManager.EMAIL)!!
                    //user_name.text = email
                    Toast.makeText(activity, "ok", Toast.LENGTH_LONG).show()

                }


            })
    }



}
