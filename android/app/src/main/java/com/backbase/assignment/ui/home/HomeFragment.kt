package com.backbase.assignment.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
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
import com.flagos.framework.home.model.MostPopularItem
import com.flagos.framework.home.model.NowPlayingItem
import com.flagos.common.getViewModel
import com.flagos.data.api.ApiHelper
import com.flagos.data.api.RetrofitBuilder
import com.flagos.data.repository.MovieDbRepository
import com.flagos.framework.home.usecase.MovieDbUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val apiHelper by lazy { ApiHelper(RetrofitBuilder.movieDbApi) }
    private val movieDbRepository by lazy { MovieDbRepository(apiHelper) }
    private val movieDbUseCase by lazy { MovieDbUseCase(movieDbRepository) }
    private val viewModel by lazy { getViewModel { HomeViewModel(movieDbUseCase, movieDbRepository) } }

    private lateinit var playingNowAdapter: PlayingNowAdapter
    private lateinit var mostPopularAdapter: MostPopularAdapter

    private var getMostPopularMoviesJob: Job? = null

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
            runGetMostPopularMovies { fetchMostPopular().collectLatest { setMostPopularSection(it) } }
        }
    }

    private fun setPlayingNowSection(items: List<NowPlayingItem>) {
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

    private fun runGetMostPopularMovies(getMostPopularMovies: suspend () -> Unit) {
        getMostPopularMoviesJob?.cancel()
        getMostPopularMoviesJob = lifecycleScope.launch { getMostPopularMovies() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMostPopularMoviesJob?.cancel()
    }
}
