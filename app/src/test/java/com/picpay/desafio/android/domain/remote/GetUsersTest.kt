package com.picpay.desafio.android.domain.remote

import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.error.ResponseError
import com.picpay.desafio.android.data.remote.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.MockKAnnotations.init
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class GetUsersTest {

    private lateinit var useCase: GetUsers

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        init(this)
        useCase = GetUsers(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `useCase should call the repository and get a successful answer`() = runBlockingTest {
        coEvery {
            repository.getUsers()
        } returns flow {
            emit(RequestState.ResponseSuccess(emptyList<User>()))
        }
        useCase.invoke().collect { result ->
            assert(result is RequestState.ResponseSuccess)
        }
    }

    @Test
    fun `useCase should call the repository and get a failure answer`() = runBlockingTest {
        coEvery {
            repository.getUsers()
        } returns flow {
            emit(RequestState.ResponseFailure(emptyList<User>(), ResponseError()))
        }
        useCase.invoke().collect { result ->
            assert(result is RequestState.ResponseFailure)
        }
    }

    @Test
    fun `useCase should call the repository and get a exception answer`() = runBlockingTest {
        coEvery {
            repository.getUsers()
        } returns flow {
            emit(RequestState.ResponseException(emptyList<User>(), Exception()))
        }
        useCase.invoke().collect { result ->
            assert(result is RequestState.ResponseException)
        }
    }
}