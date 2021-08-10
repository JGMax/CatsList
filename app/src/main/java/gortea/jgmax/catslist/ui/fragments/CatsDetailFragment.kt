package gortea.jgmax.catslist.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import gortea.jgmax.catslist.R
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsDetailFragmentBinding
import gortea.jgmax.catslist.ui.presenters.implementations.CatsDetailPresenter
import gortea.jgmax.catslist.ui.view.CatsDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CatsDetailFragment private constructor() : MvpAppCompatFragment(), CatsDetailView {
    private var _binding: CatsDetailFragmentBinding? = null
    private val binding: CatsDetailFragmentBinding
        get() = requireNotNull(_binding)

    @InjectPresenter
    lateinit var presenter: CatsDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatsDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = arguments?.getParcelable<CatsListItem>(ITEM_KEY) ?: return
        binding.apply {
            Glide.with(view)
                .load(item.url)
                .placeholder(R.drawable.ic_cats_placeholder)
                .into(catsImageView)
            downloadBtn.setOnClickListener { onDownloadClick(item) }
            favouritesBtn.setOnClickListener { onFavouritesClick(item) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onFavouritesClick(item: CatsListItem) {
        presenter.addToFavourites(item)
    }

    private fun onDownloadClick(item: CatsListItem) {
        if (isWritePermissionGranted()) {
            presenter.download(item.url)
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
        ActivityCompat.requestPermissions(
            activity as Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE
        )
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
        private const val REQUEST_CODE = 100
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