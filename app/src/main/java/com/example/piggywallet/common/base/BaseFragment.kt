package com.example.piggywallet.common.base

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import java.util.*

open class BaseFragment : Fragment() {
    var progressDialog: ProgressDialog? = null

    companion object {
        const val LOADING_DIALOG_TAG_FRAGMENT = "BaseLoadingScreenDialogFragment"
    }
}