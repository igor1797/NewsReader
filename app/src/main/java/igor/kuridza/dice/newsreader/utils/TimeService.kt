package igor.kuridza.dice.newsreader.utils

interface TimeService{
    fun getCurrentTime(): Long
    fun isStoredDataOlderThanFiveMinutes(storedTime: Long): Boolean
}