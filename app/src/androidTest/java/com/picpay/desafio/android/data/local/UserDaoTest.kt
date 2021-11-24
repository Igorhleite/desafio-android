package com.picpay.desafio.android.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.data.model.dto.UserModel
import com.picpay.desafio.android.data.model.entities.UserEntity
import com.picpay.desafio.android.data.remote.repository.fromDomainListToEntityList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class UserDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_user_db")
    lateinit var database: UserDataBase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertListOnDataBase() = runBlockingTest {
        insertOnDataBase()
    }

    @Test
    fun getListOnDataBase() = runBlockingTest {
        insertOnDataBase()
        dao.getAllUsers().first().let { actualList ->
            assert(actualList == getListUserModel())
        }
    }

    @Test
    fun insertAndDeleteOnDataBase() = runBlockingTest {
        insertOnDataBase()
        dao.deleteALlUsers()
        dao.getAllUsers().first().let { actualList ->
            assert(actualList.isEmpty())
        }
    }

    private suspend fun insertOnDataBase() {
        dao.insertAllUsers(getListUserModel())
    }

    private fun getListUserModel(): List<UserEntity> {
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
        return userList.fromDomainListToEntityList()
    }
}