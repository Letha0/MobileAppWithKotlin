package book.store.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.CartAdapter
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.api.SuccessResponse
import book.store.models.Cart
import book.store.models.Products
import book.store.ui.home.HomeFragment
import book.store.ui.order.AddOrderFragment
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.refreshLayout
import retrofit2.Call
import retrofit2.Response

class CartFragment : Fragment() {

    lateinit var session: SessionManager
    private  var product = listOf<Products>()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchCart()
        }

        cart_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        fetchCart()



        clear_cart.setOnClickListener {
            session = SessionManager(requireContext())

            RetrofitClient.instance.deleteCart(session.cookie, session.TOKEN)
                .enqueue(object : retrofit2.Callback<SuccessResponse> {
                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<SuccessResponse>,
                        response: Response<SuccessResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(requireContext(), "Cart cleared", Toast.LENGTH_SHORT)
                                .show()
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.nav_host_fragment, HomeFragment())
                            fragmentTransaction?.commit()
                        } else
                            Toast.makeText(
                                requireContext(),
                                response.code().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                    }

                })
        }



        order.setOnClickListener {

                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.nav_host_fragment, AddOrderFragment())
                    fragmentTransaction?.commit()
        }
    }



    private fun fetchCart(){

        session = SessionManager(requireContext())

        var cookie = session.cookie
        if(cookie==null)  cookie = "abc"

        RetrofitClient.instance.getCart( cookie, session.TOKEN)
            .enqueue(object : retrofit2.Callback<Cart<List<Products>>>{
                override fun onFailure(call: Call<Cart<List<Products>>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<Cart<List<Products>>>,
                    response: Response<Cart<List<Products>>>
                ) {

                    response.headers()["Set-Cookie: "+ session.cookie]

                    if(response.body()?.status!=null)
                        return Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                    else if (response.body()==null){Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()}
                    else{
                        refreshLayout.isRefreshing = false
                        product = response.body()!!.products

                        cartAdapter = CartAdapter((requireContext()), product)


                        price.text = response.body()!!.totalPrice.toString() + "$"
                        cart_recyclerview.adapter = cartAdapter
                        cartAdapter.notifyDataSetChanged()

                    }
                }

            })
    }

}
