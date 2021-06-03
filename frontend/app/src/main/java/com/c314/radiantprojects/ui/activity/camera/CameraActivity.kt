package com.c314.radiantprojects.ui.activity.camera


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
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
import java.util.*


class CameraActivity : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private lateinit var binding: ActivityCameraBinding

    private var selectedImageUri: Uri? = null
    private var selectedImageBitmap: Bitmap? = null
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    private val mConfig = ApiConfig

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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {
//                    val bitmap = data?.extras?.get("data") as Bitmap
                    selectedImageBitmap = data?.extras?.get("data") as Bitmap
                    binding.imageView.setImageBitmap(selectedImageBitmap)


//                    //we are using coroutine image loader (coil)
//                    binding.imageView.load(bitmap) {
//                        crossfade(true)
//                        crossfade(1000)
//                        transformations(CircleCropTransformation())
//                    }
                }

                GALLERY_REQUEST_CODE -> {

                    selectedImageUri = data?.data

                    binding.imageView.load(selectedImageUri) {
                        crossfade(true)
                        crossfade(1000)
//                        transformations(CircleCropTransformation())
                    }

//                    binding.imageView.setImageURI(selectedImageUri)

                }
            }

        }

    }


    private fun uploadImage() {
        if (selectedImageUri == null && selectedImageBitmap == null) {
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
                        Log.d("Gallery", response.body().toString())
                        Log.d("Gallery", response.code().toString())
                        Log.d("Gallery", response.message())
                        binding.progressBar.progress = 100

//                        val responseText = "Response code : ${response.code()}\n"+
//                                "confidence : ${response.body()!!.result.confidence}\n"+
//                                "prediction : ${response.body()!!.result.prediction}"
                        val diseasePrediction = response.body()!!.result.prediction
                        if (response.code() == 200) {
                            val intent =
                                Intent(this@CameraActivity, ResultActivity::class.java).apply {
                                    putExtra(ResultActivity.disease, diseasePrediction)
                                }
                            startActivity(intent)
                        }

//                        binding.tvTest.text = responseText

                    }
                }
            })

        } else if(selectedImageBitmap != null) {

            val uri = bitmapToFile(selectedImageBitmap!!)
//            val mFile = uri.toFile()

            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(uri, "r", null) ?: return

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(uri))
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
                        Log.d("Camera", response.body().toString())
                        Log.d("Camera", response.code().toString())
                        Log.d("Camera", response.message())
                        binding.progressBar.progress = 100
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

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap: Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }



    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

