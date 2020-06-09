package book.store.ui.comments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import book.store.GetOpinionsAdapter
import book.store.R
import book.store.api.SessionManager
import book.store.api.RetrofitClient
import book.store.models.Opinion
import book.store.requests.OpinionRequest
import kotlinx.android.synthetic.main.comment_dialog.view.*
import kotlinx.android.synthetic.main.fragment_comments.*
import retrofit2.Call
import retrofit2.Response

class CommentsFragment: Fragment() {

    private var comments = listOf<Opinion>()
    lateinit var session: SessionManager
    private lateinit var commentAdapter: GetOpinionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            refreshLayout.setOnRefreshListener {
                fetchComments()
            }
            comment_recyclerview.layoutManager = LinearLayoutManager(requireContext())

            fetchComments()

        add_comment.setOnClickListener{

            val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.comment_dialog, null)
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)
                .setTitle("Comment Form")
            val  mAlertDialog = mBuilder.show()

            mDialogView.dialogSend.setOnClickListener {
                //dismiss dialog
                mAlertDialog.dismiss()
                val title = mDialogView.dialogTitle.text.toString()
                val description = mDialogView.dialogComment.text.toString()

                RetrofitClient.instance.addOpinion(session.ID, OpinionRequest(title,description))
                    .enqueue(object : retrofit2.Callback<Opinion>{
                        override fun onFailure(call: Call<Opinion>, t: Throwable) {
                            Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Opinion>, response: Response<Opinion>) {
                            if(response.code()==200)
                            Toast.makeText(requireContext(), "Comment added", Toast.LENGTH_SHORT).show()
                            else if(response.code()==403)
                                Toast.makeText(requireContext(), "Login to add comment", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

}


private fun fetchComments(){

    session = SessionManager(requireContext())

    RetrofitClient.instance.getOpinionsAboutBook(session.ID)
        .enqueue(object : retrofit2.Callback<List<Opinion>>{
            override fun onFailure(call: Call<List<Opinion>>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Opinion>>,response: Response<List<Opinion>>) {
                if(response.body()==null) Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                else{
                refreshLayout.isRefreshing = false
                comments = response.body()!!

                commentAdapter = GetOpinionsAdapter(requireContext(), comments)

                comment_recyclerview.adapter = commentAdapter
                commentAdapter.notifyDataSetChanged()}
            }

        })

}


}
