package ua.romanik.vladislav.picsumphotos.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ua.romanik.vladislav.picsumphotos.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findNavController(R.id.hostMobileFragment).navigateUp()
    }

}
