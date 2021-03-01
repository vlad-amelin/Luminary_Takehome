package cvdevelopers.takehome.models

import android.graphics.Bitmap

class User {
    var name: String = ""
    var bitmap: Bitmap? = null

    @JvmName("getBitmap1")
    fun getBitmap(): Bitmap? {
        return bitmap
    }

    @JvmName("setBitmap1")
    fun setBitmap(bitmap: Bitmap){
        this.bitmap = bitmap
    }

    @JvmName("getName1")
    fun getName(): String {
        return name
    }

    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }
}