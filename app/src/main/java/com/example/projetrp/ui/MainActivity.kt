package com.example.projetrp.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.projetrp.R
import com.example.projetrp.model.Constants
import com.example.projetrp.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var coordinatorLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        coordinatorLayout = findViewById(R.id.drawer_layout)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_apropos,
            R.id.nav_parametres,
            R.id.nav_loidecrets
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @SuppressLint("ResourceType", "CommitPrefEdits")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.nav_setting){
            val dialogView = layoutInflater.inflate(R.layout.dialog_coefficient, null)
            val eta = dialogView.findViewById<TextInputEditText>(R.id.etCoeffa)
            val etb = dialogView.findViewById<TextInputEditText>(R.id.etCoeffb)
            val etc = dialogView.findViewById<TextInputEditText>(R.id.etCoeffc)
            val myPref = Utils.getPreference(this@MainActivity)
            eta.setText(myPref.getString(Constants.COEFF_A, "10.0"))
            etb.setText(myPref.getString(Constants.COEFF_B, "10.0"))
            etc.setText(myPref.getString(Constants.COEFF_C, "10.0"))
            MaterialAlertDialogBuilder(this).apply {
                setTitle("Réglage des coefficients")
                setIcon(R.drawable.ic_baseline_settings_applications_24)
                setView(dialogView)
                setPositiveButton("Valider", DialogInterface.OnClickListener { dialog, which ->
                    val isInputNotEmpty = Utils.isInputNotEmpty(eta)&&Utils.isInputNotEmpty(etb)&&Utils.isInputNotEmpty(etc)
                    if(isInputNotEmpty){
                        val myPrefEd = Utils.getPreference(this@MainActivity).edit()
                        myPrefEd.putString(Constants.COEFF_A, eta.text.toString())
                        myPrefEd.putString(Constants.COEFF_B, etb.text.toString())
                        myPrefEd.putString(Constants.COEFF_C, etc.text.toString())
                        if(myPrefEd.commit()) Snackbar.make(drawer_layout, "Coefficient enregistrés", Snackbar.LENGTH_LONG).show()
                        else Snackbar.make(drawer_layout, "Erreur de sauvegarde", Snackbar.LENGTH_LONG).show()
                    }
                    else Snackbar.make(drawer_layout, "Erreur! Verifier les entrées", Snackbar.LENGTH_LONG).show()
                })
                setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}