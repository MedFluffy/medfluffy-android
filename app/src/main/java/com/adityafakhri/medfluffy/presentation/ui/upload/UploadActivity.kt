package com.adityafakhri.medfluffy.presentation.ui.upload

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.adityafakhri.medfluffy.R
import com.adityafakhri.medfluffy.databinding.ActivityUploadBinding
import com.adityafakhri.medfluffy.presentation.adapter.ResultScan
import com.adityafakhri.medfluffy.presentation.ui.viewmodel.ViewModelFactory
import com.adityafakhri.medfluffy.utils.createCustomTempFile
import com.adityafakhri.medfluffy.utils.uriToFile
import com.google.android.material.snackbar.Snackbar
import java.io.File

class UploadActivity : AppCompatActivity() {

    private var _binding: ActivityUploadBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UploadViewModel
    private lateinit var currentPhotoPath: String

    private var results: ResultScan? = null

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

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }

        binding.btnUpload.setOnClickListener {
            if (getFile != null) {
                uploadImage()
            }
        }

        viewModel.apply {
            loading.observe(this@UploadActivity) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            resultPage.observe(this@UploadActivity) { isResult ->
                binding.scrollView.visibility = if (isResult) View.GONE else View.VISIBLE
                binding.scrollViewResult.visibility = if (isResult) View.VISIBLE else View.GONE
            }

            error.observe(this@UploadActivity) {
                if (it.isNotEmpty())
                    Snackbar.make(binding.root, getString(R.string.upload_failed), Snackbar.LENGTH_SHORT).show()
            }

            result.observe(this@UploadActivity) { response ->
                results = ResultScan(
                    accuracy = response.accuration,
                    prediction = response.prediction
                )
                binding.valueAccuracy.text = results?.accuracy
                binding.textDiseaseName.text = results?.prediction
                binding.imageDisease.setImageBitmap(BitmapFactory.decodeFile(getFile?.path))
                Snackbar.make(binding.root, getString(R.string.upload_success), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImage() {
        viewModel.uploadImage()
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

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        var getFile: File? = null
    }
}