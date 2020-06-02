package book.store

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import book.store.activities.LoginActivity
import book.store.activities.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit


class SessionManager {  //https://www.youtube.com/watch?v=q3EDQt7GM0A stąd te cuda, jakbyś Anka znowu szukała ;*
                        //https://stackoverflow.com/questions/41527923/handling-user-login-and-keeping-the-user-logged-in jakoś trzeba token wziąć zamiast login pass

    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var context: Context

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    val TOKEN :String
        get(){
            return "Bearer " + pref.getString("TOKEN", null) //jak było TOKEN to nie działało, ale nie przypominam sobie, bym coś zmieniała... czarna magia
        }

    val EMAIL :String?
        get(){
            return pref.getString("EMAIL", null)
        }

    val ID :Int
        get(){
            return pref.getInt("ID", 0)
        }

    //User data
    val emailUser :String?
        get(){
            return pref.getString("emailUser", null)
        }

    val name :String?
        get(){
            return pref.getString("name", null)
        }

    val surname :String?
        get(){
            return pref.getString("surname", null)
        }

    val rememberEmail :String?
        get(){
            return pref.getString("rememberEmail", null)
        }

    val rememberPassword :String?
        get(){
            return pref.getString("rememberPassword", null)
        }

    //Author Data
    val dateBirth : String?
        get(){
            return pref.getString("dateBirth", null)
        }

    val dateDeath : String?
        get(){
            return pref.getString("dateDeath", null)
        }

    val description : String?
        get(){
            return pref.getString("description", null)
        }


    val ExpiredDate: Long
    get(){
        return pref.getLong("ExpiredDate", 0)
    }


    //Book data
    val title : String?
        get(){
            return pref.getString("title", null)
        }
    val authorId : Int
        get(){
            return pref.getInt("authorId", 0)
        }
    val genreId : Int
        get(){
            return pref.getInt("genreId", 0)
        }
    val seriesId : Int
        get(){
            return pref.getInt("seriesId", 0)
        }

    val coverTypeId : Int
        get(){
            return pref.getInt("coverTypeId", 0)
        }
    val publishingHouseId : Int
        get(){
            return pref.getInt("publishingHouseId", 0)
        }

    val release : String?
        get(){
            return pref.getString("release", null)
        }

    val price : Float?
        get(){
            return pref.getFloat("price", 0F)
        }

    val coverImage : String?
        get(){
            return pref.getString("coverImage", null)
        }


    companion object {
        val PREF_NAME = "bookstore"
        val IS_LOGIN: String = "isLoggedIn"
        private var mInstance: SessionManager? = null

        @Synchronized
        fun getInstance(context: Context): SessionManager {
            if (mInstance == null) {
                mInstance = SessionManager(context)
            }
            return mInstance as SessionManager

        }
    }

    fun createLoginSession(token:String)
    {
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("TOKEN", token)
        editor.commit()
    }

    fun checkLogin() //to wołać jak trzeba będzie się logować :D
    {
        if(!this.isLoggedIn())
        {
            var i = Intent(context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
        }
    }

    fun rememberLoginData(email:String,password:String)
    {
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("rememberEmail",email)
        editor.putString("rememberPassword",password)
        editor.commit()
    }

    fun getDetailOfUser(email:String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("EMAIL",email)
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        editor.commit()
    }

    fun getDataToEditUser(name:String,surname:String,emailUser:String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString("name",name)
        editor.putString("surname",surname)
        editor.putString("emailUser",emailUser)
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60));
        editor.commit()
    }

    fun getDataToEditAuthor(id:Int,name:String, surname:String, dateOfBirth:String, dateOfDeath:String?, description:String){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("ID", id)
        editor.putString("name",name)
        editor.putString("surname",surname)
        editor.putString("dateBirth",dateOfBirth)
        editor.putString("dateDeath",dateOfDeath)
        editor.putString("description",description)
        editor.commit()
    }

    fun getDataToEditBook(id:Int, title:String, authorId:Int, genreId:Int, description: String, seriesId:Int, release:String,coverTypeId:Int,
    publishingHouseId:Int, price:Float, coverImage:String?){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("ID", id)
        editor.putString("title",title)
        editor.putInt("authorId",authorId)
        editor.putInt("genreId",genreId)
        editor.putString("description",description)
        editor.putInt("seriesId",seriesId)
        editor.putString("release",release)
        editor.putInt("coverTypeId",coverTypeId)
        editor.putInt("publishingHouseId",publishingHouseId)
        editor.putFloat("price",price)
        editor.putString("coverImage",coverImage)
        editor.commit()
    }

    fun getAuthorId(id:Int){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("authorId", id)
        editor.commit()
    }

    fun getGenreId(id:Int){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("genreId", id)
        editor.commit()
    }

    fun getSieriesId(id:Int){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("seriesId", id)
        editor.commit()
    }

    fun getCoverTypeId(id:Int){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("coverTypeId", id)
        editor.commit()
    }

    fun getPublHouseId(id:Int){
        editor.putBoolean(IS_LOGIN, true)
        editor.putInt("publishingHouseId", id)
        editor.commit()
    }



    fun Logout()
    {
        editor.clear()
        editor.commit()

        val i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }

    fun isLoggedIn() :Boolean
    {
    return pref.getBoolean(IS_LOGIN,false)
    }


}


