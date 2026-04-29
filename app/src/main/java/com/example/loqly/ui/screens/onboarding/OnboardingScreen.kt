package com.example.loqly.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.loqly.R
import com.example.loqly.ui.components.CustomMediumButton
import com.example.loqly.ui.components.CustomTextField
import com.example.loqly.ui.theme.Dimen
import com.example.loqly.ui.theme.LoqlyTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    OnboardingContent(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun OnboardingContent(
    uiState: OnboardingUiState,
    onAction: (OnboardingActions) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar()
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimen.Screen.horizontalPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            HeroSection()

            Spacer(modifier = Modifier.height(24.dp))

            AvatarPicker()

            Spacer(modifier = Modifier.height(24.dp))

            OnboardingForm(
                uiState = uiState,
                onAction = onAction,
            )
        }
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = "Almost done",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier,
    )
}

@Composable
private fun HeroSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Let friends know it's you.",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add the details your closest people will recognize",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun AvatarPicker(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .clickable(onClick = { /* TODO: add photo */ })
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_face_3),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(75.dp)
                    )
                    Text(
                        text = "Add photo",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            FilledIconButton(
                onClick = { /* TODO: add photo */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_a_photo),
                    contentDescription = "Add photo",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Text(
            text = "Optional. You can add one later.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun OnboardingForm(
    uiState: OnboardingUiState,
    onAction: (OnboardingActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            value = uiState.firstName,
            onValueChange = { onAction(OnboardingActions.UpdateFirstName(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "First name") },
            isError = uiState.firstNameError != null,
            supportingText = uiState.firstNameError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        CustomTextField(
            value = uiState.lastName,
            onValueChange = { onAction(OnboardingActions.UpdateLastName(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Last name (optional)") },
            isError = uiState.lastNameError != null,
            supportingText = uiState.lastNameError?.let { message ->
                {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        BirthDateField(
            birthDate = uiState.birthDate,
            birthDateError = uiState.birthDateError,
            onBirthDateSelected = { onAction(OnboardingActions.UpdateBirthDate(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(0.dp))

        CustomMediumButton(
            onClick = { onAction(OnboardingActions.Submit) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Complete",
                fontSize = Dimen.FontSize.mediumButton
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDateField(
    birthDate: LocalDate?,
    birthDateError: String?,
    onBirthDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            showDatePicker = true
        }
    }

    CustomTextField(
        value = birthDate?.formatBirthDate().orEmpty(),
        onValueChange = {},
        modifier = modifier,
        readOnly = true,
        interactionSource = interactionSource,
        label = { Text(text = "Birth date") },
        isError = birthDateError != null,
        supportingText = birthDateError?.let { message ->
            {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = birthDate?.let(::birthDateToPickerMillis),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    isSelectableBirthDate(utcTimeMillis)

                override fun isSelectableYear(year: Int): Boolean = year <= LocalDate.now().year
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis
                            ?.let(::pickerMillisToBirthDate)
                            ?.let(onBirthDateSelected)
                        showDatePicker = false
                    }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

internal fun birthDateToPickerMillis(date: LocalDate): Long =
    date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

internal fun pickerMillisToBirthDate(utcTimeMillis: Long): LocalDate =
    Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).toLocalDate()

internal fun isSelectableBirthDate(
    utcTimeMillis: Long,
    today: LocalDate = LocalDate.now(),
): Boolean = pickerMillisToBirthDate(utcTimeMillis).isBefore(today)

private fun LocalDate.formatBirthDate(): String =
    format(DateTimeFormatter.ofPattern("d MMM, yyyy", Locale.getDefault()))

@PreviewLightDark
@Composable
private fun OnboardingContentPreview() {
    LoqlyTheme {
        OnboardingContent(
            uiState = OnboardingUiState(),
            onAction = {},
        )
    }
}
