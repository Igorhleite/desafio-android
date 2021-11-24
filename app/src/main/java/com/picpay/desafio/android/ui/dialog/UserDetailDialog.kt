package com.picpay.desafio.android.ui.dialog

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import coil.load
import coil.transform.CircleCropTransformation
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.DetailDialogBinding

class UserDetailDialog(
    private val shareClickListener: OnSharedButtonClickListener
) :
    DialogFragment(R.layout.detail_dialog) {

    private var _binding: DetailDialogBinding? = null
    private val binding get() = _binding!!

    private var currentUser: User = User("", "", "", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DetailDialogBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        initDialog()
        setClickListener()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.run {
            setBackgroundDrawableResource(android.R.color.transparent);
            setLayout(
                width,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun setClickListener() {
        binding.apply {
            ivClose.setOnClickListener {
                dismiss()
            }
            ivShareUser.setOnClickListener {
                shareClickListener.shareButtonClickListener(currentUser)
                dismiss()
            }
        }
    }

    private fun initDialog() {
        binding.apply {
            ivDetailUserImg.load(currentUser.img) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
                transformations(CircleCropTransformation())
            }
            tvDetailUserName.text = if (currentUser.username.trim().isEmpty()) {
                binding.root.context.getString(R.string.user_name_not)
            } else {
                binding.root.context.getString(R.string.user_name, currentUser.username)
            }
            tvDetailName.text = if (currentUser.name.trim().isEmpty()) {
                binding.root.context.getString(R.string.user_name_not)
            } else {
                currentUser.name
            }
        }
    }

    fun show(fragmentManager: FragmentManager, tag: String, user: User) {
        currentUser = user
        super.show(fragmentManager, tag)
    }

    interface OnSharedButtonClickListener {
        fun shareButtonClickListener(user: User)
    }
}