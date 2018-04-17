package com.lgh.sunshine

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(diskIO : Executor, networkIO: Executor, mainThread : Executor)
{
    var diskIO = diskIO
    var mainThread = mainThread
    var networkIO = networkIO

    companion object
    {
        private val LOCK = Any()
        private var mInstance : AppExecutors? = null

        fun getInstance() : AppExecutors
        {
            if(mInstance == null)
            {
                synchronized(LOCK)
                {
                    mInstance = AppExecutors(Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(3),
                            MainThreadExecutor())
                }
            }
            return mInstance!!
        }

        private class MainThreadExecutor : Executor
        {
            val mainThreadHandler = Handler(Looper.getMainLooper())

            override fun execute(command: Runnable?)
            {
                mainThreadHandler.post(command)
            }
        }
    }
}