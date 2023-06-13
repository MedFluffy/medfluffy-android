package com.adityafakhri.medfluffy.presentation.ui.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityUploadBinding
import com.adityafakhri.medfluffy.presentation.ui.result.ResultActivity
import com.adityafakhri.medfluffy.presentation.ui.viewmodel.ViewModelFactory
import com.adityafakhri.medfluffy.utils.createCustomTempFile
import com.adityafakhri.medfluffy.utils.reduceFileImage
import com.adityafakhri.medfluffy.utils.uriToFile
import java.io.File

class UploadActivity : AppCompatActivity() {

    private var _binding: ActivityUploadBinding? = null
    private val binding get() = _binding!!

    private var viewModel: UploadViewModel? = null

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        viewModel = ViewModelProvider(this, ViewModelFactory(this))[UploadViewModel::class.java]

        binding.btnUpload.setOnClickListener {
            navigateToUResultPage()
        }
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }

        binding.btnUpload.setOnClickListener {
            val file = reduceFileImage(getFile as File)

            if (getFile != null) {
                uploadImage(file)
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.add_img_desc),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel?.apply {
            loading.observe(this@UploadActivity) {
                binding.progressBar.visibility = it
            }

            error.observe(this@UploadActivity) {
                if (it.isNotEmpty()) Toast.makeText(
                    applicationContext,
                    getString(R.string.upload_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }

            isSuccessUpload.observe(this@UploadActivity) {
                if (it) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.upload_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun uploadImage(
        image: File
    ) {
        viewModel?.uploadImage(image)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadActivity,
                "com.adityafakhri.medfluffy",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            myFile.let { file ->
                getFile = file
                binding.previewImg.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }

        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@UploadActivity)
                getFile = myFile
                binding.previewImg.setImageURI(uri)
            }
        }
    }

    private fun navigateToUResultPage() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}