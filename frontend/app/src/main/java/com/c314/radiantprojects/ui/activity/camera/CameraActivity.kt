package com.c314.radiantprojects.ui.activity.camera


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import coil.load
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


private const val GALLERY_REQUEST_CODE = 2
private const val REQUEST_IMAGE_CAPTURE = 3
class CameraActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var binding: ActivityCameraBinding

    private var selectedImageUri: Uri? = null
    private var selectedImageBitmap: Bitmap? = null

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

        //when you click on the image
        binding.imageView.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf(
                "Select photo from Gallery",
                "Capture photo from Camera"
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

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
                    "You have denied the storage permission to select image",
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
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
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
            if (takePictureIntent == null) {
                Toast.makeText(this, "Unable to save photo", Toast.LENGTH_SHORT).show()

            } else {
                val photoFile = createImageFile()
                photoFile.also {
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "com.c314.radiantprojects.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd",Locale.getDefault()).format(Date())
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
                        "Image Saved, Please See Your Photo To Gallery Before Upload It To Server!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    //    && selectedImageBitmap == null
    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.camera.snackbar("Select an Image First")
            return
        } else if (selectedImageUri != null) {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)


            binding.progressBar.progress = 0
            val body = UploadRequestBody(file, "image", this)

            mConfig.getApiService().uploadImage(
                MultipartBody.Part.createFormData(
                    "file",
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
                                    putExtra(ResultActivity.image,selectedImageUri.toString())
                                    putExtra(ResultActivity.file,file.toString())
                                    putExtra(ResultActivity.confidence,diseaseConfidence)
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
                "It looks like you have turned off permissions"
                        + "required for this feature. It can be enable under App settings!!!"
            )

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }



}

