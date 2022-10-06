package fi.shaynek.parliamentfinland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import fi.shaynek.parliamentfinland.ui.fragments.MemberDetailsFragment

class MainActivity : AppCompatActivity() {
    lateinit var fl: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fl = findViewById(R.id.fl)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl, MemberDetailsFragment())
            .commit()
    }
}