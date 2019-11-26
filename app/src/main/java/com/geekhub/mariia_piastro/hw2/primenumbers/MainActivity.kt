package com.geekhub.mariia_piastro.hw2.primenumbers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var primeNumber: ArrayList<Int> = ArrayList()
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = MyAdapter(primeNumber)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        SingletonForThread.callback = object : SingletonForThread.Callback {
            override fun onNewNumberFound(number: Int) {
                primeNumber.add(number)
                myAdapter.notifyDataSetChanged()
            }

        }

        buttonStart.setOnClickListener {
            if (!SingletonForThread.running.get())
                SingletonForThread.findPrimeNumbers()
        }

        buttonStop.setOnClickListener {
            SingletonForThread.stop()
        }
    }

    override fun onDestroy() {
        SingletonForThread.stop()
        SingletonForThread.callback = null
        super.onDestroy()
    }
}
