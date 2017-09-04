package com.example.stack

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_stack.view.*
import org.jetbrains.anko.toast

/**
 * Created by yanchunlan on 2017/9/3.
 */
class StackAdapter(val list: ArrayList<StackEntity> = arrayListOf())
    : RecyclerView.Adapter<StackAdapter.StackViewHolder>() {

    private val drawables = arrayOf(R.mipmap.stack1, R.mipmap.stack2,
            R.mipmap.stack3, R.mipmap.stack4,
            R.mipmap.stack5, R.mipmap.stack6)

    init {
        drawables.forEachIndexed { index, item ->
            list.add(StackEntity(item, index))
        }
    }

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        with(holder.itemView) {
            val item = list[position]
            item_stack_iv.setBackgroundResource(item.mipmap)
            item_stack_tv.text = String.format(context.getString(R.string.item_tv), item.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StackViewHolder
            = StackViewHolder(View.inflate(parent?.context, R.layout.item_stack, null))

    override fun getItemCount(): Int = list.size


    class StackViewHolder(item: View) : RecyclerView.ViewHolder(item)
}
