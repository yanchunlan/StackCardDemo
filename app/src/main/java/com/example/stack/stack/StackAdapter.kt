package com.example.stack.stack

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.stack.R
import com.example.stack.utils.ScreenUtils
import kotlinx.android.synthetic.main.item_stack.view.*

/**
 * Created by yanchunlan on 2017/9/3.
 */
class StackAdapter(val list: ArrayList<StackEntity> = arrayListOf())
    : RecyclerView.Adapter<StackAdapter.StackViewHolder>() {

    private val drawables = arrayOf(R.mipmap.stack1, R.mipmap.stack2,
            R.mipmap.stack3, R.mipmap.stack4,
            R.mipmap.stack5, R.mipmap.stack6)

    var halfHeight = false

    init {
        drawables.forEachIndexed { index, item ->
            list.add(StackEntity(item, index))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackViewHolder {
        val view = View.inflate(parent?.context, R.layout.item_stack, null)
        if (halfHeight) {
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(parent.context) / 2)
        }
        return StackViewHolder(view)
    }

    override fun onBindViewHolder(holder: StackViewHolder, position: Int) {
        with(holder.itemView) {
            val item = list[position]
            item_stack_iv.setBackgroundResource(item.mipmap)
            item_stack_tv.text = String.format(context.getString(R.string.item_tv), item.description)
        }
    }

    override fun getItemCount(): Int = list.size


    class StackViewHolder(item: View) : RecyclerView.ViewHolder(item)
}
