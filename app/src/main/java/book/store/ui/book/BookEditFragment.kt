package book.store.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import book.store.R
import book.store.SessionManager
import kotlinx.android.synthetic.main.fragment_book_crud.*

class BookEditFragment : Fragment(){
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_crud, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = SessionManager(requireContext())

        val title = session.title
        input_title.setText(title)
        val authorId = session.authorId
        input_author.setSelection(authorId)
        val genreId = session.genreId
        input_genre.setSelection(genreId)
        val description = session.description
        input_description.setText(description)
        val seriesId = session.seriesId
        input_series.setSelection(seriesId)
        val release = session.release
        input_release.setText(release)
        val coverTypeId = session.coverTypeId
        input_coverType.setSelection(coverTypeId)
        val publishingHouseId = session.publishingHouseId
        input_publHouse.setSelection(publishingHouseId)
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



        }




    }

}