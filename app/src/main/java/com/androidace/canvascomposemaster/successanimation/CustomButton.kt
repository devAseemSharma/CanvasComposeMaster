package com.androidace.canvascomposemaster.successanimation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

private const val debounceClick = 500L

@Composable
fun CustomButton(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    endIconResId: Int? = null,
    startIconResId: Int? = null,
    tagName: String = "tag_continueButton",
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    colors: ButtonColors = ButtonColors(
        containerColor = Color(0xFFFF4F58),
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.outline,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    onClick: () -> Unit
) {
    val singleClickState = rememberSingleClickState { onClick() }
    Button(
        onClick = { singleClickState.onClick() },
        modifier = modifier
            .then(
                Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ),

        shape = shape,
        enabled = enabled,
        colors = colors
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            startIconResId?.let {
                Icon(
                    painter = painterResource(id = startIconResId),
                    contentDescription = "Copy Icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = stringResource(id = title),
                style = textStyle,
                color = MaterialTheme.colorScheme.onPrimary
            )
            endIconResId?.let {
                Icon(
                    painter = painterResource(id = endIconResId),
                    contentDescription = "Copy Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

    }
}

@Composable
fun rememberSingleClickState(click: () -> Unit): SingleClickState {
    val scope = rememberCoroutineScope()
    val clickItem by rememberUpdatedState(newValue = click)
    return remember(Unit) {
        SingleClickState(scope = scope, singleClick = clickItem)
    }
}

class SingleClickState(
    private val singleClick: () -> Unit,
    private val isClickEnabled: AtomicBoolean = AtomicBoolean(true),
    private val scope: CoroutineScope
) {

    private val lock :Any = Any()

    private fun enableClick() {
        isClickEnabled.set(true)
    }

    fun onClick(): Unit {
        synchronized(lock) {
            if (isClickEnabled.getAndSet(false)) {
                singleClick()
                scope.launch {
                    delay(debounceClick)
                    enableClick()
                }
            }
        }
    }
}