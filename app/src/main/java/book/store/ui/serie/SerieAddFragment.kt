package book.store.ui.serie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Author
import book.store.models.PublishingHouse
import book.store.models.Serie
import book.store.requests.SerieRequest
import kotlinx.android.synthetic.main.fragment_serie_crud.*
import retrofit2.Call
import retrofit2.Response

class SerieAddFragment: Fragment() {

    lateinit var session: SessionManager
    private var inputAuthor: Spinner? = null
    private var inputPublHouse: Spinner? = null
    private var _idsAuthor: ArrayList<Int> = ArrayList<Int>()
    private var _idsPublHouse: ArrayList<Int> = ArrayList<Int>()
    private var authorPosition:Int = 0
    private var publHousePosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_serie_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        name.text ="Add serie"

        inputAuthor = getView()?.findViewById(R.id.input_author) as Spinner
        fetchAuthors()
        inputAuthor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = _idsAuthor.get(position)
                session.getAuthorId(id)
                authorPosition = parent?.getItemIdAtPosition(position)!!.toInt()

            }

        }

        inputPublHouse = getView()?.findViewById(R.id.input_publHouse) as Spinner
        fetchPublHouse()

        inputPublHouse?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var id = _idsPublHouse.get(position)
                session.getPublHouseId(id)
                publHousePosition = parent?.getItemIdAtPosition(position)!!.toInt()
            }

        }

        send_data.setOnClickListener{

            val name = input_name.text.toString().trim()
            val description = input_description.text.toString().trim()
            val authorId = session.authorId
            input_author.setSelection(authorPosition)
            val publishingHouseId = session.publishingHouseId
            input_publHouse.setSelection(publHousePosition)

            if(name.isEmpty()){
                input_name.error = "Name required"
                input_name.requestFocus()
                return@setOnClickListener
            }

            if(description.isEmpty()){
                input_description.error = "Name required"
                input_description.requestFocus()
                return@setOnClickListener
            }

            if(description.length < 20) {
                input_description.error = "Description have to have 20 characters minimum!"
                input_description.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addSerie(session.TOKEN, SerieRequest(name, description), authorId, publishingHouseId)
                .enqueue(object : retrofit2.Callback<Serie>{
                    override fun onFailure(call: Call<Serie>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Serie>,
                        response: Response<Serie>
                    ) {
                        if(response.code()==200){
                            Toast.makeText(requireContext(), "Serie added", Toast.LENGTH_SHORT).show()
                        }
                        else
                            Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }


    }


    private fun fetchAuthors(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllAuthors(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Author>>{
                override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                    if(response.body()!=null){
                        //authors - response.body()
                        val responseList = response.body()
                        val item = arrayOfNulls<String>(responseList!!.size)
                        for(i in responseList.indices)
                        {
                            item[i] = responseList[i].surname + " " + responseList[i].name
                            var id = responseList[i].id
                            _idsAuthor.add(id)
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputAuthor?.adapter = arrayAdapter

                    }

                }
            })

    }

    private fun fetchPublHouse(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllPublHouses(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<PublishingHouse>>{
                override fun onFailure(call: Call<List<PublishingHouse>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<PublishingHouse>>, response: Response<List<PublishingHouse>>) {
                    if(response.body()!=null){
                        val responseList = response.body()
                        val item = arrayOfNulls<String>(responseList!!.size)
                        for(i in responseList.indices)
                        {
                            item[i] = responseList[i].name
                            var id = responseList[i].id
                            _idsPublHouse.add(id)
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputPublHouse?.adapter = arrayAdapter

                    }
                }
            })

    }


}