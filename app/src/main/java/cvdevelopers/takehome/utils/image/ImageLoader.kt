package cvdevelopers.takehome.utils.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageLoader @Inject constructor(context: Context) {

    private val picasso = Picasso.Builder(context).build()

    fun loadCircularImage(url: String, imageView: ImageView) {
        val rc = picasso.load(url)
        rc.transform(CircleTransformation())
        rc.into(imageView)
    }
}