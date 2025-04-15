package com.coderwise.core.auth.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coderwise.core.auth.auth_ui.generated.resources.Res
import com.coderwise.core.auth.auth_ui.generated.resources.login_register
import com.coderwise.core.ui.component.CorePasswordField
import com.coderwise.core.ui.component.CoreProgressButton
import com.coderwise.core.ui.component.CoreTextField
import com.coderwise.core.ui.component.ProgressButtonState
import com.coderwise.core.ui.utils.ScreenPreview
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterContent(
        uiState = uiState,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun RegisterContent(
    uiState: RegisterUiState,
    dispatch: (RegisterAction) -> Unit
) {
    Column {
        CoreTextField(
            value = uiState.userName,
            onValueChange = { dispatch(RegisterAction.UserNameUpdated(it)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = "Username",
            enabled = !uiState.isLoading
        )
        CorePasswordField(
            value = uiState.password,
            onValueChange = { dispatch(RegisterAction.PasswordUpdated(it)) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            label = "Password",
            enabled = !uiState.isLoading
        )
        CoreProgressButton(
            text = "Register",
            state = uiState.registerButtonState,
            onClick = { dispatch(RegisterAction.RegisterButtonClicked) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    ScreenPreview {
        RegisterContent(
            uiState = RegisterUiState(
                userName = "username",
                password = "password",
                registerButtonState = ProgressButtonState.Button,
                isLoading = false
            )
        ) {}
    }
}