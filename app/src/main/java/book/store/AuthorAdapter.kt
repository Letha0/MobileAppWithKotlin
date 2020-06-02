package book.store

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
import book.store.models.Author
import book.store.models.User
import book.store.ui.author.AuthorEditFragment
import book.store.ui.user.UserEditFragment
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Response

class AuthorAdapter(var context: Context, var authors: List<Author> = arrayListOf()):
RecyclerView.Adapter<AuthorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = authors.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindAuthor(authors[position])
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        lateinit var session: SessionManager

        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)

        fun bindAuthor(author:Author){

            session = SessionManager(context)
            editBtn.setOnClickListener{
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, AuthorEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditAuthor(author.id, author.name, author.surname, author.dateOfBirth, author.dateOfDeath, author.description)

                }
                deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setMessage("Are you sure you wan to delete " + author.name + " " + author.surname +"?")
                        .setCancelable(false)
                        .setPositiveButton("Delete") { dialog, id ->
                            RetrofitClient.instance.deleteAuthor(session.TOKEN, author.id)
                                .enqueue(object : retrofit2.Callback<Author>{
                                    override fun onFailure(call: Call<Author>, t: Throwable) {
                                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    }

                                    override fun onResponse(call: Call<Author>,response: Response<Author>) {
                                        Toast.makeText(context, "Author deleted", Toast.LENGTH_SHORT).show()
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




            itemView.main_info.text = author.surname
            itemView.add_info.text = author.name
        }

    }
}