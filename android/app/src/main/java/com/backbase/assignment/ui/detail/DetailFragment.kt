package com.backbase.assignment.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.backbase.assignment.databinding.FragmentDetailBinding
import com.backbase.assignment.ui.MainActivity
import com.backbase.assignment.ui.detail.adapter.GenresAdapter
import com.backbase.assignment.ui.extensions.loadImageFromUrl
import com.backbase.assignment.ui.home.HomeViewModel.Companion.POSTER_PATH
import com.flagos.common.getViewModel
import com.flagos.data.api.MovieDbClient
import com.flagos.data.api.MovieServiceImpl
import com.flagos.data.api.retrofit.RetrofitBuilder
import com.flagos.data.repository.MovieDbRepositoryImpl
import com.flagos.data.utils.NORMAL_DATE
import com.flagos.data.utils.UI_DATE
import com.flagos.data.utils.getFormattedDate
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.usecase.PlayingNowMoviesUseCase

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    private val movieDbClient by lazy { MovieDbClient(RetrofitBuilder.movieDbApi) }
    private val moviesService by lazy { MovieServiceImpl(movieDbClient) }
    private val playingNowRepository by lazy { MovieDbRepositoryImpl(moviesService) }
    private val playingNowMoviesUseCase by lazy { PlayingNowMoviesUseCase(playingNowRepository) }
    private val viewModel by lazy { getViewModel { DetailViewModel(args.movieId, playingNowMoviesUseCase) } }

    private lateinit var adapter: GenresAdapter

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).enableHomeUpButton(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        adapter = GenresAdapter()
        binding.recycler.adapter = adapter

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as MainActivity).enableHomeUpButton(false)
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initObservers() {
        with(viewModel) {
            onMovieDetailRetrieved.observe(viewLifecycleOwner, { setMovieDetail(it) })
            onError.observe(viewLifecycleOwner, { /*TODO: Put a snackbar*/ })
        }
    }

    private fun setMovieDetail(detail: MovieDetailItem) {
        with(binding) {
            imageMovieDetail.loadImageFromUrl(POSTER_PATH.plus(detail.poster_path))
            textMovieTitle.text = detail.title
            textMovieReleaseDate.text = getFormattedDate(detail.release_date, NORMAL_DATE, UI_DATE)
            textMovieDetailOverview.text = detail.overview
            adapter.submitList(detail.genres)
        }
    }
}
