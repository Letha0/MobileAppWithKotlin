package book.store.ui.user

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import book.store.BookAdapter

import book.store.R
import book.store.SessionManager
import book.store.UsersAdapter
import book.store.activities.MainActivity
import book.store.api.RetrofitClient
import book.store.api.UserResponse
import book.store.models.Book
import book.store.models.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_user.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Response

class UsersFragment : Fragment() {

    lateinit var session: SessionManager
    private var users = listOf<User>()
    private lateinit var userAdapter: UsersAdapter



    companion object {
        fun newInstance() = UsersFragment()
    }

    private lateinit var viewModel: UsersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onStart() {
        Thread.sleep(1000)

        session = SessionManager(requireContext())

        if(System.currentTimeMillis()>=session.ExpiredDate){
            session.Logout()
        }
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        refreshLayout.setOnRefreshListener {
            fetchUsers()
        }
        users_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchUsers()

        add_user.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, UserAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }




    private fun fetchUsers(){

        session = SessionManager(requireContext())


        val TOKEN = SessionManager.getInstance(requireContext()).TOKEN

        RetrofitClient.instance.getAllUsers(TOKEN)
            .enqueue(object : retrofit2.Callback<List<User>>{

                override fun onFailure(call: Call<List<User>>, t: Throwable) {

                    Toast.makeText(requireContext(), "You have been logged out", Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                    refreshLayout.isRefreshing = false
                    users = response.body()!!

                    userAdapter = UsersAdapter(requireContext(), users)

                    users_recyclerview.adapter = userAdapter
                    userAdapter.notifyDataSetChanged()

                }

            })

    }

}
