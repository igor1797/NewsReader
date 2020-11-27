package igor.kuridza.dice.newsreader.persistence

import android.content.SharedPreferences


private const val TIME_KEY = "TIME_KEY"

class TimePrefs(private val sharedPrefs: SharedPreferences){

    fun getTime(): Long{
        return sharedPrefs.getLong(TIME_KEY, 0L)
    }

    fun storeTime(timeValue: Long){
       sharedPrefs.edit().putLong(TIME_KEY, timeValue).apply()
    }
}