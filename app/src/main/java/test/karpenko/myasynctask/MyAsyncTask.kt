package test.karpenko.myasynctask

import android.os.Handler
import android.os.Looper

abstract class MyAsyncTask<T>(private val urls: T) : Thread() {

    private val mMainHandler: Handler = Handler(Looper.getMainLooper())

    abstract fun onPreExecute()
    abstract fun onPostExecute(result: T)
    abstract fun onProgressUpdate(result: T)

    protected fun publishProgress(progress: T) {
        mMainHandler.post {
            onProgressUpdate(progress)
        }
    }

    abstract fun doInBackground(urls: T): T

    private fun execute() {
        start()
    }

    override fun run() {
        // run in UI thread.
        mMainHandler.post { onPreExecute() }
        var result: T? = null
        try {
            // run in the background thread.
            result = doInBackground(urls)
        } finally {
            // run in UI thread.
            mMainHandler.post {
                if (result != null) {
                    onPostExecute(result)
                }
            }
        }
    }

    init {
        execute()
    }
}