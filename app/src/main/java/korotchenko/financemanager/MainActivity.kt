package korotchenko.financemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import korotchenko.logic.MainLogic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            hello.text = MainLogic.test()
        }
    }
}
