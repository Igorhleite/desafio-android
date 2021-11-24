package com.picpay.desafio.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.databinding.ListItemUserBinding

class UserAdapter(
    private val onClickListener: (user: User, position: Int, clickType: ClickType) -> Unit
) :
    ListAdapter<User, UserAdapter.UserViewHolder>(UserComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    class UserViewHolder(
        private val binding: ListItemUserBinding,
        private val onClickListener: (user: User, position: Int, clickType: ClickType) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                tvUserName.text = if (user.username.trim().isEmpty()) {
                    binding.root.context.getString(R.string.user_name_not)
                } else {
                        binding.root.context.getString(R.string.user_name, user.username)
                }

                tvName.text = if (user.name.trim().isEmpty()) {
                    binding.root.context.getString(R.string.user_name_not)
                } else {
                    user.name
                }

                ivUserImage.load(user.img) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                    transformations(CircleCropTransformation())
                }
                ibShare.setOnClickListener {
                    onClickListener(user, layoutPosition, ClickType.SHARE)
                }
                root.setOnClickListener {
                    onClickListener(user, layoutPosition, ClickType.DETAIL)
                }
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}

public enum class ClickType{
    DETAIL, SHARE
}