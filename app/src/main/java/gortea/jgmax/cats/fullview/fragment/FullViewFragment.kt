package gortea.jgmax.cats.fullview.fragment

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import gortea.jgmax.cats.R
import gortea.jgmax.cats.app.state.UrlCheckState
import gortea.jgmax.cats.app.state.LoadingState
import gortea.jgmax.cats.core.ViewModelFactory
import gortea.jgmax.cats.databinding.FullViewFragmentBinding
import javax.inject.Inject

@AndroidEntryPoint
class FullViewFragment : Fragment() {
    private var _binding: FullViewFragmentBinding? = null
    private val binding: FullViewFragmentBinding
        get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<FullViewViewModel>

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val service =
                    activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                viewModel.onReceiveBroadcast(service, it)
            }
        }
    }

    private val viewModel: FullViewViewModel by lazy {
        ViewModelProvider(
            this as ViewModelStoreOwner,
            viewModelFactory
        ).get(FullViewViewModel::class.java)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showMessage(R.string.write_permission_denied_error)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FullViewFragmentBinding.inflate(inflater, container, false)
        viewModel.initArgs(arguments)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCheckState(view)
        observeLoadingState()
        binding.downloadBtn.setOnClickListener { onDownloadClick() }
    }


    private fun observeLoadingState() {
        viewModel.getLoadingState().observe(viewLifecycleOwner) {
            when(it) {
                is LoadingState.Default -> {
                    binding.downloadBtn.isEnabled = true
                }
                is LoadingState.Success -> {
                    binding.downloadBtn.isEnabled = true
                    showMessage(R.string.download_success)
                }
                is LoadingState.Failed -> {
                    binding.downloadBtn.isEnabled = true
                    showMessage(it.error)
                }
                is LoadingState.Loading -> {
                    binding.downloadBtn.isEnabled = false
                }
            }
        }
    }

    private fun observeCheckState(root: View) {
        viewModel.getCheckState().observe(viewLifecycleOwner) {
            when(it) {
                is UrlCheckState.Default -> {
                    binding.downloadBtn.isEnabled = false
                }
                is UrlCheckState.Success -> {
                    binding.downloadBtn.isEnabled = true
                        Glide
                            .with(root)
                            .load(it.data)
                            .into(binding.catsImageView)
                }
                is UrlCheckState.Fail -> {
                    binding.downloadBtn.isEnabled = true
                    showMessage(it.msg)
                }
            }
        }
    }

    private fun onDownloadClick() {
        if (isWritePermissionGranted()) {
            val service = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            viewModel.download(service, getString(R.string.download_description))
        } else {
            requestWritePermission()
        }
    }

    private fun requestWritePermission() {
        when {
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun isWritePermissionGranted(): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    private fun showMessage(@StringRes msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(receiver)
    }
}