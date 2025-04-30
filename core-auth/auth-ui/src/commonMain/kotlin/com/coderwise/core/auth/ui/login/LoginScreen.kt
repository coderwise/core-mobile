package com.coderwise.core.auth.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.coderwise.core.ui.component.CorePasswordField
import com.coderwise.core.ui.component.CoreProgressButton
import com.coderwise.core.ui.component.CoreTextField
import com.coderwise.core.ui.component.ProgressButtonState
import com.coderwise.core.ui.utils.ScreenPreview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    dispatch: (LoginAction) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        CoreTextField(
            value = uiState.userName,
            onValueChange = { dispatch(LoginAction.UserNameUpdated(it)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = "Username",
            enabled = !uiState.isLoading,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        CorePasswordField(
            value = uiState.password,
            onValueChange = { dispatch(LoginAction.PasswordUpdated(it)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = "Password",
            enabled = !uiState.isLoading,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        // TODO make to a component
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.rememberMe,
                onCheckedChange = { dispatch(LoginAction.RememberMeUpdated(it)) },
                modifier = Modifier,
                enabled = !uiState.isLoading
            )
            Text(
                text = "Remember me",
                modifier = Modifier.clickable {
                    if (!uiState.isLoading) {
                        dispatch(LoginAction.RememberMeUpdated(!uiState.rememberMe))
                    }
                }
            )
        }
        CoreProgressButton(
            text = "Login",
            state = uiState.loginButtonState,
            onClick = { dispatch(LoginAction.LoginButtonClicked) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = { dispatch(LoginAction.RegisterButtonClicked) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text("Register")
        }
        TextButton(
            onClick = { dispatch(LoginAction.ForgotPasswordButtonClicked) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text("Forgot Password")
        }
    }
}

@Preview
@Composable
private fun LoginPreview() {
    ScreenPreview {
        LoginContent(
            uiState = LoginUiState(
                userName = "username",
                password = "password",
                rememberMe = true,
                loginButtonState = ProgressButtonState.Button,
                isLoading = false
            )
        ) {}
    }
}

@Preview
@Composable
private fun LoginProgressPreview() {
    ScreenPreview {
        LoginContent(
            uiState = LoginUiState(
                userName = "username",
                password = "password",
                rememberMe = true,
                loginButtonState = ProgressButtonState.Progress,
                isLoading = true
            )
        ) {}
    }
}