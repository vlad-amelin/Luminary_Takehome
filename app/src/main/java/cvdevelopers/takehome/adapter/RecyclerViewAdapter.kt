package cvdevelopers.takehome.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularimageview.CircularImageView
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.models.User
import cvdevelopers.takehome.utils.image.CircleTransformation
import cvdevelopers.takehome.utils.image.ImageCache
import java.net.URL
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class RecyclerViewAdapter @Inject
constructor() : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val data: MutableList<User>
    var context: Context? = null

    init {
        data = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userImage.setImageBitmap(data[position].getBitmap())
        holder.userName.text = String.format("%s", data[position].name)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userImage: CircularImageView = itemView.findViewById(R.id.user_image)
        val userName: TextView = itemView.findViewById(R.id.user_name)
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun setData(data: List<User>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setCacheValue(clientItems: List<Client>?, imageCache: ImageCache) {
        if ( imageCache.size() == 0 ) {
            if (clientItems != null) {
                for (client: Client in clientItems) {
                    val bitmap = getBitmapFromURL(client.picture.large)
                    if (bitmap != null) {
                        imageCache.set(String.format("%s %s", client.name.first, client.name.last), bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromURL(imageUrl: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(imageUrl)
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: Exception) {
            Log.e("ImageLoader", "Exception " + e.message)
        }
        return bitmap
    }

    fun convertMaptoArrayListObject(imageCache: ImageCache) {
        var arrayUser: MutableList<User> = ArrayList()

        var name = ArrayList(imageCache.cacheMap.keys)
        var bitmap = ArrayList(imageCache.cacheMap.values)
        for( i in 0 until name.size) {
            val user = User()
            user.setName(name[i].toString())
            user.setBitmap(bitmap[i])
            arrayUser.add(i, user)
        }

        clear()
        setData(arrayUser)
    }
}