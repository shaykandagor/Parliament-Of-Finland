package dev.vstec.parliament2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dev.vstec.parliament2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var navControler:NavController
    companion object {
        var SHARED: String? = null
    }

    lateinit var fl: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navControler = navHost.navController
        setupActionBarWithNavController(navControler)

        /*fl = findViewById(R.id.fl)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl, PartiesFragment())
//            .add(R.id.fl, MemberDetailsFragment())
            .commit()*/
    }

    override fun onSupportNavigateUp(): Boolean {
        return navControler.navigateUp() ||  super.onSupportNavigateUp()
    }

}