package com.muratozturk.openai_dall_e_2.presentation.image_detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.openai_dall_e_2.R
import com.muratozturk.openai_dall_e_2.common.Constants
import com.muratozturk.openai_dall_e_2.common.glideImage
import com.muratozturk.openai_dall_e_2.databinding.FragmentImageDetailBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImageDetailFragment : BottomSheetDialogFragment(R.layout.fragment_image_detail) {

    private val binding by viewBinding(FragmentImageDetailBinding::bind)
    private val viewModel: ImageDetailViewModel by viewModels()
    private val args: ImageDetailFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.AppModalStyle)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        val window = dialog?.window

        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        context?.let {
            if (window != null) {
                window.statusBarColor = it.getColor(android.R.color.transparent)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding)
        {
            with(viewModel)
            {
                close.setOnClickListener {
                    findNavController().popBackStack()
                }
                generatedImageView.glideImage(args.url)
                download.setOnClickListener {
                    askPermissions(args.url, requireActivity())
                }

                state.observe(viewLifecycleOwner) {
                    if (it.isNullOrEmpty().not()) {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    fun askPermissions(url: String, context: Context) {
        with(viewModel)
        {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context as Activity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    AlertDialog.Builder(context)
                        .setTitle("Permission required")
                        .setMessage("Permission required to save photos from the Web.")
                        .setPositiveButton("Accept") { _, _ ->
                            ActivityCompat.requestPermissions(
                                context,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                            )

                            downloadImage(url)
                        }
                        .setNegativeButton("Deny") { dialog, _ -> dialog.cancel() }
                        .show()
                } else {
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                    )
                    downloadImage(url)
                }
            } else {
                downloadImage(url)
            }
        }

    }

}