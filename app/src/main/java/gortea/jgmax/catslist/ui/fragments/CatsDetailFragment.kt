package gortea.jgmax.catslist.ui.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsDetailFragmentBinding
import gortea.jgmax.catslist.ui.presenters.implementations.CatsDetailPresenter
import gortea.jgmax.catslist.ui.view.CatsDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.lang.NullPointerException

class CatsDetailFragment : MvpAppCompatFragment(), CatsDetailView {
    private var _binding: CatsDetailFragmentBinding? = null
    private val binding: CatsDetailFragmentBinding
        get() = requireNotNull(_binding)


    @InjectPresenter
    lateinit var presenter: CatsDetailPresenter

    @ProvidePresenter
    fun providePresenter(): CatsDetailPresenter {
        return try {
            val item = arguments?.getParcelable<CatsListItem>(ITEM_KEY)
            CatsDetailPresenter(requireNotNull(item))
        } catch (e: NullPointerException) {
            parentFragmentManager.popBackStack()
            CatsDetailPresenter(null)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            download()
        } else {
            showError(R.string.write_permission_denied_error)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatsDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = presenter.item ?: return
        binding.apply {
            Glide.with(view)
                .load(item.url)
                .placeholder(R.drawable.ic_cats_placeholder)
                .into(catsImageView)
            downloadBtn.setOnClickListener { onDownloadClick() }
            favouritesBtn.setOnClickListener { onFavouritesClick() }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onFavouritesClick() {
        presenter.addToFavourites()
    }

    private fun onDownloadClick() {
        download()
    }

    private fun download() {
        if (isWritePermissionGranted()) {
            presenter.download()
        } else {
            requestWritePermission()
        }
    }

    private fun isWritePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestWritePermission() {
        when {
            isWritePermissionGranted() -> download()
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    // View impl
    override fun <T> showError(message: T) {
        val text = when (message) {
            is String -> message
            is Int -> getString(message)
            else -> null
        } ?: return

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessDownload() {

    }

    override fun onSuccessSaveToFavourites() {

    }

    override fun download(request: DownloadManager.Request) {
        val manager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    // Instance
    companion object {
        private const val ITEM_KEY = "ITEM_KEY"
        fun getInstance(item: CatsListItem): CatsDetailFragment {
            return CatsDetailFragment().apply {
                val args = Bundle()
                args.putParcelable(ITEM_KEY, item)
                arguments = args
            }
        }
    }
}