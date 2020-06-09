package book.store.ui.coverType

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.CoverTypeAdapter
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.CoverType
import kotlinx.android.synthetic.main.fragment_cover_type.*
import retrofit2.Call
import retrofit2.Response

class CoverTypeFragment : Fragment() {

    private var coverTypes = listOf<CoverType>()
    lateinit var session: SessionManager
    private lateinit var coverTypeAdapter: CoverTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cover_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshLayout.setOnRefreshListener {
            fetchCoverTypes()
        }

        cover_type_recyclerview.layoutManager = LinearLayoutManager(requireContext())
        fetchCoverTypes()

        add.setOnClickListener{
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.myFragment, CoverTypeAddFragment())
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }


        private fun fetchCoverTypes(){

            session = SessionManager(requireContext())

            RetrofitClient.instance.getAllCoverTypes(session.TOKEN)
                .enqueue(object : retrofit2.Callback<List<CoverType>>{
                    override fun onFailure(call: Call<List<CoverType>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<List<CoverType>>,response: Response<List<CoverType>>) {
                        if(refreshLayout == null)
                            Toast.makeText(requireContext(), "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            else {
                        refreshLayout.isRefreshing = false
                        coverTypes = response.body()!!

                        coverTypeAdapter = CoverTypeAdapter(requireContext(), coverTypes)

                        cover_type_recyclerview.adapter = coverTypeAdapter
                        coverTypeAdapter.notifyDataSetChanged()}

                    }

                })
        }
    }

