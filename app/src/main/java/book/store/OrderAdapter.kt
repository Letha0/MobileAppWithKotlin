package book.store

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.SessionManager
import book.store.models.Order
import kotlinx.android.synthetic.main.order_row_item.view.*

class OrderAdapter (var context: Context, var orders:List<Order> = arrayListOf()):
    RecyclerView.Adapter<OrderAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_row_item, null)
        return OrderAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = orders.size


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindOrders(orders[position])
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        lateinit var session: SessionManager

        var seeBtn = view.findViewById<Button>(R.id.btn_see)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context


        fun bindOrders(order: Order) {

            seeBtn.setOnClickListener{

                val dialogBuilder = AlertDialog.Builder(context)
                with(dialogBuilder)
                {
                    setTitle("Order details")
                    setMessage("User: " + order.user.name + " " + order.user.surname + System.lineSeparator() +
                            "Address: " + order.address + System.lineSeparator() +
                            "City: " + order.city + System.lineSeparator() +
                            "Total price: " + order.totalPrice + System.lineSeparator())
                }
                    .setCancelable(false)
                    .setPositiveButton("Ok"){dialog, id ->
                        dialog.dismiss()
                    }
                val alert = dialogBuilder.create()
                alert.show()



            }

            itemView.main_info.text = "Order nr " + order.id.toString()

        }

    }
}