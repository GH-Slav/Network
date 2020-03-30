package by.tms.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.tms.network.retrofit.CoinApiFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val response = CoinApiFactory.getRetrofit().getTopCoins(10, "BYN").await()

                if (response.isSuccessful) {
                    val coins = response.body()

                    withContext(Dispatchers.Main) {
                        text.text = coins?.data.toString()
                    }
                } else {
                    Log.e("ERROR", response.code().toString())
                }
            }
        }
    }
}

