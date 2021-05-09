package com.backbase.assignment.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.backbase.assignment.R
import com.backbase.assignment.databinding.FragmentHomeBinding
import com.backbase.assignment.ui.home.adapter.PlayingNowAdapter
import com.flagos.common.getViewModel
import com.flagos.data.api.ApiHelper
import com.flagos.data.api.RetrofitBuilder
import com.flagos.data.repository.MovieDbRepository

class HomeFragment : Fragment() {

    private val apiHelper by lazy { ApiHelper(RetrofitBuilder.movieDbApi) }
    private val movieDbRepository by lazy { MovieDbRepository(apiHelper) }
    private val viewModel by lazy { getViewModel { HomeViewModel(movieDbRepository) } }

    private lateinit var playingNowAdapter: PlayingNowAdapter

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

        with(binding) {
            recyclerPlayingNow.adapter = playingNowAdapter
            sectionPlayingNow.textSectionTitle.text = getString(R.string.text_playing_now)
        }
    }

    private fun goToMovieDetail(movieId: Int) {
        val action = HomeFragmentDirections.actionHomeDestToDetailDest(movieId)
        findNavController().navigate(action)
    }

    private fun initObservers() {
        with(viewModel) {
            //TODO: Check if we can set with init func.
            fetchPlayingNow()
            onPlayingNowMoviesRetrieved.observe(viewLifecycleOwner, { playingNowAdapter.submitList(it) })
        }
    }
}
