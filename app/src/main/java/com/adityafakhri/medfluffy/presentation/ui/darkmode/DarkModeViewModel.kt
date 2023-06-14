package com.adityafakhri.medfluffy.presentation.ui.darkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.adityafakhri.medfluffy.core.data.AppPreferences
import kotlinx.coroutines.launch

class DarkModeViewModel(private val pref: AppPreferences) : ViewModel() {

    fun getDarkMode(): LiveData<Boolean> {
        return pref.getDarkMode().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveDarkMode(isDarkModeActive)
        }
    }
}