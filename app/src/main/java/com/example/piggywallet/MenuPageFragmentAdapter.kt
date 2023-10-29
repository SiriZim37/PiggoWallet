package com.example.piggywallet

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.piggywallet.module.main.AnalyticFragment
import com.example.piggywallet.module.main.BookFragment
import com.example.piggywallet.module.main.SettingFragment
import com.example.piggywallet.module.main.WalletFragment

class MenuPageFragmentAdapter(
    fragmentManager: FragmentManager ,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager ,lifecycle){
    override fun getItemCount(): Int {
       return 4
    }

    override fun createFragment(position: Int): Fragment {
        return if ( position == 0 )
            BookFragment()
        else if ( position == 2 )
            WalletFragment()
        else if ( position == 3 )
            AnalyticFragment()
        else
            SettingFragment()
    }
}