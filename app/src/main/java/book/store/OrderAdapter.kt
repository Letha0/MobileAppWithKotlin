package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
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
        var editBtn = view.findViewById<Button>(R.id.btn_edit)
        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context


        fun bindOrders(order: Order) {

            itemView.main_info.text = "Order nr " + order.id.toString()

        }

    }
}