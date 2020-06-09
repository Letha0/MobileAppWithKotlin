package book.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import book.store.models.Opinion
import kotlinx.android.synthetic.main.comment_row_item.view.*


class GetOpinionsAdapter (var context: Context, var opinions: List<Opinion> = arrayListOf()):
    RecyclerView.Adapter<GetOpinionsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_row_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = opinions.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindOpinion(opinions[position])
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindOpinion(opinion: Opinion) {
            itemView.main_info.text = opinion.id.toString()
            itemView.add_info.text = opinion.title
            itemView.more_info.text = opinion.content

        }
        }

        }


