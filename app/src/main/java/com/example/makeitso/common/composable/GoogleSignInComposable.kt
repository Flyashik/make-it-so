package com.example.makeitso.common.composable

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.makeitso.theme.Shapes
import com.example.makeitso.R
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState

@ExperimentalMaterialApi
@Composable
fun GoogleSignInButton(@StringRes text: Int, @StringRes loadingText: Int, action: (token: String) -> Unit) {
    var clicked by remember { mutableStateOf(false) }
    val state = rememberOneTapSignInState()

    OneTapSignInWithGoogle(
        state = state,
        clientId = stringResource(id = R.string.client_id),
        onTokenIdReceived = { action(it) },
        onDialogDismissed = { Log.d("[Google Auth]", it) }
    )

    Surface(
        onClick = { clicked = !clicked; state.open() },
        shape = Shapes.medium,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = MaterialTheme.colors.surface
    ) {
        Row(
           modifier = Modifier
               .padding(
                   start = 12.dp,
                   end = 16.dp,
                   top = 12.dp,
                   bottom = 12.dp
               )
               .animateContentSize(
                   animationSpec = tween(
                       durationMillis = 300,
                       easing = LinearOutSlowInEasing
                   )
               ),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement =  Arrangement.Center,
        ) {
           Icon(
               painter = painterResource(id = R.drawable.ic_google_logo),
               contentDescription = "Google Sign Up Button",
               tint = Color.Unspecified
           )
           Spacer(modifier = Modifier.width(8.dp))
           Text(text = if(!clicked) stringResource(text) else stringResource(loadingText))
           if (clicked) {
               Spacer(modifier = Modifier.width(16.dp))
               CircularProgressIndicator(
                   modifier = Modifier
                       .width(16.dp)
                       .height(16.dp),
                   strokeWidth = 2.dp,
                   color = MaterialTheme.colors.primary
               )
           }
        }
    }
}