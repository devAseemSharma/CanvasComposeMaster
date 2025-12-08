package com.androidace.canvascomposemaster.successanimation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidace.canvascomposemaster.R

@Composable
fun SuccessScreen(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(modifier = Modifier.weight(1f)) {
                    CustomButton(
                        title = R.string.confirm,
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = true,
                        shape = RoundedCornerShape(8.dp),
                        textStyle = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4F58)),
                        onClick = { }
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Success Icon
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    SuccessAnimationWithBurst(soundResourceId = R.raw.success_sound)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    // Success Title
                    Text(
                        text = stringResource(id = R.string.success),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    // Success Desc
                    SelectionContainer {
                        Text(
                            text = "You have sent R20 to Hello Pay merchant successfully",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}