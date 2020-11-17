package igor.kuridza.dice.newsreader.di

import igor.kuridza.dice.newsreader.utils.TimeService
import igor.kuridza.dice.newsreader.utils.TimeServiceImpl
import org.koin.dsl.module
import java.util.*

val timeUtilModule = module {
    single{ Calendar.getInstance() }
    single<TimeService> { TimeServiceImpl(get()) }
}