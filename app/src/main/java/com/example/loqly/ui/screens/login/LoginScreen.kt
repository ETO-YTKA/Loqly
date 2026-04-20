package com.example.loqly.ui.screens.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.loqly.R
import com.example.loqly.ui.components.CustomMediumButton
import com.example.loqly.ui.components.CustomTextField
import com.example.loqly.ui.theme.Dimen
import com.example.loqly.ui.theme.LoqlyTheme

@Composable
fun LoginScreen(
    navigateToChats: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginContent(
        uiState = uiState,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        authenticate = { viewModel.authenticate(onSuccess = navigateToChats) },
        togglePasswordVisibility = viewModel::togglePasswordVisibility,
        navigateToSignUp = navigateToSignUp,
        navigateToForgotPassword = navigateToForgotPassword
    )
}

@Composable
private fun LoginContent(
    uiState: LoginUiState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    authenticate: () -> Unit,
    togglePasswordVisibility: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToForgotPassword: () -> Unit,
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                WelcomeHeader()

                LoginForm(
                    uiState = uiState,
                    updateEmail = updateEmail,
                    updatePassword = updatePassword,
                    authenticate = authenticate,
                    togglePasswordVisibility = togglePasswordVisibility,
                    navigateToForgotPassword = navigateToForgotPassword
                )
            }

            //nav to sign up
            TextButton(
                onClick = navigateToSignUp
            ) {
                Text(
                    text = stringResource(R.string.new_here),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.create_an_account),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun WelcomeHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.drop_in_and_say_hi),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun LoginForm(
    uiState: LoginUiState,
    authenticate: () -> Unit,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    togglePasswordVisibility: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            value = uiState.email,
            onValueChange = updateEmail,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = null
                )
            }
        )

        //password and forgot password
        Column(horizontalAlignment = Alignment.End) {
            CustomTextField(
                value = uiState.password,
                onValueChange = updatePassword,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.password)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = togglePasswordVisibility
                    ) {
                        AnimatedContent(targetState = uiState.isPasswordVisible) { isPasswordVisible ->
                            if (isPasswordVisible) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_visibility_off),
                                    contentDescription = stringResource(R.string.hide_password)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_visibility),
                                    contentDescription = stringResource(R.string.show_password)
                                )
                            }
                        }
                    }
                },
                visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            TextButton(
                onClick = navigateToForgotPassword
            ) {
                Text(text = stringResource(R.string.forgot_password))
            }
        }

        CustomMediumButton(
            onClick = authenticate,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.log_in),
                fontSize = Dimen.FontSize.mediumButton
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LoginContentPreview() {
    LoqlyTheme {
        LoginContent(
            uiState = LoginUiState(),
            updateEmail = {},
            updatePassword = {},
            authenticate = {},
            togglePasswordVisibility = {},
            navigateToSignUp = {},
            navigateToForgotPassword = {}
        )
    }
}
