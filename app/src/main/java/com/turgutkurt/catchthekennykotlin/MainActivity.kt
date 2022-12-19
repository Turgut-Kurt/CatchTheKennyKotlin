package com.turgutkurt.catchthekennykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.turgutkurt.catchthekennykotlin.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.myLooper()!!)
    var runnable = Runnable{}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        hideVisible()
        object : CountDownTimer(15000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timeTextView.text="Time : ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                binding.timeTextView.text="Time : 0"

                handler.removeCallbacks(runnable)
                for (image in imageArray){
                    image.visibility=View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart Game ?")
                alert.setPositiveButton("Yes"){dialog,which ->
                    println("Restart app")
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No"){dialog,which ->
                    Toast.makeText(this@MainActivity,"Game Over",Toast.LENGTH_LONG).show()
                }
                alert.show()
            }

        }.start()
    }

    fun hideVisible(){
        runnable = object  : Runnable{
            override fun run() {
                for (image in imageArray){
                    image.visibility=View.INVISIBLE
                }

                var random = Random()
                var randomIndex= random.nextInt(9)
                imageArray[randomIndex].visibility=View.VISIBLE
                handler.postDelayed(runnable,600)
            }
        }

        handler.post(runnable)


    }

    fun handleIncreaseScore (view: View){
        score+=1
        binding.scoreTextView.text="Score : ${score}"
    }
}