package com.picpay.desafio.android.data.remote.repository

import androidx.room.withTransaction
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.local.UserDataBase
import com.picpay.desafio.android.data.model.dto.UserModel
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.utils.parseResponseError
import com.picpay.desafio.fromDomainListToUserList
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    @MockK
    private lateinit var picPayService: PicPayService

    @MockK
    private lateinit var userDataBase: UserDataBase

    private lateinit var repository: UserRepository

    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        initMocks()
        repository = UserRepositoryImpl(picPayService, userDataBase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initMocks() {
        MockKAnnotations.init(this)
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )
        val transactionLambda = slot<suspend () -> Unit>()
        coEvery { userDataBase.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
        coEvery {
            userDataBase.userDao().getAllUsers()
        } returns flow { emit(getListUserModel().fromDomainListToEntityList()) }
        coEvery { userDataBase.userDao().deleteALlUsers() } just Runs
        coEvery {
            userDataBase.userDao().insertAllUsers(getListUserModel().fromDomainListToEntityList())
        } just Runs
    }

    @Test
    fun `repository should call the service and get a successful response`() = runBlockingTest {
        coEvery { picPayService.getUsers() } returns Response.success(getListUserModel())
        repository.getUsers().collect { result ->
            assertEquals(RequestState.ResponseSuccess(getResponseSuccess()), result)
        }
    }

    @Test
    fun `repository should call the service and get a error response`() = runBlockingTest {
        coEvery { picPayService.getUsers() } returns getResponseError()
        repository.getUsers().collect { result ->
            assertEquals(
                RequestState.ResponseFailure(
                    getListUserModel().fromDomainListToUserList(),
                    getResponseError().parseResponseError()
                ),
                result
            )
        }
    }

    @Test
    fun `repository should call the service and get a exception response`() = runBlockingTest {
        coEvery { picPayService.getUsers() } throws getException()
        repository.getUsers().collect { result ->
            assert(result is RequestState.ResponseException)
        }
    }

    private fun getException() = Exception("Generic exception!!")

    private fun getResponseError(): Response<List<UserModel>> {
        return Response.error(401, ResponseBody.create(MediaType.get("application/json"), ""))
    }

    private fun getResponseSuccess() = getListUserModel().fromDomainListToUserList()

    private fun getListUserModel(): List<UserModel> {
        val userList = mutableListOf<UserModel>()
        for (i in 1..4) {
            userList.add(
                UserModel(
                    id = i,
                    name = "user$i",
                    username = "@userName$i",
                    img = "imgUrl$i"
                )
            )
        }
        return userList
    }
}