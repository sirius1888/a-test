package com.example.common.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner.lifecycle.addObserver(ViewLifecycleObserver {
                binding = null
            })
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }

        return bindingInflater.invoke(LayoutInflater.from(thisRef.context), null, false).also { this.binding = it }
    }
}

public class ViewLifecycleObserver(private val onDestroyAction: () -> Unit) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroyAction.invoke()
        }
    }
}

public fun <T : ViewBinding> Fragment.viewBinding(bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(this, bindingInflater)
