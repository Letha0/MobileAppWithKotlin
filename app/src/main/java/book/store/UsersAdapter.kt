package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import book.store.models.User
//import book.store.models.User
import kotlinx.android.synthetic.main.user_row_item.view.*

class UsersAdapter(var context: Context, var users: List<User> = arrayListOf()) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_row_item, null)
        return ViewHolder(view)
}

    override fun onBindViewHolder(viewHolder: UsersAdapter.ViewHolder, position: Int) {
        viewHolder.bindUser(users[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // This displays the product information for each item
    fun bindUser(user: User) {

        itemView.user_id.text = user.id.toString()
        itemView.user_email.text = user.email
     } }

    override fun getItemCount(): Int = users.size
}

