package book.store

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import book.store.api.RetrofitClient
import book.store.models.Opinion
import kotlinx.android.synthetic.main.opinion_row_item.view.*
import retrofit2.Call
import retrofit2.Response

class OpinionAdapter (var context: Context, var opinions: List<Opinion> = arrayListOf()):
    RecyclerView.Adapter<OpinionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.opinion_row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = opinions.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindOpinion(opinions[position])
    }

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {


    lateinit var session: SessionManager
    var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
    var context: Context = itemView.context

    fun bindOpinion(opinion: Opinion) {

        session = SessionManager(context)

        deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage("Are you sure you wan to delete opinion " + opinion.id + ". " + opinion.title +"?")
                .setCancelable(false)
                .setPositiveButton("Delete") { dialog, id ->
                    RetrofitClient.instance.deleteOpinion(session.TOKEN, opinion.id)
                        .enqueue(object : retrofit2.Callback<String>{
                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }

                            override fun onResponse(call: Call<String>,response: Response<String>) {
                                Toast.makeText(context, "Opinion deleted", Toast.LENGTH_SHORT).show()
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



        itemView.main_info.text = opinion.id.toString()
        itemView.add_info.text = opinion.title
        itemView.content.text = opinion.content
    }




}


}