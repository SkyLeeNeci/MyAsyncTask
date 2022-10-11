package test.karpenko.myasynctask

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import test.karpenko.myasynctask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startTimerBtn.setOnClickListener {
            object : MyAsyncTask(10) {
                override fun onPreExecute() {
                    binding.timerResult.text = "0"
                    Log.d(TAG, "onPreExecute ${currentThread().name}")
                }

                override fun onPostExecute(result: Int?) {
                    Log.d(TAG, "onPostExecute ${currentThread().name}")
                    Toast.makeText(this@MainActivity, "Result: $result", Toast.LENGTH_SHORT).show()
                }

                override fun onProgressUpdate(result: Int?) {
                    Log.d(TAG, "onProgressUpdate ${currentThread().name}")
                    binding.timerResult.text = "$result"
                }

                override fun doInBackground(urls: Int): Int {
                    for (i  in 0 until  urls) {
                        publishProgress(i)
                        Log.d(TAG, "doInBackground ${currentThread().name}")
                        try {
                            sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    return urls
                }
            } }

    }

    companion object{
        private const val TAG = "TEST_TAG"
    }

}