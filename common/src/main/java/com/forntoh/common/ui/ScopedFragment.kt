package com.forntoh.common.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.forntoh.common.internal.data.Response
import com.forntoh.common.internal.data.ResponseHolder
import com.forntoh.common.utils.getLoadingDialog
import com.forntoh.common.utils.showError
import com.forntoh.common.utils.showSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class ScopedFragment : Fragment(), CoroutineScope, LifecycleObserver {

    private lateinit var job: Job

    protected abstract val viewModel: BaseViewModel

    protected val navController: NavController by lazy { NavHostFragment.findNavController(this) }

    val dialog by lazy { requireActivity().getLoadingDialog() }

    val root: View by lazy { requireActivity().findViewById(android.R.id.content) }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setHasOptionsMenu(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        dialog.dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.removeObserver(this)
    }

    override fun onPause() {
        super.onPause()
        ResponseHolder.response.postValue(Response.clear())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ResponseHolder.response.observe(viewLifecycleOwner, {
            it?.let { response ->
                if (response.type != Response.ResponseType.PENDING)
                    response.message?.let { it1 -> root.showSnackBar(getString(it1)) }
                when (response.type) {
                    Response.ResponseType.PENDING -> dialog.show()
                    else -> dialog.hide()
                }
                if (response.goBack)
                    navController.navigateUp()
            }
        })
        viewModel.inputError.observe(viewLifecycleOwner, {
            if (it == null) return@observe

            val v = root.findViewById<View>(it.view)
            val message = it.message?.joinToString(separator = " ") { m ->
                var text = ""
                if (m is String) text = m
                else if (m is Int) text = getString(m)
                text
            }
            v.showError(message, it.shouldFocus)
        })
    }
}
