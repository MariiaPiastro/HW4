package com.geekhub.mariia_piastro.hw2.primenumbers

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var primeNumber: ArrayList<Int> = ArrayList()
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        primeNumber= savedInstanceState?.getIntegerArrayList("primeNumbers") ?: ArrayList()

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList("primeNumbers", primeNumber)
    }

    override fun onDestroy() {
        SingletonForThread.callback = null
        super.onDestroy()
    }
}
