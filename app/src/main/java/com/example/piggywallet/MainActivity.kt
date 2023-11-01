package com.example.piggywallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.piggywallet.module.main.AnalyticFragment
import com.example.piggywallet.module.main.BookMainFragment
import com.example.piggywallet.module.main.SettingFragment
import com.example.piggywallet.module.main.WalletFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInstances()


    }

    private fun  initInstances(){

        supportFragmentManager.beginTransaction()
            .add(R.id.container, BookMainFragment()).commit()

        tabLayout.addOnTabSelectedListener(object :
            com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when {
                    tab!!.position == 0 -> { supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            BookMainFragment()
                        ).commit() }
                    tab.position == 1 -> { supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            WalletFragment()
                        ).commit() }
                    tab.position == 2 -> { supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            AnalyticFragment()
                        ).commit() }
                    else -> { supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            SettingFragment ()
                        ).commit() }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                return
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                return
            }

        })



    }


}