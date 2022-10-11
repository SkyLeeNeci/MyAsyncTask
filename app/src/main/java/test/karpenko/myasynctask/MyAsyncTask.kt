package test.karpenko.myasynctask

import android.os.Handler
import android.os.Looper

abstract class MyAsyncTask(private val urls: Int) : Thread() {

    private var mUrls: Int? = null
    private val mMainHandler: Handler = Handler(Looper.getMainLooper())

     abstract fun onPreExecute()
     abstract fun onPostExecute(result: Int?)
     abstract fun onProgressUpdate(result: Int?)


     fun publishProgress(progress: Int?) {
        mMainHandler.post {
            onProgressUpdate(progress)
        }
    }

     abstract fun doInBackground(urls: Int): Int

    private fun execute() {
        mUrls = urls
        start()
    }

    override fun run() {
        // run in UI thread.
        mMainHandler.post { onPreExecute() }
        var result: Int? = null
        try {
            // run in the background thread.
            result = mUrls?.let { doInBackground(it) }
        } finally {
            // run in UI thread.
            mMainHandler.post { onPostExecute(result) }
        }
    }

    init {
        execute()
    }

}