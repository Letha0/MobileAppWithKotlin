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
import book.store.api.SessionManager
import book.store.models.PublishingHouse
import book.store.ui.publHouse.PublHouseEditFragment
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Response

class PublHouseAdapter(var context: Context, var publHouses:List<PublishingHouse> = arrayListOf()):
    RecyclerView.Adapter<PublHouseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = publHouses.size


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindPublHouse(publHouses[position])
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        lateinit var session: SessionManager

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        fun bindPublHouse(publHouse: PublishingHouse) {

            session = SessionManager(context)

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Publishing house details")
                    setMessage("Name: " + publHouse.name + System.lineSeparator() +
                            "Description: " + publHouse.description)
                }
                    .setCancelable(false)
                    .setPositiveButton("Ok"){dialog, id ->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()



            }

            editBtn.setOnClickListener {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, PublHouseEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditPublHouse(publHouse.id, publHouse.name, publHouse.description)

            }

            deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("Are you sure you wan to delete " + publHouse.name+"?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deletePublHouse(session.TOKEN, publHouse.id)
                            .enqueue(object : retrofit2.Callback<String>{
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Toast.makeText(context, "Publishing house deleted", Toast.LENGTH_SHORT).show()
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

            itemView.main_info.text = publHouse.name


        }

    }

}

