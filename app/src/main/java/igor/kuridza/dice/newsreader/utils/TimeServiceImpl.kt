package igor.kuridza.dice.newsreader.utils

import java.util.*


private const val FIVE_MINUTES = 300000

class TimeServiceImpl(private val calendar: Calendar): TimeService{

    override fun getCurrentTime(): Long{
        return calendar.timeInMillis
    }

    override fun isStoredDataOlderThanFiveMinutes(storedTime: Long): Boolean{
        val currentTime = getCurrentTime()
        return (currentTime-storedTime)>FIVE_MINUTES
    }
}