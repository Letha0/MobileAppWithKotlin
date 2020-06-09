package book.store.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.AddBookResponse
import book.store.api.RetrofitClient
import book.store.models.*
import book.store.requests.AddBookRequest
import kotlinx.android.synthetic.main.fragment_book_crud.*
import retrofit2.Call
import retrofit2.Response


class BookAddFragment: Fragment() {
    lateinit var session: SessionManager
    private var authors = listOf<Author>()
    private var inputAuthor: Spinner? = null
    private var authorsList: List<Author> = ArrayList()
    private var inputGenre: Spinner? = null
    private var inputSeries: Spinner? = null
    private var inputCoverType: Spinner? = null
    private var inputPublHouse: Spinner? = null
    private var _idsAuthor: ArrayList<Int> = ArrayList<Int>()
    private var _idsGenre: ArrayList<Int> = ArrayList<Int>()
    private var _idsSeries: ArrayList<Int> = ArrayList<Int>()
    private var _idsCoverType: ArrayList<Int> = ArrayList<Int>()
    private var _idsPublHouse: ArrayList<Int> = ArrayList<Int>()
    var authorPosition:Int = 0
    private var genrePosition = 0
    private var seriesPosition = 0
    private var coverTypePosition = 0
    private var publHousePosition = 0




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

        name.text ="Add new book"

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

        inputGenre = getView()?.findViewById(R.id.input_genre) as Spinner
        fetchGenres()

        inputGenre?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = _idsGenre.get(position)
                session.getGenreId(id)
                genrePosition = parent?.getItemIdAtPosition(position)!!.toInt()
            }

        }

        inputSeries = getView()?.findViewById(R.id.input_series) as Spinner
        fetchSeries()

        inputSeries?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = _idsSeries.get(position)
                session.getSieriesId(id)
                seriesPosition = parent?.getItemIdAtPosition(position)!!.toInt()
            }

        }

        inputCoverType = getView()?.findViewById(R.id.input_coverType) as Spinner
        fetchCoverType()

        inputCoverType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val id = _idsCoverType.get(position)
                session.getCoverTypeId(id)
                coverTypePosition = parent?.getItemIdAtPosition(position)!!.toInt()
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

        book_send_data.setOnClickListener{
            val title = input_title.text.toString().trim()
            val description =input_description.text.toString().trim()
            val release = input_release.text.toString().trim()
            val price = input_price.text.toString().trim().toFloat()
            val coverImage = input_coverImage.text.toString().trim()
            val authorId = session.authorId
            input_author.setSelection(authorPosition)
            val genreId = session.genreId
            input_genre.setSelection(genrePosition)
            val seriesId = session.seriesId
            input_series.setSelection(seriesPosition)
            val coverType = session.coverTypeId
            input_coverType.setSelection(coverTypePosition)
            val publishingHouseId = session.publishingHouseId
            input_publHouse.setSelection(publHousePosition)     //POSITION!! XD w końcu znazałam - zmienic tez w edit



            RetrofitClient.instance.addBook(session.TOKEN, AddBookRequest(title, description, release,
             price, coverImage), authorId, genreId, seriesId, coverType, publishingHouseId)
                .enqueue(object : retrofit2.Callback<AddBookResponse>{
                    override fun onFailure(call: Call<AddBookResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<AddBookResponse>, response: Response<AddBookResponse>) {
                        if(response.code()==200){
                            Toast.makeText(requireContext(), "Book added", Toast.LENGTH_SHORT).show()
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
                            var id = responseList[i].id
                            _idsGenre.add(id)
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
                            var id = responseList[i].id
                            _idsSeries.add(id)
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
                            var id = responseList[i].id
                            _idsCoverType.add(id)
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
