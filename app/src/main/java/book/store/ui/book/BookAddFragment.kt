package book.store.ui.book

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import book.store.R
import book.store.SessionManager
import book.store.api.RetrofitClient
import book.store.models.*
import book.store.requests.NewAuthorRequest
import kotlinx.android.synthetic.main.fragment_book_crud.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit


class BookAddFragment: Fragment() {
    lateinit var session: SessionManager
    private var authors = listOf<Author>()
    private var inputAuthor: Spinner? = null
    private var authorsList: List<Author> = ArrayList()
    private var inputGenre: Spinner? = null
    private var inputSeries: Spinner? = null
    private var inputCoverType: Spinner? = null
    private var inputPublHouse: Spinner? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_book_crud, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        inputAuthor = getView()?.findViewById(R.id.input_author) as Spinner
        fetchAuthors()
        inputAuthor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemIdAtPosition(position)!!.toInt()
                session.getAuthorId(type + 1)
            }

        }

        inputGenre = getView()?.findViewById(R.id.input_genre) as Spinner
        fetchGenres()

        inputGenre?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemIdAtPosition(position)!!.toInt()
                session.getGenreId(type + 1)
            }

        }

        inputSeries = getView()?.findViewById(R.id.input_series) as Spinner
        fetchSeries()

        inputSeries?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemIdAtPosition(position)!!.toInt()
                session.getSieriesId(type + 1)
            }

        }

        inputCoverType = getView()?.findViewById(R.id.input_coverType) as Spinner
        fetchCoverType()

        inputCoverType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemIdAtPosition(position)!!.toInt()
                session.getCoverTypeId(type + 1)
            }

        }

        inputPublHouse = getView()?.findViewById(R.id.input_publHouse) as Spinner
        fetchPublHouse()

        inputPublHouse?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemIdAtPosition(position)!!.toInt()
                session.getPublHouseId(type + 1)
            }

        }

        book_send_data.setOnClickListener{
            val title = input_title.text.toString().trim()
            val description =input_description.text.toString().trim()
            val release = input_release.text.toString().trim()
            val price = input_price.text.toString().trim()
            val coverImage = input_coverImage.text.toString().trim()
            val authorId = session.authorId
            input_author.setSelection(authorId)
            val genreId = session.genreId
            input_genre.setSelection(genreId)
            val seriesId = session.seriesId
            input_series.setSelection(seriesId)
            val coverType = session.coverTypeId
            input_coverType.setSelection(coverType)
            val publishingHouseId = session.publishingHouseId
            input_publHouse.setSelection(publishingHouseId)








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
                            var number = responseList[i].id
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputAuthor?.adapter = arrayAdapter


                    }


                }

            })

    }

    private fun fetchGenres(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllGenres(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Genre>>{
                override fun onFailure(call: Call<List<Genre>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Genre>>, response: Response<List<Genre>>) {
                    if(response.body()!=null){
                        //authors - response.body()
                        val responseList = response.body()
                        val item = arrayOfNulls<String>(responseList!!.size)
                        for(i in responseList.indices)
                        {
                            item[i] = responseList[i].title
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputGenre?.adapter = arrayAdapter


                    }


                }

            })

    }


    private fun fetchSeries(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllSeries(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<Serie>>{
                override fun onFailure(call: Call<List<Serie>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<Serie>>, response: Response<List<Serie>>) {
                    if(response.body()!=null){
                        val responseList = response.body()
                        val item = arrayOfNulls<String>(responseList!!.size)
                        for(i in responseList.indices)
                        {
                            item[i] = responseList[i].name
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputSeries?.adapter = arrayAdapter


                    }


                }

            })

    }

    private fun fetchCoverType(){

        session = SessionManager(requireContext())

        RetrofitClient.instance.getAllCoverTypes(session.TOKEN)
            .enqueue(object : retrofit2.Callback<List<CoverType>>{
                override fun onFailure(call: Call<List<CoverType>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<CoverType>>, response: Response<List<CoverType>>) {
                    if(response.body()!=null){
                        val responseList = response.body()
                        val item = arrayOfNulls<String>(responseList!!.size)
                        for(i in responseList.indices)
                        {
                            item[i] = responseList[i].name
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputCoverType?.adapter = arrayAdapter


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
                        }
                        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, item)
                        inputPublHouse?.adapter = arrayAdapter


                    }


                }

            })

    }
}
