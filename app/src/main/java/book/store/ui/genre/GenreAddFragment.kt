package book.store.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Genre
import book.store.requests.NewGenreRequest
import kotlinx.android.synthetic.main.fragment_genre_crud.*
import retrofit2.Call
import retrofit2.Response

class GenreAddFragment: Fragment(){
    lateinit var session: SessionManager

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    return inflater.inflate(R.layout.fragment_genre_crud, container, false)
}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session = SessionManager(requireContext())

        name.text ="Add new genre"

        send_data.setOnClickListener{
            val title = input_title.text.toString().trim()

            if (title.isEmpty()) {
                input_title.error = "Title required"
                input_title.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.addGenre(session.TOKEN, NewGenreRequest(title))
                .enqueue(object: retrofit2.Callback<Genre>{
                    override fun onFailure(call: Call<Genre>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                       if(response.code()==200)
                           Toast.makeText(requireContext(), "Genre added!", Toast.LENGTH_SHORT).show()
                        else
                           Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }

                })

        }

    }


}
