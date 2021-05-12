package com.backbase.assignment.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentHomeBinding
import com.backbase.assignment.ui.home.adapter.MostPopularAdapter
import com.backbase.assignment.ui.home.adapter.PlayingNowAdapter
import com.backbase.assignment.ui.home.mapper.MoviesUiMapper
import com.backbase.assignment.ui.home.model.MostPopularItem
import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.common.getViewModel
import com.flagos.data.api.ApiHelper
import com.flagos.data.api.RetrofitBuilder
import com.flagos.data.repository.MovieDbRepository
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {

    private val apiHelper by lazy { ApiHelper(RetrofitBuilder.movieDbApi) }
    private val movieDbRepository by lazy { MovieDbRepository(apiHelper) }
    private val viewModel by lazy { getViewModel { HomeViewModel(RetrofitBuilder.movieDbApi, movieDbRepository) } }

    private lateinit var playingNowAdapter: PlayingNowAdapter
    private lateinit var mostPopularAdapter: MostPopularAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        playingNowAdapter = PlayingNowAdapter { goToMovieDetail(it) }
        mostPopularAdapter = MostPopularAdapter { goToMovieDetail(it) }

        with(binding) {
            recyclerPlayingNow.adapter = playingNowAdapter
            recyclerMostPopular.apply {
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = mostPopularAdapter
            }
        }
    }

    private fun goToMovieDetail(movieId: Int) {
        val action = HomeFragmentDirections.actionHomeDestToDetailDest(movieId)
        findNavController().navigate(action)
    }

    private fun initObservers() {
        with(viewModel) {
            onPlayingNowMoviesRetrieved.observe(viewLifecycleOwner, { setPlayingNowSection(it) })
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                movies.collectLatest { setMostPopularSection(it) }
            }
        }
    }

    private fun setPlayingNowSection(items: List<MovieImageItem>) {
        with(binding) {
            sectionPlayingNow.textSectionTitle.text = getString(R.string.text_playing_now)
            sectionPlayingNow.root.visibility = VISIBLE
        }
        playingNowAdapter.submitList(items)
    }

    private suspend fun setMostPopularSection(items: PagingData<MostPopularItem>) {
        with(binding) {
            sectionMostPopular.textSectionTitle.text = getString(R.string.text_most_popular)
            sectionMostPopular.root.visibility = VISIBLE
            mostPopularAdapter.submitData(items)
        }
    }
}
