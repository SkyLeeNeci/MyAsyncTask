package test.karpenko.myasynctask

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import test.karpenko.myasynctask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.startTimerBtn.setOnClickListener {
            object : MyAsyncTask<Int>(10) {
                override fun onPreExecute() {
                    binding.timerResult.text = "0"
                    Log.d(TAG, "onPreExecute ${currentThread().name}")
                }

                override fun onPostExecute(result: Int) {
                    Log.d(TAG, "onPostExecute ${currentThread().name}")
                    Toast.makeText(this@MainActivity, "Result: $result", Toast.LENGTH_SHORT).show()
                }

                override fun onProgressUpdate(result: Int) {
                    Log.d(TAG, "onProgressUpdate ${currentThread().name}")
                    binding.timerResult.text = "$result"
                }

                override fun doInBackground(urls: Int): Int {
                    for (i in 0 until urls) {
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
            }
        }*/

        binding.startTimerBtn.setOnClickListener {
            object : MyAsyncTask2<Int, Int, AsyncResult<String>>(10) {
                override fun onPreExecute() {
                    binding.timerResult.text = "0"
                    Log.d(TAG, "onPreExecute ${currentThread().name}")
                }

                override fun onProgressUpdate(result: Int) {
                    Log.d(TAG, "onProgressUpdate ${currentThread().name}")
                    binding.timerResult.text = "$result"
                }

                override fun onPostExecute(result: AsyncResult<String>) {
                    when (result) {
                        is AsyncResult.Success -> {
                            Toast.makeText(this@MainActivity, "Result: ${result.data}", Toast.LENGTH_SHORT).show()
                        }

                        is AsyncResult.Error -> {
                            Log.d(TAG, "onPostExecute ${currentThread().name}")
                            Toast.makeText(this@MainActivity, "Result: ${result.message}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                override fun doInBackground(urls: Int): AsyncResult<String> {
                    for (i in 0 until urls) {
                        publishProgress(i)
                        Log.d(TAG, "doInBackground ${currentThread().name}")
                        try {
                            sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    return AsyncResult.Success(urls.toString())
                }

            }
        }
    }

    companion object {
        private const val TAG = "TEST_TAG"
    }
}