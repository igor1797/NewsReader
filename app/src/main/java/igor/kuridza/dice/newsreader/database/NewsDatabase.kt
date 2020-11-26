package igor.kuridza.dice.newsreader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import igor.kuridza.dice.newsreader.model.SingleNews

@Database(entities = [SingleNews::class], version = 1, exportSchema = false)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun newsDao(): NewsDao
}