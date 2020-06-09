package book.store.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import book.store.R
import book.store.api.SessionManager
import book.store.UsersAdapter
import book.store.api.RetrofitClient
import book.store.models.User
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Call
import retrofit2.Response

class UsersFragment : Fragment() {

    lateinit var session: SessionManager
    private var users = listOf<User>()
    private lateinit var userAdapter: UsersAdapter


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

                    session.Logout()

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
