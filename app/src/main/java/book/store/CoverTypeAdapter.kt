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
import book.store.models.CoverType
import book.store.ui.coverType.CoverTypeEditFragment
import kotlinx.android.synthetic.main.row_item.view.*
import retrofit2.Call
import retrofit2.Response

class CoverTypeAdapter(var context: Context, var coverTypes:List<CoverType> = arrayListOf()):
RecyclerView.Adapter<CoverTypeAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int= coverTypes.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindCoverType(coverTypes[position])
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        lateinit var session: SessionManager

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        fun bindCoverType(coverType: CoverType){

            session = SessionManager(context)

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Cover type details")
                    setMessage("Name: " + coverType.name)
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
                fragmentTransaction.replace(R.id.myFragment, CoverTypeEditFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                session.getDataToEditCoverType(coverType.id, coverType.name)

            }

            deleteBtn.setOnClickListener{               //see delete alert and confir or dismiss
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setMessage("Are you sure you want to delete " + coverType.name+"?")
                    .setCancelable(false)
                    .setPositiveButton("Delete") { dialog, id ->
                        RetrofitClient.instance.deleteCoverType(session.TOKEN, coverType.id)
                            .enqueue(object : retrofit2.Callback<String>{
                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }

                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                    Toast.makeText(context, "Cover type deleted", Toast.LENGTH_SHORT).show()
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

            itemView.main_info.text = coverType.name

        }

    }


}