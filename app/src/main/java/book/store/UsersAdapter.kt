package book.store

//import book.store.models.User
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.RetrofitClient
import book.store.models.User
import book.store.ui.user.UserEditFragment
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Response


class UsersAdapter(var context: Context, var users: List<User> = arrayListOf()) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, null)
        return ViewHolder(view)
}

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindUser(users[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var session: SessionManager

        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        fun bindUser(user: User) {
                        session = SessionManager(context)
                        editBtn.setOnClickListener{         //go to edit layout
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.myFragment,UserEditFragment()                            )
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                            session.getDataToEditUser(user.name, user.surname, user.email)
                        }

                        deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                            val dialogBuilder = AlertDialog.Builder(context)
                            dialogBuilder.setMessage("Are you sure you wan to delete " + user.email +"?")
                                .setCancelable(false)
                                .setPositiveButton("Delete") { dialog, id ->
                                    RetrofitClient.instance.deleteUser(session.TOKEN, user.id)
                                        .enqueue(object : retrofit2.Callback<User>{
                                            override fun onFailure(call: Call<User>, t: Throwable) {
                                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                                dialog.dismiss()
                                            }

                                            override fun onResponse(call: Call<User>,response: Response<User>) {
                                                Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show()
                                                dialog.dismiss()
                                            }

                                        })

                                }
                                .setNegativeButton("Dismiss"){dialog, id->
                                    dialog.dismiss()
                                }
                            val alert = dialogBuilder.create()
                            alert.show()

                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN)

                        }




        itemView.main_info.text = user.email       //put user email on list
     } }

    override fun getItemCount(): Int = users.size



}