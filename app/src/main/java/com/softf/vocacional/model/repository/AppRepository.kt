package com.softf.vocacional.model.repository

import com.softf.vocacional.model.*
import com.softf.vocacional.network.NetworkService
import com.softf.vocacional.utils.Prefs
import kotlinx.coroutines.delay

class AppRepository(
    private val prefs: Prefs, private val networkService: NetworkService, private val appDatabase: AppDatabase
) {

    // Items per page for query
    private val ITEMS_PER_PAGE = 20
    // Either open, closed, or all to filter by state.
    private val REQUEST_STATE = "all"

    private val isQuestionsLoaded = prefs.isQuestionOnDB()

    suspend fun getRepoSelectedStatus(): Boolean {
        delay(2000)
        return prefs.getRepoSelectedStatus()
    }

    suspend fun getRepo(ownerName: String, repositoryName: String) =
        networkService.getRepo(ownerName, repositoryName).await()

    suspend fun getRepoFromDB() = appDatabase.repositoryDao().getRepository()

    suspend fun saveRepo(repository: Repository) {
        appDatabase.repositoryDao().apply {
            nukeTable()
            insert(repository)
        }
        prefs.setRepoSelected(true)
        prefs.setRepoName("${repository.ownerName}/${repository.name}")
    }




    suspend fun getPullRequests(page: Int): List<PullRequest> {
        val repo = getRepoFromDB()
        val pullRequests = networkService.getPullRequests(
            repo.ownerName,
            repo.name,
            page,
            ITEMS_PER_PAGE,
            REQUEST_STATE
        ).await()
        if (page == 1) {
            appDatabase.pullRequestDao().apply {
                nukeTable()
                insert(pullRequests)
            }
        }
        return pullRequests
    }

    suspend fun getPullRequestsFromDB() = appDatabase.pullRequestDao().getPullRequests().reversed()

    fun getRepositoryName() = prefs.getRepoName()

    suspend fun changeRepository() {
        appDatabase.pullRequestDao().nukeTable()
        appDatabase.repositoryDao().nukeTable()
        prefs.setRepoSelected(false)
    }


    suspend fun getTestVocacional(): VocacionalTest {
        //val repo = getRepoFromDB()
        val testVocacionalData = networkService.getTestVocacional(
            "spanish"
        ).await()
        appDatabase.productDao().apply {
            nukeTable()
            insert(testVocacionalData.products)
        }

        appDatabase.questionDao().apply {
            //nukeTable()
            if(!prefs.isQuestionOnDB()){
                nukeTable()
                insert(testVocacionalData.questions)
                prefs.questionLoaded()
            }
        }

        return testVocacionalData
    }

    suspend fun getProductsFromDB() = appDatabase.productDao().getProducts().reversed()

    suspend fun getQuestionsFromDB() = appDatabase.questionDao().getQuestions()

    suspend fun updateQuestion(question: Question) {
        appDatabase.questionDao().apply {
            updateQuestion(question)
        }
    }
}