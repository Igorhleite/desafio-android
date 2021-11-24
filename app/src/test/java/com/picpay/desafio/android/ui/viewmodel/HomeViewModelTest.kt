package com.picpay.desafio.android.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.dto.UserModel
import com.picpay.desafio.android.domain.remote.GetUsers
import com.picpay.desafio.android.utils.parseResponseError
import com.picpay.desafio.fromDomainListToUserList
import io.mockk.MockKAnnotations.init
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var useCase: GetUsers

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        init(this)
        homeViewModel = HomeViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `view model should call the useCase and get a successful response`() = runBlockingTest {
        coEvery {
            useCase.invoke()
        } returns flow {
            emit(RequestState.ResponseSuccess(getListUserModel()))
        }
        homeViewModel.getUsers()

        homeViewModel.userEvent.observeForever { result ->
            assertEquals(RequestState.ResponseSuccess(getListUserModel()), result)
        }
    }

    @Test
    fun `view model should call the useCase and get a failure response`() = runBlockingTest {
        coEvery {
            useCase.invoke()
        } returns flow {
            emit(
                RequestState.ResponseFailure(
                    getListUserModel(),
                    getResponseError().parseResponseError()
                )
            )
        }
        homeViewModel.getUsers()

        homeViewModel.userEvent.observeForever { result ->
            assertEquals(
                RequestState.ResponseFailure(
                    getListUserModel(),
                    getResponseError().parseResponseError()
                ), result
            )
        }
    }

    @Test
    fun `view model should call the useCase and get a exception response`() = runBlockingTest {
        coEvery {
            useCase.invoke()
        } returns flow {
            emit(
                RequestState.ResponseException(
                    getListUserModel(),
                    Exception("Generic Exception!!")
                )
            )
        }
        homeViewModel.getUsers()

        homeViewModel.userEvent.observeForever { result ->
            assert(result is RequestState.ResponseException)
        }
    }

    private fun getResponseError(): Response<List<UserModel>> {
        return Response.error(401, ResponseBody.create(MediaType.get("application/json"), ""))
    }

    private fun getListUserModel(): List<User> {
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
        return userList.fromDomainListToUserList()
    }
}