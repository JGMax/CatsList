package gortea.jgmax.catslist.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.catslist.CatsApp
import gortea.jgmax.catslist.data.local.cats.constants.*
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListFragmentBinding
import gortea.jgmax.catslist.ui.delegates.ItemClickDelegate
import gortea.jgmax.catslist.ui.delegates.OpenFragmentDelegate
import gortea.jgmax.catslist.ui.list.adapters.CatsListAdapter
import gortea.jgmax.catslist.ui.list.decorators.GridItemDecoration
import gortea.jgmax.catslist.ui.list.layoutManagers.FooterGridLayoutManager
import gortea.jgmax.catslist.ui.list.layoutManagers.FooterGridLayoutManagerImpl
import gortea.jgmax.catslist.ui.presenters.implementations.CatsRemoteListPresenter
import gortea.jgmax.catslist.ui.view.CatsListView
import gortea.jgmax.catslist.utils.hide
import gortea.jgmax.catslist.utils.show
import gortea.jgmax.catslist.utils.toPx
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CatsRemoteListFragment : MvpAppCompatFragment(), CatsListView, ItemClickDelegate {
    private var _binding: CatsListFragmentBinding? = null
    private val binding: CatsListFragmentBinding
        get() = requireNotNull(_binding)

    private val adapter: CatsListAdapter = CatsListAdapter(
        loadingOffset = CATS_LOADING_OFFSET,
        onLoad = { fetchCatsList() }
    ).also { it.attachClickDelegate(this) }

    private lateinit var layoutManager: FooterGridLayoutManager

    private var openFragmentDelegate: OpenFragmentDelegate? = null

    @InjectPresenter
    lateinit var presenter: CatsRemoteListPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        openFragmentDelegate = context as? OpenFragmentDelegate
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
        binding.apply {
            setupRecyclerView(catsList)
            tryAgainButton.hide()
            if (presenter.getList().isEmpty()) {
                firstLoadingProgressBar.show()
                fetchCatsList()
            } else {
                firstLoadingProgressBar.hide()
                adapter.submitList(presenter.getList())
            }
            favouritesButton.setOnClickListener { onFavouritesClick() }
            tryAgainButton.setOnClickListener { onTryAgainClick() }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onFavouritesClick() {
        openFragment(CatsFavouritesFragment())
    }

    private fun onTryAgainClick() {
        if (presenter.getList().isNotEmpty()) return
        binding.apply {
            tryAgainButton.hide()
            firstLoadingProgressBar.show()
        }
        fetchCatsList()
    }

    private fun fetchCatsList() {
        val api = ((activity?.application as? CatsApp)?.catsApi) ?: return
        presenter.fetchCatsList(api)
    }

    // RecyclerView setup
    private fun setupRecyclerView(view: RecyclerView) {
        layoutManager = FooterGridLayoutManagerImpl(
            GridLayoutManager(
                requireActivity(),
                CATS_LIST_SPAN_COUNT_PORTRAIT
            )
        )
        with(layoutManager) {
            spanCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> CATS_LIST_SPAN_COUNT_PORTRAIT
                Configuration.ORIENTATION_LANDSCAPE -> CATS_LIST_SPAN_COUNT_LANDSCAPE
                else -> CATS_LIST_SPAN_COUNT_PORTRAIT
            }
            showFooter()
        }
        view.let {
            it.layoutManager = layoutManager.getLayoutManager()
            it.adapter = this.adapter
            it.addItemDecoration(
                GridItemDecoration(
                    spacing = ITEM_SPACING_DP.toPx(requireContext()),
                    margin = ITEM_MARGIN_DP.toPx(requireContext())
                )
            )
        }
    }

    // View Impl
    override fun onStartRequest() {
        adapter.loadingStarted()
    }

    override fun updateList(items: List<CatsListItem?>?) {
        if (items != null) {
            adapter.submitList(items)
        }
    }

    override fun openFragment(fragment: MvpAppCompatFragment) {
        openFragmentDelegate?.openFragment(fragment)
    }

    override fun onSuccessRequest() {
        binding.apply {
            firstLoadingProgressBar.hide()
            tryAgainButton.hide()
        }
        adapter.loadingFinished(withError = false)
    }

    override fun <T> onErrorRequest(message: T) {
        if (presenter.getList().isEmpty()) {
            binding.apply {
                firstLoadingProgressBar.hide()
                binding.tryAgainButton.show()
            }
        }
        adapter.loadingFinished(withError = true)
        val text = when (message) {
            is String -> message
            is Int -> getString(message)
            else -> return
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    // Item Click Delegate impl
    override fun onItemSelected(item: CatsListItem) {
        presenter.onCatsItemSelected(item)
    }
}