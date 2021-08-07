package gortea.jgmax.catslist.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import gortea.jgmax.catslist.CatsApp
import gortea.jgmax.catslist.data.local.cats.constants.*
import gortea.jgmax.catslist.data.remote.cats.model.CatsListItem
import gortea.jgmax.catslist.databinding.CatsListFragmentBinding
import gortea.jgmax.catslist.ui.list.adapters.CatsListAdapter
import gortea.jgmax.catslist.ui.list.decorators.GridItemDecoration
import gortea.jgmax.catslist.ui.presenters.implementations.CatsRemoteListPresenter
import gortea.jgmax.catslist.ui.view.CatsRemoteListView
import gortea.jgmax.catslist.utils.toPx
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class CatsListFragment : MvpAppCompatFragment(), CatsRemoteListView {
    private var _binding: CatsListFragmentBinding? = null
    private val binding: CatsListFragmentBinding
        get() = requireNotNull(_binding)

    private val adapter: CatsListAdapter = CatsListAdapter(
        loadingOffset = CATS_LOADING_OFFSET,
        onLoad = { fetchCatsList() }
    )

    @InjectPresenter
    lateinit var presenter: CatsRemoteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CatsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView(binding.catsList)
        if (savedInstanceState == null) {
            fetchCatsList()
        } else {
            adapter.submitList(presenter.getList())
        }
        binding.favouritesButton.setOnClickListener { onFavouritesClick() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun onFavouritesClick() {
        fetchCatsList()
    }

    private fun fetchCatsList() {
        val api = ((activity?.application as? CatsApp)?.catsApi) ?: return
        presenter.fetchCatsList(api)
    }

    // RecyclerView setup
    private fun setupRecyclerView(view: RecyclerView) {
        view.let {
            it.adapter = this.adapter
            it.addItemDecoration(
                GridItemDecoration(
                    spacing = ITEM_SPACING_DP.toPx(requireContext()),
                    margin = ITEM_MARGIN_DP.toPx(requireContext())
                )
            )
        }
    }

    override fun onStartRequest() {
        adapter.loadingStarted()
    }

    // View Impl

    override fun updateList(items: List<CatsListItem>?) {
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
        //todo open fragment
    }

    override fun onSuccessRequest() {
        adapter.loadingFinished()
    }

    override fun <T> onErrorRequest(message: T) {
        adapter.loadingFinished()
        val text = when(message) {
            is String -> message
            is Int -> getString(message)
            else -> null
        } ?: return

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}