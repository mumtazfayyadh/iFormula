package com.mumtazfayyadh0102.iformula.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mumtazfayyadh0102.iformula.navigation.AppNavigation
import com.mumtazfayyadh0102.iformula.ui.theme.IFormulaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IFormulaTheme {
                AppNavigation()
            }
        }
    }
}