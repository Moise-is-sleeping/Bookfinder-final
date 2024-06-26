package data.Models

import android.net.Uri
import com.google.firebase.Timestamp
import java.time.LocalDateTime

data class Post(
    val date: Timestamp,
    val email:String,
    val group:String,
    val title:String,
    val description:String,
    val ratings:Int,
    val book:Doc,
    val imgUri: String,
    val id : String,
    val userName: String,
    val likes : MutableList<String>,
    val comments:MutableList<String>

)