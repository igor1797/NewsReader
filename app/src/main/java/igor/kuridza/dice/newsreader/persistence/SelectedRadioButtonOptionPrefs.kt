package igor.kuridza.dice.newsreader.persistence

import android.content.SharedPreferences

private const val RADIO_BUTTON_OPTION_KEY = "RadioButtonOption"

class SelectedRadioButtonOptionPrefs(private val sharedPref: SharedPreferences){

    fun getSelectedRadioButtonOption(): Int{
        return sharedPref.getInt(RADIO_BUTTON_OPTION_KEY, -1)
    }

    fun storeSelectedRadioButtonOption(radioButtonOption: Int){
        sharedPref.edit().putInt(RADIO_BUTTON_OPTION_KEY, radioButtonOption).apply()
    }
}