package com.c314.radiantprojects.ui.activity.camera


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import coil.load
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.source.remote.response.UploadResponse
import com.c314.radiantprojects.core.data.source.remote.service.ApiConfig
import com.c314.radiantprojects.databinding.ActivityCameraBinding
import com.c314.radiantprojects.ui.activity.result.ResultActivity
import com.c314.radiantprojects.utils.getFileName
import com.c314.radiantprojects.utils.snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class CameraActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var binding: ActivityCameraBinding

    private var selectedImageUri: Uri? = null

    private val mConfig = ApiConfig
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.GONE

        binding.btnCamera.setOnClickListener {
            cameraCheckPermission()
        }

        binding.btnGallery.setOnClickListener {
            galleryCheckPermission()
        }

        binding.btnUpload.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            uploadImage()
        }

        binding.imageView.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle(resources.getString(R.string.select_action))
            val pictureDialogItem = arrayOf(
                resources.getString(R.string.select_gallery),
                resources.getString(R.string.capture_camera)
            )
            pictureDialog.setItems(pictureDialogItem) { _, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }
            pictureDialog.show()
        }

    }

    private fun galleryCheckPermission() {

        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@CameraActivity,
                    resources.getString(R.string.permission_denied_select_image),
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?
            ) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    private fun gallery() {
        Intent(Intent.ACTION_PICK).also {
            it.type = resources.getString(R.string.image_type)
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, GALLERY_REQUEST_CODE)
        }
    }


    private fun cameraCheckPermission() {

        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }

    private fun camera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)
            val photoFile = createImageFile()
            photoFile.also {
                FileProvider.getUriForFile(
                    this,
                    resources.getString(R.string.authority),
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    selectedImageUri = data?.data
                    binding.imageView.load(selectedImageUri) {
                        crossfade(true)
                        crossfade(1000)
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.image_saved),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.camera.snackbar(resources.getString(R.string.select_image_first))
            return
        } else if (selectedImageUri != null) {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(
                    selectedImageUri!!,
                    resources.getString(R.string.mode), null
                ) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)


            binding.progressBar.progress = 0
            val body = UploadRequestBody(file, resources.getString(R.string.image), this)

            mConfig.getApiService().uploadImage(
                MultipartBody.Part.createFormData(
                    resources.getString(R.string.file),
                    file.name,
                    body
                ),
                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())

            ).enqueue(object : Callback<UploadResponse> {
                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    binding.camera.snackbar(t.message!!)
                    binding.progressBar.progress = 0
                }

                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {
                    response.body()?.let {
                        binding.camera.snackbar(it.message)
                        binding.progressBar.progress = 100

                        val diseasePrediction = response.body()!!.result.prediction
                        val diseaseConfidence = response.body()!!.result.confidence
                        if (response.code() == 200) {
                            val intent =
                                Intent(this@CameraActivity, ResultActivity::class.java).apply {
                                    putExtra(ResultActivity.disease, diseasePrediction)
                                    putExtra(ResultActivity.file, file.toString())
                                    putExtra(ResultActivity.confidence, diseaseConfidence)
                                }
                            startActivity(intent)
                        }
                    }
                }
            })
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage(
                resources.getString(R.string.message_permisions)
            )

            .setPositiveButton(resources.getString(R.string.goto_setting)) { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts(resources.getString(R.string.scheme), packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    companion object {
        private const val GALLERY_REQUEST_CODE = 2
        private const val REQUEST_IMAGE_CAPTURE = 3
    }


}

