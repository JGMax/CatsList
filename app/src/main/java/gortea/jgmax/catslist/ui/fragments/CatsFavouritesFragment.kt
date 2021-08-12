package gortea.jgmax.catslist.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import gortea.jgmax.catslist.CatsApp
import gortea.jgmax.catslist.data.local.cats.constants.CATS_LIST_SPAN_COUNT_LANDSCAPE
import gortea.jgmax.catslist.data.local.cats.constants.CATS_LIST_SPAN_COUNT_PORTRAIT
import gortea.jgmax.catslist.data.local.cats.constants.ITEM_MARGIN_DP
import gortea.jgmax.catslist.data.local.cats.constants.ITEM_SPACING_DP
import gortea.jgmax.catslist.data.local.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.FavouritesListFragmentBinding
import gortea.jgmax.catslist.ui.delegates.ItemClickDelegate
import gortea.jgmax.catslist.ui.delegates.OpenFragmentDelegate
import gortea.jgmax.catslist.ui.list.adapters.FavouritesListAdapter
import gortea.jgmax.catslist.ui.list.decorators.GridItemDecoration
import gortea.jgmax.catslist.ui.presenters.implementations.CatsFavouritesListPresenter
import gortea.jgmax.catslist.ui.view.CatsFavouritesListView
import gortea.jgmax.catslist.utils.toPx
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CatsFavouritesFragment : MvpAppCompatFragment(), CatsFavouritesListView, ItemClickDelegate {
    private var _binding: FavouritesListFragmentBinding? = null
    private val binding: FavouritesListFragmentBinding
        get() = requireNotNull(_binding)
    private val adapter = FavouritesListAdapter().also { it.attachClickDelegate(this) }

    @InjectPresenter
    lateinit var presenter: CatsFavouritesListPresenter

    private var openFragmentDelegate: OpenFragmentDelegate? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        openFragmentDelegate = context as? OpenFragmentDelegate
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavouritesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (presenter.getList().isEmpty()) {
            fetchCatsList()
        } else {
            adapter.submitList(presenter.getList())
        }
        setupRecyclerView()
    }

    private fun fetchCatsList() {
        val dao = ((activity?.application as? CatsApp)?.catsDao) ?: return
        presenter.fetchCatsList(dao)
    }

    private fun setupRecyclerView() {
        val spanCount = when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> CATS_LIST_SPAN_COUNT_LANDSCAPE
            else -> CATS_LIST_SPAN_COUNT_PORTRAIT
        }
        binding.catsList.let {
            it.layoutManager = GridLayoutManager(context, spanCount)
            it.adapter = this.adapter
            it.addItemDecoration(
                GridItemDecoration(
                    spacing = ITEM_SPACING_DP.toPx(requireContext()),
                    margin = ITEM_MARGIN_DP.toPx(requireContext())
                )
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // View impl
    override fun onStartRequest() {
        //TODO("Not yet implemented")
    }

    override fun updateList(items: List<CatsListItem?>?) {
        if (items != null) {
            adapter.submitList(items)
        }
    }

    override fun openActivity(intent: Intent?) {
        val pm = activity?.packageManager ?: return
        intent?.resolveActivity(pm)?.let {
            startActivity(intent)
        }
    }

    override fun openFragment(fragment: MvpAppCompatFragment) {
        openFragmentDelegate?.openFragment(fragment)
    }

    override fun onSuccessRequest() {
        //TODO("Not yet implemented")
    }

    override fun <T> onErrorRequest(message: T) {
        val text = when (message) {
            is String -> message
            is Int -> getString(message)
            else -> return
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    // Delegate impl
    override fun onItemSelected(item: CatsListItem) {
        presenter.onCatsItemSelected(item)
    }
}