package com.geekhub.mariia_piastro.hw2.primenumbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.atomic.AtomicBoolean


class MainActivity : AppCompatActivity() {

    private var primeNumber: ArrayList<Int> = ArrayList()
    private lateinit var myAdapter: MyAdapter
    private var thread: Thread? = null
    private var lastPrimeNumber = 0
    private val to = 10000000
    private var running: AtomicBoolean = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.run {
            running.set(getBoolean("running"))
            primeNumber = getIntegerArrayList("primeNumbers") ?: ArrayList()
            lastPrimeNumber = getInt("lastPrimeNumber")
            if (running.get())
                findPrimeNumbers()
        }

        myAdapter = MyAdapter(primeNumber)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonStart.setOnClickListener {
            if (!running.get())
                findPrimeNumbers()
        }

        buttonStop.setOnClickListener {
            running.set(false)
            if (thread != null) {
                val dummy = thread
                thread = null
                dummy?.interrupt()
            }
        }
    }

    private fun findPrimeNumbers() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            running.set(true)
            while (running.get()) {
                try {
                    for (i in lastPrimeNumber + 1..to) {
                        var isPrimal = false
                        for (j in 2 until i) {
                            isPrimal = i % j != 0
                            if (!isPrimal) break
                        }
                        if (isPrimal) {
                            lastPrimeNumber = i
                            handler.post {
                                primeNumber.add(i)
                                myAdapter.notifyDataSetChanged()
                            }
                            Thread.sleep(500)
                        }
                    }
                } catch (e: InterruptedException) {
                    break
                }
            }
        }
        thread = Thread(runnable)
        thread?.start()
    }

    override fun onDestroy() {
        if (thread != null) {
            val dummy = thread
            thread = null
            dummy?.interrupt()
        }
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putIntegerArrayList("primeNumbers", primeNumber)
            putBoolean("running", running.get())
            putInt("lastPrimeNumber", lastPrimeNumber)
        }
    }
}
