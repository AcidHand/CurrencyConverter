package com.acidhand.currencyconverter.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("StateFlowValueCalledInComposition")
@Composable
fun <T : R, R> StateFlow<T>.collectAsStateWithLifecycle(): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val flowLifecycleAware = remember(this, lifecycleOwner) {
        flowWithLifecycle(lifecycleOwner.lifecycle)
    }
    return flowLifecycleAware.collectAsState(initial = value)
}

operator fun <T : Any> MutableStateFlow<T>.provideDelegate(
    thisRef: Any?,
    property: KProperty<*>,
): ReadWriteProperty<Any?, T> {
    return object : ReadWriteProperty<Any?, T> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return this@provideDelegate.value
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this@provideDelegate.value = value
        }
    }
}