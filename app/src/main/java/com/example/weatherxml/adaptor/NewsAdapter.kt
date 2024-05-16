import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.weatherxml.model.DataNews
import com.example.weatherxml.R

class NewsAdapter(private var newsList: List<DataNews>, private val onItemClick: (DataNews) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_view_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.text_view_description)
        private val newsImage: ImageView = itemView.findViewById(R.id.news_image)

        fun bind(newsItem: DataNews) {
            titleTextView.text = newsItem.title
            descriptionTextView.text = newsItem.description
            Glide.with(itemView.context)
                .load(newsItem.imageUrl)
                .apply(RequestOptions().transform(RoundedCorners(30)))
                .error(R.drawable.ic_error)
                .into(newsImage)

            itemView.setOnClickListener {
                onItemClick(newsItem)
            }
        }
    }

    fun updateData(newData: List<DataNews>) {
        newsList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
