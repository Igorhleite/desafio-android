package com.picpay.desafio.android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.RequestState
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.FragmentHomeBinding
import com.picpay.desafio.android.ui.adapter.ClickType
import com.picpay.desafio.android.ui.adapter.UserAdapter
import com.picpay.desafio.android.ui.dialog.UserDetailDialog
import com.picpay.desafio.android.ui.viewmodel.HomeViewModel
import com.picpay.desafio.android.utils.getDefaultErrorMessage
import com.picpay.desafio.android.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),
    UserDetailDialog.OnSharedButtonClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var dialog: UserDetailDialog

    val userAdapter by lazy {
        UserAdapter { user, _, clickType ->
            makeClickDialogActions(user, clickType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecycler()
        initObservers()
        initRefreshBtAction()
        initSwipeAction()
    }

    private fun initRefreshBtAction() {
        binding.apply {
            screenError.btErrorRefresh.setOnClickListener {
                viewModel.getUsers()
            }
        }
    }

    private fun initSwipeAction() {
        binding.apply {
            swRefresh.setOnRefreshListener {
                viewModel.getUsers()
            }
        }
    }

    private fun makeClickDialogActions(user:User, clickType: ClickType){
        when (clickType) {
            ClickType.DETAIL -> {
                buildDialog(user)
            }
            ClickType.SHARE -> {
                shareContact(user)
            }
        }
    }

    private fun initRecycler() {
        binding.rcUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isVerticalFadingEdgeEnabled = true
            setFadingEdgeLength(150)
        }
    }

    private fun initObservers() {
        viewModel.userEvent.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.ResponseSuccess -> {
                    submitListToAdapter(it.data)
                }
                is RequestState.ResponseFailure -> {
                    requireContext().apply {
                        it.error?.message?.let { errorMessage ->
                            if (errorMessage.trim().isEmpty()) {
                                makeToast(getDefaultErrorMessage())
                            } else {
                                makeToast(errorMessage)
                            }
                        }
                    }
                    submitListToAdapter(it.data)
                }
                is RequestState.ResponseException -> {
                    requireContext().apply {
                        makeToast(getDefaultErrorMessage())
                    }
                    submitListToAdapter(it.data)
                }
                is RequestState.Loading -> {
                    binding.apply {
                        it.status.let { status ->
                            pbProgress.isVisible = status
                            swRefresh.isRefreshing = status
                        }
                        screenError.root.isVisible = false
                    }
                }
            }
        }
    }

    private fun buildDialog(user: User) {
        dialog = UserDetailDialog(this)
        dialog.show(parentFragmentManager, "", user)
    }

    private fun shareContact(user: User) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_contact, user.username, user.name))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onPause() {
        super.onPause()
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }

    private fun checkIfShowScreenError(isShowing: Boolean) {
        binding.apply {
            screenError.root.isVisible = isShowing
            rcUsers.isVisible = !isShowing
            tvTitle.isVisible = !isShowing
        }
    }

    private fun submitListToAdapter(list: List<User>) {
        userAdapter.submitList(list)
        checkIfShowScreenError(list.isNullOrEmpty())
    }

    override fun shareButtonClickListener(user: User) {
        shareContact(user)
    }
}