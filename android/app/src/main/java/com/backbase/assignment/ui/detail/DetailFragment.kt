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
import com.backbase.assignment.ui.detail.model.MovieDetailItem
import com.backbase.assignment.ui.extensions.loadImageFromUrl
import com.backbase.assignment.ui.home.HomeViewModel.Companion.POSTER_PATH
import com.flagos.common.getViewModel
import com.flagos.data.api.ApiHelper
import com.flagos.data.api.RetrofitBuilder
import com.flagos.data.repository.MovieDbRepository

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val apiHelper by lazy { ApiHelper(RetrofitBuilder.movieDbApi) }
    private val movieDbRepository by lazy { MovieDbRepository(apiHelper) }
    private val viewModel by lazy { getViewModel { DetailViewModel(args.movieId, movieDbRepository) } }

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
        }
    }

    private fun setMovieDetail(detail: MovieDetailItem) {
        with(binding) {
            imageMovieDetail.loadImageFromUrl(POSTER_PATH.plus(detail.porterPath))
            textMovieTitle.text = detail.title
            textMovieReleaseDate.text = detail.releaseDate
            textMovieDetailOverview.text = detail.overview
            adapter.submitList(detail.genres)
        }
    }
}
