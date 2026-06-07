package com.example.wearworldtime.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Modifier.rotaryHandler(
    scrollState: ScrollableState,
    focusRequester: FocusRequester = remember { FocusRequester() }
): Modifier {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Request focus automatically when composed so that the rotary inputs work immediately
        try {
            focusRequester.requestFocus()
        } catch (e: Exception) {
            // Safe fallback if focus is not ready
        }
    }

    return this
        .focusRequester(focusRequester)
        .focusable()
        .onRotaryScrollEvent {
            coroutineScope.launch {
                // Adjust factor to make virtual bezel rotation feel incredibly organic and smooth
                scrollState.scrollBy(it.verticalScrollPixels * 1.5f)
            }
            true
        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Modifier.rotaryValueHandler(
    onValueChange: (Float) -> Unit,
    focusRequester: FocusRequester = remember { FocusRequester() }
): Modifier {
    LaunchedEffect(Unit) {
        try {
            focusRequester.requestFocus()
        } catch (e: Exception) {
            // Safe fallback
        }
    }

    return this
        .focusRequester(focusRequester)
        .focusable()
        .onRotaryScrollEvent {
            onValueChange(it.verticalScrollPixels)
            true
        }
}
