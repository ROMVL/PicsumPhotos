package ua.romanik.vladislav.picsumphotos.presentation.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import ua.romanik.vladislav.picsumphotos.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE = 18086
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!checkWriteExternalStoragePermission()) requestPermission()
    }

    override fun onNavigateUp() = findNavController(R.id.hostMobileFragment).navigateUp()

    override fun onBackPressed() {
        if (!findNavController(R.id.hostMobileFragment).popBackStack())
            super.onBackPressed()
    }

    private fun checkWriteExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    requestPermission()
                }
                return
            }
        }
    }


}
