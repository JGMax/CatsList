package gortea.jgmax.cats.catslist.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gortea.jgmax.cats.app.CATS_LOADING_OFFSET
import gortea.jgmax.cats.app.hide
import gortea.jgmax.cats.app.show
import gortea.jgmax.cats.app.toPx
import gortea.jgmax.cats.catslist.data.model.CatModel
import gortea.jgmax.cats.catslist.list.CATS_LIST_SPAN_COUNT_LANDSCAPE
import gortea.jgmax.cats.catslist.list.CATS_LIST_SPAN_COUNT_PORTRAIT
import gortea.jgmax.cats.catslist.list.ITEM_MARGIN_DP
import gortea.jgmax.cats.catslist.list.ITEM_SPACING_DP
import gortea.jgmax.cats.catslist.list.adapters.CatsListAdapter
import gortea.jgmax.cats.catslist.list.adapters.delegate.ItemClickDelegate
import gortea.jgmax.cats.catslist.list.adapters.delegate.LoadingClickDelegate
import gortea.jgmax.cats.catslist.list.decorator.GridItemDecoration
import gortea.jgmax.cats.catslist.list.manager.FooterGridLayoutManagerImpl
import gortea.jgmax.cats.catslist.state.LoadingState
import gortea.jgmax.cats.core.ViewModelFactory
import gortea.jgmax.cats.databinding.CatsListFragmentBinding
import kotlinx.android.synthetic.main.cats_list_loading.*
import javax.inject.Inject

@AndroidEntryPoint
class CatsListFragment : Fragment(), ItemClickDelegate, LoadingClickDelegate {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CatsListViewModel>

    private val viewModel: CatsListViewModel by lazy {
        ViewModelProvider(
            this as ViewModelStoreOwner,
            viewModelFactory
        ).get(CatsListViewModel::class.java)
    }

    private var _binding: CatsListFragmentBinding? = null
    private val binding: CatsListFragmentBinding
        get() = requireNotNull(_binding)

    private var firstLoad = true

    private val adapter: CatsListAdapter by lazy {
        CatsListAdapter(
            loadingOffset = CATS_LOADING_OFFSET,
            onLoad = { viewModel.fetchCatsList() }
        ).also {
            it.attachItemClickDelegate(this)
            it.attachLoadingClickDelegate(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLoadingState()
        observeList()
        if (viewModel.isEmpty()) {
            viewModel.fetchCatsList()
        }
        binding.apply {
            setupRecyclerView(catsList)
            tryAgainButton.setOnClickListener { onTryAgainClick() }
        }
    }

    private fun setupRecyclerView(view: RecyclerView) {
        val layoutManager = FooterGridLayoutManagerImpl(
            GridLayoutManager(requireContext(), CATS_LIST_SPAN_COUNT_PORTRAIT)
        ).apply {
            spanCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> CATS_LIST_SPAN_COUNT_LANDSCAPE
                else -> CATS_LIST_SPAN_COUNT_PORTRAIT
            }
        }
        view.layoutManager = layoutManager.getLayoutManager()
        view.adapter = this.adapter
        view.addItemDecoration(
            GridItemDecoration(
                spacing = ITEM_SPACING_DP.toPx(requireContext()),
                margin = ITEM_MARGIN_DP.toPx(requireContext())
            )
        )
    }

    private fun observeList() {
        viewModel.getListLiveData().observe(viewLifecycleOwner) {
            adapter.submitList(it) { adapter.loadingFinished(withError = false) }
        }
    }

    private fun observeLoadingState() {
        binding.apply {
            viewModel.getStateLiveData().observe(viewLifecycleOwner) {
                when (it) {
                    is LoadingState.Loading, LoadingState.Default -> onLoadingState()
                    is LoadingState.Success -> onSuccessState()
                    is LoadingState.Failed -> onErrorState(it.error)
                }
            }
        }
    }

    private fun onErrorState(@StringRes msg: Int) {
        Log.e("state", "error")
        binding.apply {
            adapter.loadingFinished(withError = true)
            showError(msg)
            if (firstLoad) {
                firstLoadPB.hide()
                tryAgainButton.show()
            }
        }
    }

    private fun onSuccessState() {
        Log.e("state", "success")
        binding.apply {
            if (firstLoad) {
                firstLoadPB.hide()
                tryAgainButton.hide()
                firstLoad = false
            }
        }
    }
    
    private fun onLoadingState() {
        Log.e("state", "loading")
        binding.apply {
            adapter.loadingStarted()
            if (firstLoad) {
                firstLoadPB.show()
                tryAgainButton.hide()
            } else {
                firstLoadPB.hide()
                tryAgainButton.hide()
            }
        }
    }

    private fun showError(@StringRes msg: Int) {
        Toast.makeText(context, getString(msg), Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(item: CatModel) {
        viewModel.onItemClick(item)
    }

    override fun onReloadClick() {
        viewModel.fetchCatsList()
    }

    private fun onTryAgainClick() {
        viewModel.fetchCatsList()
    }

    override fun onDestroyView() {
        _binding = null
        adapter.detachLoadingHolder()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.detachDelegates()
    }
}
