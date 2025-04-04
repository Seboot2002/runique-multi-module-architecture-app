package com.sebascamayo.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sebascamayo.core.presentation.designsystem.MyOwnRuniqueTheme

@Composable
fun RuniqueScaffold(
    modifier: Modifier = Modifier,
    withGradient: Boolean = true,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) { paddingValues ->
        if(withGradient) {
            GradientBackground {
                content(paddingValues)
            }
        } else {
            content(paddingValues)
        }
    }
}

@Preview
@Composable
private fun RuniqueScaffoldPreview() {
    MyOwnRuniqueTheme {
        RuniqueScaffold(
            modifier = TODO(),
            withGradient = TODO(),
            topAppBar = TODO(),
            floatingActionButton = TODO(),
            content = TODO()
        )
    }
}