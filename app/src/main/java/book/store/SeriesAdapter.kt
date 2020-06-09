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
import book.store.api.SuccessResponse
import book.store.models.Serie
import book.store.ui.serie.SeriesEditFragment
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Response

class SeriesAdapter(var context: Context, val series:List<Serie> = arrayListOf()):
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = series.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindSeries(series[position])
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        lateinit var session: SessionManager

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        fun bindSeries(serie:Serie)
        {
            session = SessionManager(context)

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Serie details")
                    setMessage("Name: " + serie.name + System.lineSeparator() +
                            "Description: " + serie.description + System.lineSeparator() +
                            "Author: " + serie.author?.name + " " + serie.author?.surname + System.lineSeparator() +
                            "Publishing house: " + serie.publHouse?.name+ System.lineSeparator())
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
                fragmentTransaction.replace(R.id.myFragment, SeriesEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditSerie(serie.id, serie.name, serie.description, serie.authorId, serie.publHouseID)

            }

            deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("Are you sure you wan to delete " + serie.name+"?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deleteSerie(session.TOKEN, serie.id)
                            .enqueue(object : retrofit2.Callback<SuccessResponse>{
                                override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                                    Toast.makeText(context, "Serie deleted", Toast.LENGTH_SHORT).show()
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

            itemView.main_info.text = serie.name

        }
    }

}