package nl.recall.generic.di

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectWhenCreated(lifecycleOwner: LifecycleOwner, collector: suspend (T) -> Unit): Job =
    collectWhen(Lifecycle.State.CREATED, lifecycleOwner, collector)

fun <T> Flow<T>.collectWhen(
    state: Lifecycle.State,
    lifecycleOwner: LifecycleOwner,
    collector: suspend (T) -> Unit,
): Job = lifecycleOwner.lifecycle.coroutineScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) { collect(collector) }
}