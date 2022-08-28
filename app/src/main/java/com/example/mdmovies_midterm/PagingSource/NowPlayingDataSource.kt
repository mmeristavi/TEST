package com.example.mdmovies_midterm.PagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mdmovies_midterm.Models.NowPlayingMoviesModel

import com.example.mdmovies_midterm.Network.RetrofitClient

class NowPlayingDataSource: PagingSource<Int, NowPlayingMoviesModel.Result>() {

    override fun getRefreshKey(state: PagingState<Int, NowPlayingMoviesModel.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingMoviesModel.Result> {
        val page: Int = params.key ?: 1
        val prevPage = if (page != 1) page - 1 else null
        val nextPage = page + 1
        return try {
            val response = RetrofitClient.connectRetrofit()
                .getNowPlayingMovies(key = "86d68d7f4a0e3dccfa45e4e97b45f379", page = page)
            if (response.isSuccessful) {
                val movies = response.body()?.results?: emptyList()
                return LoadResult.Page (movies, prevPage, nextPage)

            } else {
                return LoadResult.Error(Throwable())
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}