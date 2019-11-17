package com.geekhub.mariia_piastro.hw2.primenumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var primeNumber: ArrayList<Int> = ArrayList()
    private lateinit var myAdapter: MyAdapter
    private var thread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = MyAdapter(primeNumber)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonStart.setOnClickListener {
            findPrimeNumbers()
        }

        buttonStop.setOnClickListener {
            if (thread != null) {
                val dummy = thread
                thread = null
                dummy?.interrupt()
            }
        }
    }

    private fun findPrimeNumbers() {
        val handler = Handler()
        val runnable = Runnable {
            for (i in 1..1000000) {
                var isPrimal = false
                for (j in 2 until i) {
                    isPrimal = i % j != 0
                    if (!isPrimal) break
                }
                if (isPrimal) {
                    handler.post {
                        primeNumber.add(i)
                        myAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        thread = Thread(runnable)
        thread?.start()
    }
}
