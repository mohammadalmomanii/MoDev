package com.mohammadalmomani.modevlib.photoPicker

import android.Manifest
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntRange
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohammadalmomani.modevlib.R
import com.mohammadalmomani.modevlib.databinding.FragmentCameraBinding
import com.mohammadalmomani.modevlib.support.AppHelper
import kotlinx.coroutines.launch


/**
 * CameraFragment is a custom BottomSheetDialogFragment for capturing images with:
 * - Optional camera switch (front/back)
 * - Optional flash control
 * - Maximum image selection limit
 * - Vibration feedback on capture
 *
 * ## Features:
 * - Uses CameraX for camera preview and image capture.
 * - Allows dynamic control of UI elements (flash, switch camera, capture button).
 * - Integrates a RecyclerView to display captured images.
 * - Supports a listener to return selected images to the parent Activity/Fragment.
 *
 * ## How to Use:
 *
 * ### 1. Show CameraFragment using the Builder:
 * ```kotlin
 * CameraFragment.builder()
 *     .allowSwitchCamera(true)    // Enable front/back camera switch
 *     .showFlash(true)            // Show flash toggle button
 *     .enableCapture(true)        // Enable capture button
 *     .maxImage(5)                // Maximum selectable images (1..8)
 *     .listener(object : CameraFragment.CameraResultListener {
 *         override fun onImagesSelected(images: List<TakenImage>) {
 *             if (images.isEmpty()) {
 *                 Toast.makeText(
 *                     this@MainActivity,
 *                     "لم تختار أي صورة",
 *                     Toast.LENGTH_SHORT
 *                 ).show()
 *             } else {
 *                 // Handle selected images
 *                 images.forEach { takenImage ->
 *                     Log.d("Camera", "Image path: ${takenImage.file.absolutePath}")
 *                 }
 *             }
 *         }
 *     })
 *     .build(
 *         supportFragmentManager,
 *         "CameraFragment"
 *     )
 * ```
 *
 * ### 2. Notes:
 * - Maximum image selection is enforced only when selecting from captured images.
 * - Capturing more than `maxImages` is allowed, but only `maxImages` can be selected.
 * - Flash and camera switch visibility are optional.
 * - Ensure permissions for CAMERA are granted.
 */


class CameraPickerFragment : BottomSheetDialogFragment() {

    // ========= CONFIG FROM BUILDER =========
    private var allowSwitchCamera = true
    private var showFlashButton = true
    private var enableCapture = true
    private var maxImages = 1
    private var resultListener: CameraResultListener? = null
    // ======================================

    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraManager: CameraManager
    private lateinit var takenImageAdapter: TakenImageAdapter
    private lateinit var vibrator: Vibrator

    // ========= RESULT INTERFACE =========
    interface CameraResultListener {
        fun onImagesSelected(images: List<TakenImage>)
    }

    // ========= BUILDER =========
    class Builder {
        private val fragment = CameraPickerFragment()

        fun allowSwitchCamera(enable: Boolean) = apply {
            fragment.allowSwitchCamera = enable
        }

        fun showFlash(enable: Boolean) = apply {
            fragment.showFlashButton = enable
        }

        fun enableCapture(enable: Boolean) = apply {
            fragment.enableCapture = enable
        }

        fun maxImage(@IntRange(from = 1, to = 8) max: Int) = apply {
            require(max in 1..8) { "maxImage must be between 1 and 8" }
            fragment.maxImages = max
        }

        fun listener(listener: CameraResultListener) = apply {
            fragment.resultListener = listener
        }

        fun build(
            fragmentManager: androidx.fragment.app.FragmentManager,
            tag: String,
        ) {
            fragment.show(fragmentManager, tag)
        }
    }

    companion object {
        fun builder() = Builder()
    }
    // ================================

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) initCamera()
            else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
                dismiss()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.modev_styleSheetBottom)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        AppHelper.expandedBottomSheetDialog(this)
        AppHelper.disableBottomSheetDrag(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vibrator = setVibrator()

        applyConfigUI()
        setupRecycler()
        setupClicks()
        requestCameraPermission()
    }

    // ========= UI CONFIG =========
    private fun applyConfigUI() {
        binding.switchCameraBtn.visibility =
            if (allowSwitchCamera) View.VISIBLE else View.GONE

        binding.toggleFlashBtn.visibility =
            if (showFlashButton) View.VISIBLE else View.GONE

        binding.takeImageBtn.isEnabled = enableCapture
    }

    // ========= CAMERA =========
    private fun requestCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun initCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraManager = CameraManager(
                cameraProvider,
                binding.viewFinder,
                viewLifecycleOwner
            )
            cameraManager.startCamera(
                lensFacing = CameraSelector.LENS_FACING_BACK,
                flashEnabled = false
            )
            observeCapturedImage()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun observeCapturedImage() {
        lifecycleScope.launch {
            cameraManager.getCapturedImage().collect { file ->
                file?.let {
                    startVibration()
                    takenImageAdapter.addItem(
                        TakenImage(file = it, isSelected = false)
                    )
                    if (takenImageAdapter.currentList.size > 10) {
                        binding.takeImageBtn.isEnabled = false
                    }
                    binding.confirmBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    // ========= VIBRATION =========
    private fun setVibrator(): Vibrator =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager =
                requireActivity().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireActivity().getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

    private fun startVibration() {
        val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    }

    // ========= RECYCLER =========
    private fun setupRecycler() {
        takenImageAdapter = TakenImageAdapter(
            object : TakenImageAdapter.OnTakenImageClickListener {
                override fun onTakenImageClicked(takenImage: TakenImage) {
                    takenImageAdapter.toggleSelection(takenImage)
                }

                override fun onSelectionLimitReached() {
                    Toast.makeText(requireContext(), getString(R.string.max_images_reached), Toast.LENGTH_SHORT).show()
                }
            },
            maxSelection = maxImages
        )
        binding.takenImageRv.adapter = takenImageAdapter
    }

    // ========= CLICKS =========
    private fun setupClicks() {
        binding.takeImageBtn.setOnClickListener {
            if (enableCapture) cameraManager.captureImage()
        }

        binding.switchCameraBtn.setOnClickListener {
            if (allowSwitchCamera) cameraManager.switchCamera()
        }

        binding.toggleFlashBtn.setOnClickListener {
            cameraManager.toggleFlash()
            binding.toggleFlashBtn.setImageResource(
                if (cameraManager.isFlashEnabled())
                    R.drawable.ic_camera_flash_on
                else
                    R.drawable.ic_camera_flash_off
            )
        }

        binding.confirmBtn.setOnClickListener {
            val selectedImages = takenImageAdapter.currentList.filter { it.isSelected }

            if (selectedImages.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_images_selected), // اضيف هالستنج للـ strings.xml
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            resultListener?.onImagesSelected(selectedImages)
            dismiss()
        }

        binding.closeBtn.setOnClickListener { dismiss() }
    }
}
