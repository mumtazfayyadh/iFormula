package com.mumtazfayyadh0102.iformula.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mumtazfayyadh0102.iformula.navigation.AppNavigation
import com.mumtazfayyadh0102.iformula.ui.theme.IFormulaTheme
import com.mumtazfayyadh0102.iformula.util.SettingsDataStore
import com.mumtazfayyadh0102.iformula.viewmodel.ThemeViewModel
import com.mumtazfayyadh0102.iformula.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsDataStore = SettingsDataStore(applicationContext)
        val themeViewModel: ThemeViewModel by viewModels {
            ViewModelFactory(applicationContext, settingsDataStore)
        }

        setContent {
            val selectedColor by themeViewModel.selectedColor.collectAsState()

            IFormulaTheme(selectedColor = selectedColor) {
                AppNavigation(themeViewModel = themeViewModel)
            }
        }
    }
}
