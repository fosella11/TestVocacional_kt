package com.softf.vocacional.network

import com.softf.vocacional.model.PullRequest
import com.softf.vocacional.model.Repository
import com.softf.vocacional.model.VocacionalTest
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("{owner_name}/{repo_name}")
    fun getRepo(
        @Path("owner_name") ownerName: String,
        @Path("repo_name") repositoryName: String
    ): Deferred<Repository>

    @GET("{owner_name}/{repo_name}/pulls")
    fun getPullRequests(
        @Path("owner_name") ownerName: String,
        @Path("repo_name") repositoryName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("state") state: String
    ): Deferred<List<PullRequest>>

    @GET("{lang}.json")
    fun getTestVocacional(
        @Path("lang") languageTests: String
    ): Deferred<VocacionalTest>
}