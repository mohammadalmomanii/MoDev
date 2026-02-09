package com.mohammadalmomani.modevlib.photoPicker

import android.util.Log
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class CameraManager(
    private val cameraProvider: ProcessCameraProvider,
    private val previewView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
) {

    private lateinit var camera: Camera
    private lateinit var imageCapture: ImageCapture
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var flashEnabled = false

    private val _imageFlow = MutableStateFlow<File?>(null)

    fun startCamera(lensFacing: Int, flashEnabled: Boolean) {
        this.lensFacing = lensFacing
        this.flashEnabled = flashEnabled

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build() // لا نضع الفلاش هنا، نضبطه قبل كل تصوير

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()

        try {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (e: Exception) {
            Log.e("CameraXManager", "Camera initialization failed", e)
        }
    }

    fun captureImage() {
        val photoFile = createTempFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // تطبيق الفلاش قبل كل تصوير
        imageCapture.flashMode = if (flashEnabled)
            ImageCapture.FLASH_MODE_ON
        else
            ImageCapture.FLASH_MODE_OFF

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(previewView.context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CameraXManager", "Photo saved to ${photoFile.absolutePath}")
                    _imageFlow.value = photoFile
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraXManager", "Error capturing image: ${exception.message}")
                }
            }
        )
    }

    private fun createTempFile(): File {
        val outputDir = previewView.context.cacheDir
        return File(outputDir, "temp_image${System.currentTimeMillis()}.jpg")
    }

    fun getCapturedImage(): StateFlow<File?> = _imageFlow

    fun switchCamera() {
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK)
            CameraSelector.LENS_FACING_FRONT
        else
            CameraSelector.LENS_FACING_BACK

        startCamera(lensFacing, flashEnabled)
    }

    fun toggleFlash() {
        flashEnabled = !flashEnabled
        // لا نعيد تشغيل الكاميرا
    }

    fun isFlashEnabled(): Boolean = flashEnabled
}
