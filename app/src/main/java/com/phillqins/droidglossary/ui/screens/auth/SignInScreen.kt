package com.phillqins.droidglossary.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phillqins.droidglossary.R
import com.phillqins.droidglossary.utils.CollectOneTimeEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel: SignInViewModel
){
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    CollectOneTimeEvent(viewModel.event) {event->
        when(event){
            is SignInUiEvent.ShowToast -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            SignInUiEvent.NavigateToHome -> {}
        }
    }

    Scaffold(
        content = {innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))

                SignInForm(
                    uiState = uiState,
                    onAction = {
                        viewModel.onAction(it)
                    },
                    closeKeyboard = {
                        keyboardController?.hide()
                    }
                )

            }
        }
    )
}

@Composable
fun SignInForm(
    uiState: SignInUiState,
    onAction: (SignInUiAction) -> Unit,
    closeKeyboard: () -> Unit
){
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = uiState.username,
            onValueChange = {onAction(SignInUiAction.UsernameChanged(it))},
            singleLine = true,
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter Username") },
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = uiState.password,
            singleLine = true,
            onValueChange = {onAction(SignInUiAction.PasswordChanged(it))},
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter Password") },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        onAction(SignInUiAction.TogglePasswordVisibility)
                    }
                ) {
                    Icon(
                        imageVector = if (uiState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .size(50.dp),
            enabled = uiState.username.isNotBlank() && uiState.password.isNotBlank(),
            onClick = {
                onAction(SignInUiAction.Submit)
                closeKeyboard()
            },
            shape = RoundedCornerShape(10.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 4.dp
                )
            } else{
                Text(text = "Sign In")
            }

        }
    }
}


