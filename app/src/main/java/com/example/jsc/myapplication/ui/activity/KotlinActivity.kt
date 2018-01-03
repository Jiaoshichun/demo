package com.example.jsc.myapplication.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.jsc.myapplication.R

class KotlinActivity : AppCompatActivity() {
    var txt: TextView? = null
        get() = findViewById(R.id.txt1) as TextView
    var a: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        a()
        a = 10
        var items = setOf("apple", "banana", "kiwi")
    }

    fun a() {
        txt?.setOnClickListener(View.OnClickListener {
            txt?.setText("22222")
            longToast("厉害呀")
            PreferenceManager.getDefaultSharedPreferences(this).editor {
                it.putBoolean("1", true)
            }

        })

        b { c() }
    }

    fun c(): Int {
        return 2
    }

    fun b(f: () -> Int) {
        val f1 = f()

    }
}

fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
}

fun SharedPreferences.editor(f: (SharedPreferences.Editor) -> Unit) {
    val edit = edit()
    f(edit)
    edit.apply()
}
