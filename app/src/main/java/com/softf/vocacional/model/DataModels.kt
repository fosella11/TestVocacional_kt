package com.softf.vocacional.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class RepoDetails(
    var ownerName: String = "",
    var repoName: String = ""
)

@Entity(tableName = "repository")
data class Repository(
    @PrimaryKey
    var id: Long = 0,
    var name: String = "",
    var private: Boolean = false,
    var ownerName: String = ""
)

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository LIMIT 1")
    suspend fun getRepository(): Repository

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: Repository)

    @Query("DELETE FROM repository")
    suspend fun nukeTable()
}

@Entity(tableName = "pullRequest")
data class PullRequest(
    @PrimaryKey
    val id: Long = 0,
    val title: String = "",
    val updated_at: String = "",
    val created_at: String = "",
    val state: String = "",
    val number: Long = 0,
    val html_url: String = "",
    @TypeConverters(UserTypeConverter::class)
    val user: User = User()
)

@Dao
interface PullRequestDao {

    @Query("SELECT * FROM pullRequest")
    suspend fun getPullRequests(): List<PullRequest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pullRequestList: List<PullRequest>)

    @Query("DELETE FROM pullRequest")
    suspend fun nukeTable()
}

data class User(
    val login: String = "",
    val avatar_url: String = ""
)

data class VocacionalTest(
    val name: String = "",
    val language: String = "",
    val web: String = "",
    var products: List<Product>,
    var questions: List<Question>
)

@Entity(tableName = "product")
data class Product(
    @PrimaryKey var uid: Int,
    @SerializedName("tittle")
    @ColumnInfo(name = "tittle") var tittle: String,
    @SerializedName("full_info")
    @ColumnInfo(name = "fullInfo") var fullInfo: String,
    @SerializedName("image")
    @ColumnInfo(name = "image") var image: String,
    @SerializedName("default_image")
    @ColumnInfo(name = "defaultImage") var defaultImage: String,
    @SerializedName("free")
    @ColumnInfo(name = "isFree") var isFree: Boolean,
    @SerializedName("url_info")
    @ColumnInfo(name = "urlInfo") var urlInfo: String)

@Entity(tableName = "question")
data class Question(
    @PrimaryKey var uid: Int,
    @SerializedName("test_id")
    @ColumnInfo(name = "testId") var testId: Int,
    @SerializedName("name")
    @ColumnInfo(name = "name") var name: String,
    @SerializedName("question_num")
    @ColumnInfo(name = "questionNumCount") var questionNumCount: Int,
    @SerializedName("ads")
    @ColumnInfo(name = "withAds") var withAds: Boolean,
    @SerializedName("question")
    @ColumnInfo(name = "question") var question: String,
    @SerializedName("is_responded")
    @ColumnInfo(name = "isResponded") var isResponded: Boolean,
    @SerializedName("response")
    @ColumnInfo(name = "response") var response: Boolean)

@Dao
interface ProductDao {

    @Query("SELECT * FROM product ORDER BY uid DESC")
    suspend fun getProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productList: List<Product>)

    @Query("DELETE FROM product")
    suspend fun nukeTable()
}

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question ORDER BY uid DESC")
    suspend fun getQuestions(): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionList: List<Question>)

    @Query("DELETE FROM question")
    suspend fun nukeTable()

    @Update
    suspend fun updateQuestion(question: Question)
}