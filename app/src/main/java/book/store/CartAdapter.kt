package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import book.store.api.SessionManager
import book.store.models.*
import kotlinx.android.synthetic.main.cart_list_products.view.*

class CartAdapter  (var context: Context, var products:List<Products> = arrayListOf()):
RecyclerView.Adapter<CartAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_list_products, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindProduct(products[position])
    }



    class ViewHolder(view: View):RecyclerView.ViewHolder(view){


        lateinit var session: SessionManager

        var deleteBtn = view.findViewById<Button>(R.id.btn_delete)
        var context: Context = itemView.context

        var fragmentManager =
            (view.context as FragmentActivity).supportFragmentManager //to handle context

        fun bindProduct(product: Products){

            itemView.main_info.text = product.title




        }

    }



}