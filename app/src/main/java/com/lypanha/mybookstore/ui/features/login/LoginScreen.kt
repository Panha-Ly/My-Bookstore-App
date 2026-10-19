package com.lypanha.mybookstore.ui.features.login

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lypanha.mybookstore.R
import com.lypanha.mybookstore.Utils
import com.lypanha.mybookstore.model.User
import com.lypanha.mybookstore.ui.components.BookstoreTopAppBar
import com.lypanha.mybookstore.ui.theme.MyBookstoreTheme
import com.lypanha.mybookstore.ui.theme.accentColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val scope = rememberCoroutineScope()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var loginErrorCount by rememberSaveable { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val emailTextFieldState = rememberTextFieldState()
    val passwordTextFieldState = rememberTextFieldState()

    LoginContent(
        loginErrorCount = loginErrorCount,
        emailTextFieldState = emailTextFieldState,
        passwordTextFieldState = passwordTextFieldState,
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        onLoginClick = {
            scope.launch {
                if (emailTextFieldState.text.isNotEmpty() && passwordTextFieldState.text.isNotEmpty()) {
                    isLoading = true
                    val user = Utils.users.find { user ->
                        user.email.contentEquals(emailTextFieldState.text) &&
                                user.password.contentEquals(passwordTextFieldState.text)
                    }
                    delay(2.seconds)
                    if (user != null) {
                        Utils.setLoginStatus(user.id, User.LOGGED_IN)
                        backDispatcher?.onBackPressed()
                    } else {
                        isLoading = false
                        emailTextFieldState.clearText()
                        passwordTextFieldState.clearText()
                        loginErrorCount++ // trigger focus on email TextField
                        scope.launch {
                            snackbarHostState.showSnackbar("Incorrect email or password")
                        }
                    }
                }
            }
        },
        isLoading = isLoading,
        onBackClick = { backDispatcher?.onBackPressed() },
    )
}

@Composable
private fun LoginContent(
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    loginErrorCount: Int,
    onLoginClick: () -> Unit,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDirty by rememberSaveable { mutableStateOf(false) }

    Box(modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BookstoreTopAppBar(
                    title = "",
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = "Navigate Up"
                            )
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(
                    snackbarHostState,
                    Modifier
                        .consumeWindowInsets(WindowInsets.navigationBars) // 1. Consume the nav bar inset so the system knows it's already accounted for
                        .imePadding() // 2. Apply IME padding (it will now subtract the nav bar height automatically)
                )
            }
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            ) {
                LoginCard(
                    loginErrorCount = loginErrorCount,
                    isDirty = isDirty,
                    emailTextFieldState = emailTextFieldState,
                    passwordTextFieldState = passwordTextFieldState,
                    onLoginClick = {
                        isDirty = true
                        onLoginClick()
                    }
                )
            }
        }
        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {} // block touch input from the Login Card behind the Loading Box
                    .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = .5f))
            ) {
                CircularProgressIndicator(color = accentColor)
            }
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LoginCard(
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    onLoginClick: () -> Unit,
    isDirty: Boolean,
    loginErrorCount: Int,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val isKeyboardOpen = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var showPassword by rememberSaveable { mutableStateOf(false) }

    val textFieldAccentColors = OutlinedTextFieldDefaults.colors(
        cursorColor = accentColor,
        selectionColors = TextSelectionColors(
            handleColor = accentColor,
            backgroundColor = accentColor.copy(alpha = .2f)
        ),
        focusedLabelColor = accentColor,
        focusedBorderColor = accentColor,
        focusedTrailingIconColor = accentColor,
        focusedContainerColor = accentColor.copy(alpha = .2f),
    )

    LaunchedEffect(loginErrorCount) {
        if (loginErrorCount > 0) {
            scope.launch {
                // requestFocus() will try to open the keyboard, if it is not open before.
                // the if statement below make sure it keep being close, if the keyboard was closed.
                focusRequester.requestFocus()
                delay(50.milliseconds)
                if (!isKeyboardOpen) {
                    // In case of the keyboard is closed before log in button is clicked,
                    // close the keyboard to keep it in closed state
                    // when it is open by requestFocus().
                    keyboardController?.hide()
                }
            }
        }
    }

    Card(
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceContainerHighest),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
            .widthIn(max = 600.dp)
            .imePadding()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.text_log_in),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            )
            OutlinedTextField(
                state = emailTextFieldState,
                label = { Text(stringResource(R.string.prompt_email)) },
                colors = textFieldAccentColors,
                lineLimits = TextFieldLineLimits.SingleLine,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                isError = isDirty && emailTextFieldState.text.isEmpty(),
                supportingText = {
                    if (isDirty && emailTextFieldState.text.isEmpty()) {
                        TextFieldErrorLayout("Enter Email")
                    }
                }
            )
            OutlinedSecureTextField(
                state = passwordTextFieldState,
                textObfuscationMode = if (showPassword) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped // Or TextObfuscationMode.Hidden
                },
                label = { Text(stringResource(R.string.prompt_password)) },
                colors = textFieldAccentColors,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            painter = painterResource(
                                if (showPassword) R.drawable.visibility_off_24px
                                else R.drawable.visibility_24px
                            ),
                            contentDescription = if (showPassword) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                isError = isDirty && passwordTextFieldState.text.isEmpty(),
                supportingText = {
                    if (isDirty && passwordTextFieldState.text.isEmpty()) {
                        TextFieldErrorLayout("Enter Password")
                    }
                }
            )
            Button(
                onClick = onLoginClick,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = accentColor
                ),
            ) { Text(stringResource(R.string.text_log_in)) }
        }

    }
}

// Reusable micro-layout component for error supporting text
@Composable
private fun TextFieldErrorLayout(message: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error_24px),
            contentDescription = "Alert",
            modifier = Modifier.size(14.dp)
        )
        Text(text = message)
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginContentPreview() {
    MyBookstoreTheme {
        LoginContent(
            loginErrorCount = 0,
            isLoading = false,
            emailTextFieldState = rememberTextFieldState(),
            passwordTextFieldState = rememberTextFieldState(),
            snackbarHostState = SnackbarHostState(),
            onLoginClick = {},
            onBackClick = {}
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun LoginCardPreview() {
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()
    MyBookstoreTheme {
        LoginCard(
            loginErrorCount = 0,
            isDirty = false,
            emailTextFieldState = email,
            passwordTextFieldState = password,
            onLoginClick = {},
        )
    }
}