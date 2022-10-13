package test.karpenko.myasynctask

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log

abstract class MyAsyncTask2<Input, Progress, AsyncResult>(private val urls: Input) : Thread() {

    private val mMainHandler: Handler = Handler(Looper.getMainLooper())

    abstract fun onPreExecute()
    abstract fun onProgressUpdate(result: Progress)
    abstract fun onPostExecute(result: AsyncResult)
    abstract fun doInBackground(urls: Input): AsyncResult

    protected fun publishProgress(progress: Progress) {
        mMainHandler.post {
            onProgressUpdate(progress)
        }
    }

    private fun execute() {
        start()
    }

    override fun run() {
        // run in UI thread.
        mMainHandler.post { onPreExecute() }
        val result = doInBackground(urls) // run in the background thread.
            // run in UI thread.
            mMainHandler.post {
                result?.let{ onPostExecute(result) }
            }
        }

    init {
        execute()
    }

}

