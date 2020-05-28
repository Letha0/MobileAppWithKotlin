package book.store.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.SessionManager
import book.store.activities.LoginActivity
import book.store.api.RetrofitClient
import book.store.api.UserResponse
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

    // override fun onActivityCreated(savedInstanceState: Bundle?) {
    //     super.onActivityCreated(savedInstanceState)
    //     viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    //  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManager(requireContext())


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
                        Toast.makeText(
                            activity,
                            "Your session expired. Please login to continue.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        session.getDetailOfUser(response.body()?.email!!)
                        val email = session.EMAIL//response.body()?.email.toString()

                        user_name.text = user_name.text.toString()
                        user_name.text = email
                    }
                    Toast.makeText(activity, response.body().toString(), Toast.LENGTH_LONG).show()

                }


            })
    }
}
