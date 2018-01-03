package com.example.jsc.myapplication.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ImageView
import com.example.jsc.myapplication.R
import kotlinx.android.synthetic.main.adapter_image.view.*


/**
 * Created by jsc on 2017/6/29.
 */
class ImageAdapter(var mData: ArrayList<Bean>, var context: Context) : BaseAdapter() {
    var index: Int = -1
    var currentFocusEdit: EditText? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        //重用view
        var v: View
        if (convertView == null) {
            v = View.inflate(context, R.layout.adapter_image, null)
            holder = ViewHolder(v)
            v.setTag(holder)
            v.edt.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    when (event?.action) {
                        MotionEvent.ACTION_UP -> index = position
                    }
                    return false
                }
            }
            )
        } else {
            v = convertView
            holder = convertView.getTag() as ViewHolder
        }
        val bean = getItem(position)
        holder.edt.setText(bean.text)
        when (bean.type) {
            0 -> {
                holder.edt.visibility = View.VISIBLE
                holder.img.visibility = View.GONE
            }
            1 -> {
                holder.edt.visibility = View.GONE
                holder.img.visibility = View.VISIBLE
            }
        }
        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            holder.edt.requestFocus();
//            holder.edt.setSelection(holder.edt.getText().length)
            currentFocusEdit = holder.edt
        }
        if(position==0) holder.edt.requestFocus()
        holder.edt.setTag(position)
        holder.edt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                ""
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                ""
            }

            override fun afterTextChanged(s: Editable?) {
                bean.text = s?.toString() ?: ""
            }

        })

        return v
    }

    override fun getItem(position: Int): Bean {
        return mData.get(position)
    }

    public fun resetFocus() {
        index = -1;
//        currentFocusEdit?.clearFocus();
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mData.size
    }

    class ViewHolder(var v: View) {
        var edt = v.findViewById(R.id.edt) as EditText
        var img = v.findViewById(R.id.img) as ImageView
    }
}

//type 0文字 1图片
public class Bean(var type: Int, var text: String)