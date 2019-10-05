package korotchenko.financemanager

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import korotchenko.logic.MainLogic
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()

        button.setOnClickListener {
            hello.text = MainLogic.test()
        }
    }
}
