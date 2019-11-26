package com.geekhub.mariia_piastro.hw2.primenumbers

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import java.util.concurrent.atomic.AtomicBoolean

object SingletonForThread {

    private var thread: Thread? = null
    private var lastPrimeNumber = 0
    private val to = 10000000
    var running: AtomicBoolean = AtomicBoolean(false)

    var callback: Callback? = null

    interface Callback {

        @MainThread
        fun onNewNumberFound(number: Int)
    }

    fun findPrimeNumbers() {
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
                                callback?.onNewNumberFound(i)
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

    fun stop() {
        running.set(false)
        if (thread != null) {
            val dummy = thread
            thread = null
            dummy?.interrupt()
        }
    }
}