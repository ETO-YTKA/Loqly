package com.example.loqly.ui.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.loqly.R
import com.example.loqly.ui.components.CustomMediumButton
import com.example.loqly.ui.components.CustomTextField
import com.example.loqly.ui.theme.Dimen
import com.example.loqly.ui.theme.LoqlyTheme

@Composable
fun SignUpScreen(
    popBackStack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    SignUpContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        popBackStack = popBackStack,
        onSuccess = onSuccess
    )
}

@Composable
private fun SignUpContent(
    uiState: SignUpUiState,
    onAction: (SignUpAction) -> Unit,
    popBackStack: () -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimen.Screen.horizontalPadding),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeroSection()

            SignUpForm(
                uiState = uiState,
                onAction = onAction,
                onSuccess = onSuccess
            )

            //nav back to login
            TextButton(
                onClick = popBackStack
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.log_in),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun HeroSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.make_your_little_corner_of_chat),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SignUpForm(
    uiState: SignUpUiState,
    onAction: (SignUpAction) -> Unit,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        CustomTextField(
            value = uiState.username,
            onValueChange = { onAction(SignUpAction.UpdateUsername(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.username)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_account_circle),
                    contentDescription = null
                )
            },
            isError = uiState.usernameError != null,
            supportingText = uiState.usernameError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { onAction(SignUpAction.UpdateEmail(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = null
                )
            },
            isError = uiState.emailError != null,
            supportingText = uiState.emailError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.password,
            onValueChange = { onAction(SignUpAction.UpdatePassword(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = null
                )
            },
            isError = uiState.passwordError != null,
            supportingText = uiState.passwordError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.confirmPassword,
            onValueChange = { onAction(SignUpAction.UpdateConfirmPassword(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.confirm_password)) },
            isError = uiState.confirmPasswordError != null,
            supportingText = uiState.confirmPasswordError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomMediumButton(
            onClick = { onAction(SignUpAction.Submit(onSuccess = onSuccess)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text(
                text = stringResource(R.string.create_account),
                fontSize = Dimen.FontSize.mediumButton
            )
        }

        uiState.generalError?.let { message ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SignUpScreenPreview() {
    LoqlyTheme {
        SignUpContent(
            uiState = SignUpUiState(),
            onAction = {},
            popBackStack = {},
            onSuccess = {}
        )
    }
}
