package book.store.ui.book

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
import book.store.models.*
import book.store.requests.AddBookRequest
import kotlinx.android.synthetic.main.fragment_book_crud.*
import retrofit2.Call
import retrofit2.Response

class BookEditFragment : Fragment(){
    lateinit var session: SessionManager
    private var inputAuthor: Spinner? = null
    private var authorsList: List<Author> = ArrayList()
    private var inputGenre: Spinner? = null
    private var inputSeries: Spinner? = null
    private var inputCoverType: Spinner? = null
    private var inputPublHouse: Spinner? = null
    private var _idsAuthor: ArrayList<Int> = ArrayList<Int>()
    private var _idsGenre: ArrayList<Int> = ArrayList<Int>()
    private var _idsSeries: ArrayList<Int> = ArrayList<Int>()
    private var _idCoverType: ArrayList<Int> = ArrayList<Int>()
    private var _idsPublHouse: ArrayList<Int> = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())

        name.text ="Edit book"

        inputAuthor = getView()?.findViewById(R.id.input_author) as Spinner
        fetchAuthors()
        inputAuthor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val authorId = session.authorIdToEdit
                val idA:Int = _idsAuthor.indexOf(authorId)
                input_author.setSelection(idA)
               // val authorId = session.authorIdToEdit
                val id = _idsAuthor.get(position)
              //  if(_idsAuthor.get(position)==authorId)
               //  inputAuthor!!.setSelection(authorId)
                session.getAuthorId(id)
            }

        }

        inputGenre = getView()?.findViewById(R.id.input_genre) as Spinner
        fetchGenres()

        inputGenre?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val genreId = session.genreIdToEdit
                val idG:Int = _idsGenre.indexOf(genreId)
                input_genre.setSelection(idG)
                var id = _idsGenre.get(position)
                session.getGenreId(id)
            }

        }

        inputSeries = getView()?.findViewById(R.id.input_series) as Spinner
        fetchSeries()

        inputSeries?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seriesId = session.seriesIdToEdit
                val idS:Int = _idsSeries.indexOf(seriesId)
                input_series.setSelection(idS)
                var id = _idsSeries.get(position)
                session.getSieriesId(id)
            }

        }

        inputCoverType = getView()?.findViewById(R.id.input_coverType) as Spinner
        fetchCoverType()

        inputCoverType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val coverTypeId = session.coverTypeIdToEdit
                val idC:Int =_idCoverType.indexOf(coverTypeId)
                input_coverType.setSelection(idC)
                var id = _idCoverType.get(position)
                session.getCoverTypeId(id)
            }

        }

        inputPublHouse = getView()?.findViewById(R.id.input_publHouse) as Spinner
        fetchPublHouse()

        inputPublHouse?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val publishingHouseId = session.publishingHouseIdToEdit
                val idP:Int = _idsPublHouse.indexOf(publishingHouseId)
                input_publHouse.setSelection(idP)
                var id = _idsPublHouse.get(position)
                session.getPublHouseId(id)
            }

        }

        val title = session.title
        input_title.setText(title)


        val description = session.description
        input_description.setText(description)

        val release = session.release
        input_release.setText(release)


        val price = session.price.toString()
        input_price.setText(price)
        val coverImage = session.coverImage
        input_coverImage.setText(coverImage)



        book_send_data.setOnClickListener{

            val title = input_title.text.toString().trim()
            val description =input_description.text.toString().trim()
            val release = input_release.text.toString().trim()
            val price = input_price.text.toString().trim()
            val coverImage = input_coverImage.text.toString().trim()


            RetrofitClient.instance.editBook(session.TOKEN, AddBookRequest(title, description,release,price.toFloat(),coverImage), session.ID,
            session.authorId, session.genreId, session.seriesId, session.coverTypeId, session.publishingHouseId)
                .enqueue(object : retrofit2.Callback<Book>{
                    override fun onFailure(call: Call<Book>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Book>, response: Response<Book>) {
                        if(response.code()==200){
                            Toast.makeText(requireContext(), "Book updated!", Toast.LENGTH_SHORT).show()}
                        else Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
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
                            _idCoverType.add(id)
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