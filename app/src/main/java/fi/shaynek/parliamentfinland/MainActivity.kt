package fi.shaynek.parliamentfinland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import fi.shaynek.parliamentfinland.app.MainApplication
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModel
import fi.shaynek.parliamentfinland.data.viewmodels.MemberDetailsViewModelFactory
import fi.shaynek.parliamentfinland.databinding.ActivityMainBinding

/**
 * This class defines the___________
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navControler:NavController
    lateinit var fl: FrameLayout
    lateinit var viewModel: MemberDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navControler = navHost.navController
        setupActionBarWithNavController(navControler)

        viewModel = ViewModelProvider(
            this, MemberDetailsViewModelFactory(
                (this.application as MainApplication).membersRepository
            ))[MemberDetailsViewModel::class.java]

        viewModel.syncFetch(this)

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