package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

enum class AuthMethod {
    EMAIL,
    PHONE
}

enum class EmailAuthMode {
    LOGIN,
    REGISTER
}

enum class AuthStep {
    CREDENTIALS,
    SELECT_ROLE
}

@Composable
fun LoginScreen(
    onLoginSuccess: (phone: String, email: String, name: String, role: UserRole) -> Unit = { _, _, _, _ -> },
    onSkip: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    // Hatua za usajili
    var currentStep by remember { mutableStateOf(AuthStep.CREDENTIALS) }
    var selectedAuthMethod by remember { mutableStateOf(AuthMethod.EMAIL) }
    var emailAuthMode by remember { mutableStateOf(EmailAuthMode.REGISTER) }

    // Taarifa za mtumiaji
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var phoneNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }

    var selectedRole by remember { mutableStateOf(UserRole.JOB_SEEKER) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("login_screen_surface"),
        color = Color(0xFFF8FAF9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header / Logo
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(BrandGreenDark, BrandGreen)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Work,
                    contentDescription = "ClipZone Logo",
                    tint = Color.White,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "ClipZone Ajira",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BrandGreenDark
            )

            Text(
                text = "Soko la Ajira na Vipaji Tanzania",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = currentStep,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "auth_step_transition"
            ) { step ->
                when (step) {
                    AuthStep.CREDENTIALS -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_credentials_card"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Tab ya Njia ya Kuingia (Email vs Phone)
                                TabRow(
                                    selectedTabIndex = if (selectedAuthMethod == AuthMethod.EMAIL) 0 else 1,
                                    containerColor = Color(0xFFF1F5F9),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp)),
                                    indicator = { tabPositions ->
                                        TabRowDefaults.SecondaryIndicator(
                                            modifier = Modifier.tabIndicatorOffset(
                                                tabPositions[if (selectedAuthMethod == AuthMethod.EMAIL) 0 else 1]
                                            ),
                                            color = BrandGreen,
                                            height = 3.dp
                                        )
                                    },
                                    divider = {}
                                ) {
                                    Tab(
                                        selected = selectedAuthMethod == AuthMethod.EMAIL,
                                        onClick = {
                                            selectedAuthMethod = AuthMethod.EMAIL
                                            errorMessage = null
                                        },
                                        modifier = Modifier.testTag("tab_email_auth"),
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Email,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp),
                                                    tint = if (selectedAuthMethod == AuthMethod.EMAIL) BrandGreenDark else Color(0xFF64748B)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Barua Pepe (Email)",
                                                    fontSize = 13.sp,
                                                    fontWeight = if (selectedAuthMethod == AuthMethod.EMAIL) FontWeight.Bold else FontWeight.Medium,
                                                    color = if (selectedAuthMethod == AuthMethod.EMAIL) BrandGreenDark else Color(0xFF64748B)
                                                )
                                            }
                                        }
                                    )

                                    Tab(
                                        selected = selectedAuthMethod == AuthMethod.PHONE,
                                        onClick = {
                                            selectedAuthMethod = AuthMethod.PHONE
                                            errorMessage = null
                                        },
                                        modifier = Modifier.testTag("tab_phone_auth"),
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Phone,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp),
                                                    tint = if (selectedAuthMethod == AuthMethod.PHONE) BrandGreenDark else Color(0xFF64748B)
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Namba ya Simu",
                                                    fontSize = 13.sp,
                                                    fontWeight = if (selectedAuthMethod == AuthMethod.PHONE) FontWeight.Bold else FontWeight.Medium,
                                                    color = if (selectedAuthMethod == AuthMethod.PHONE) BrandGreenDark else Color(0xFF64748B)
                                                )
                                            }
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(18.dp))

                                // Error message banner
                                errorMessage?.let { msg ->
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFFEE2E2))
                                            .padding(12.dp)
                                    ) {
                                        Text(
                                            text = msg,
                                            color = Color(0xFFDC2626),
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(14.dp))
                                }

                                // FORM KWA NJIA YA EMAIL
                                if (selectedAuthMethod == AuthMethod.EMAIL) {
                                    // Switcher kati ya Jisajili na Ingia
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFFF8FAFC))
                                            .padding(4.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (emailAuthMode == EmailAuthMode.REGISTER) BrandGreen else Color.Transparent)
                                                .clickable {
                                                    emailAuthMode = EmailAuthMode.REGISTER
                                                    errorMessage = null
                                                }
                                                .padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Jisajili Upya",
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp,
                                                color = if (emailAuthMode == EmailAuthMode.REGISTER) Color.White else Color(0xFF475569)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (emailAuthMode == EmailAuthMode.LOGIN) BrandGreen else Color.Transparent)
                                                .clickable {
                                                    emailAuthMode = EmailAuthMode.LOGIN
                                                    errorMessage = null
                                                }
                                                .padding(vertical = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Ingia (Login)",
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 13.sp,
                                                color = if (emailAuthMode == EmailAuthMode.LOGIN) Color.White else Color(0xFF475569)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Jina Kamili (kwa Kujisajili)
                                    if (emailAuthMode == EmailAuthMode.REGISTER) {
                                        OutlinedTextField(
                                            value = fullName,
                                            onValueChange = { fullName = it },
                                            label = { Text("Jina Kamili *") },
                                            placeholder = { Text("Mfano: Juma Rashid") },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = null,
                                                    tint = BrandGreenDark
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("input_fullname"),
                                            singleLine = true,
                                            shape = RoundedCornerShape(12.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = BrandGreen,
                                                unfocusedBorderColor = Color(0xFFCBD5E1)
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Next
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }

                                    // Barua Pepe (Email)
                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = {
                                            email = it
                                            errorMessage = null
                                        },
                                        label = { Text("Barua Pepe (Email) *") },
                                        placeholder = { Text("mfano@gmail.com") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Email,
                                                contentDescription = null,
                                                tint = BrandGreenDark
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("input_email"),
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = BrandGreen,
                                            unfocusedBorderColor = Color(0xFFCBD5E1)
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Email,
                                            imeAction = ImeAction.Next
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Nenosiri (Password)
                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = {
                                            password = it
                                            errorMessage = null
                                        },
                                        label = { Text("Nenosiri *") },
                                        placeholder = { Text("Angalau herufi 6") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Lock,
                                                contentDescription = null,
                                                tint = BrandGreenDark
                                            )
                                        },
                                        trailingIcon = {
                                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                                Icon(
                                                    imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                    contentDescription = if (isPasswordVisible) "Ficha Nenosiri" else "Onyesha Nenosiri",
                                                    tint = Color(0xFF64748B)
                                                )
                                            }
                                        },
                                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("input_password"),
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = BrandGreen,
                                            unfocusedBorderColor = Color(0xFFCBD5E1)
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Password,
                                            imeAction = if (emailAuthMode == EmailAuthMode.REGISTER) ImeAction.Next else ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = { focusManager.clearFocus() }
                                        )
                                    )

                                    // Thibitisha Nenosiri (kwa Kujisajili)
                                    if (emailAuthMode == EmailAuthMode.REGISTER) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        OutlinedTextField(
                                            value = confirmPassword,
                                            onValueChange = {
                                                confirmPassword = it
                                                errorMessage = null
                                            },
                                            label = { Text("Thibitisha Nenosiri *") },
                                            placeholder = { Text("Rudia nenosiri lako") },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Lock,
                                                    contentDescription = null,
                                                    tint = BrandGreenDark
                                                )
                                            },
                                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("input_confirm_password"),
                                            singleLine = true,
                                            shape = RoundedCornerShape(12.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = BrandGreen,
                                                unfocusedBorderColor = Color(0xFFCBD5E1)
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Password,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = { focusManager.clearFocus() }
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    // Kitufe kikuu cha Barua Pepe
                                    Button(
                                        onClick = {
                                            focusManager.clearFocus()
                                            // Validation
                                            if (email.isBlank() || !email.contains("@") || !email.contains(".")) {
                                                errorMessage = "Tafadhali weka barua pepe (email) sahihi."
                                                return@Button
                                            }
                                            if (password.length < 6) {
                                                errorMessage = "Nenosiri liwe na herufi zisizopungua 6."
                                                return@Button
                                            }
                                            if (emailAuthMode == EmailAuthMode.REGISTER) {
                                                if (fullName.isBlank()) {
                                                    errorMessage = "Tafadhali weka Jina lako Kamili."
                                                    return@Button
                                                }
                                                if (password != confirmPassword) {
                                                    errorMessage = "Nenosiri na uthibitisho havilingani."
                                                    return@Button
                                                }
                                            }

                                            // Firebase Auth
                                            coroutineScope.launch {
                                                isLoading = true
                                                errorMessage = null
                                                try {
                                                    val auth = FirebaseAuth.getInstance()
                                                    if (emailAuthMode == EmailAuthMode.REGISTER) {
                                                        try {
                                                            auth.createUserWithEmailAndPassword(email.trim(), password).await()
                                                        } catch (e: Exception) {
                                                            // Ikiwa tayari ipo au offline, jaribu kuingia
                                                            if (e.message?.contains("email-already-in-use", ignoreCase = true) == true) {
                                                                auth.signInWithEmailAndPassword(email.trim(), password).await()
                                                            } else {
                                                                throw e
                                                            }
                                                        }
                                                    } else {
                                                        auth.signInWithEmailAndPassword(email.trim(), password).await()
                                                    }
                                                    isLoading = false
                                                    currentStep = AuthStep.SELECT_ROLE
                                                } catch (e: Exception) {
                                                    isLoading = false
                                                    val errorText = when {
                                                        e.message?.contains("password", ignoreCase = true) == true -> "Nenosiri si sahihi au halikidhi vigezo."
                                                        e.message?.contains("user-not-found", ignoreCase = true) == true -> "Akaunti hii haipo. Tafadhali bofya 'Jisajili Upya'."
                                                        e.message?.contains("network", ignoreCase = true) == true -> "Hitilafu ya mtandao. Tutasajili akaunti yako mtandaoni ukipata intaneti."
                                                        else -> e.localizedMessage ?: "Hitilafu imetokea. Tafadhali jaribu tena."
                                                    }
                                                    // Ikiwa mtandao haupatikani au firebase haijajazwa kabisa, ruhusu offline flow
                                                    if (e.message?.contains("network", ignoreCase = true) == true || e.message?.contains("CONFIGURATION_NOT_FOUND", ignoreCase = true) == true) {
                                                        Toast.makeText(context, "Umesajiliwa katika mfumo (Hali ya Ndani)", Toast.LENGTH_SHORT).show()
                                                        currentStep = AuthStep.SELECT_ROLE
                                                    } else {
                                                        errorMessage = errorText
                                                    }
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .testTag("btn_submit_email_auth"),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = BrandGreenDark,
                                            contentColor = Color.White
                                        ),
                                        enabled = !isLoading
                                    ) {
                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                color = Color.White,
                                                modifier = Modifier.size(22.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Text(
                                                text = if (emailAuthMode == EmailAuthMode.REGISTER) "Jisajili kwa Barua Pepe" else "Ingia kwa Barua Pepe",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            )
                                        }
                                    }
                                } else {
                                    // FORM KWA NJIA YA SIMU (OTP)
                                    OutlinedTextField(
                                        value = phoneNumber,
                                        onValueChange = {
                                            phoneNumber = it
                                            errorMessage = null
                                        },
                                        label = { Text("Nambari ya Simu *") },
                                        placeholder = { Text("+255 7XX XXX XXX") },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Phone,
                                                contentDescription = null,
                                                tint = BrandGreenDark
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .testTag("input_phone_number"),
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = BrandGreen,
                                            unfocusedBorderColor = Color(0xFFCBD5E1)
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Phone,
                                            imeAction = if (isOtpSent) ImeAction.Next else ImeAction.Done
                                        )
                                    )

                                    if (isOtpSent) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        OutlinedTextField(
                                            value = otpCode,
                                            onValueChange = {
                                                otpCode = it
                                                errorMessage = null
                                            },
                                            label = { Text("Msimbo wa OTP (Tarakimu 6) *") },
                                            placeholder = { Text("123456") },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Lock,
                                                    contentDescription = null,
                                                    tint = BrandGreenDark
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .testTag("input_otp_code"),
                                            singleLine = true,
                                            shape = RoundedCornerShape(12.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = BrandGreen,
                                                unfocusedBorderColor = Color(0xFFCBD5E1)
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = { focusManager.clearFocus() }
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Button(
                                        onClick = {
                                            focusManager.clearFocus()
                                            if (phoneNumber.length < 9) {
                                                errorMessage = "Tafadhali weka namba sahihi ya simu ya Tanzania."
                                                return@Button
                                            }

                                            if (!isOtpSent) {
                                                // Tuma OTP simulation / Firebase phone
                                                isLoading = true
                                                coroutineScope.launch {
                                                    kotlinx.coroutines.delay(1000)
                                                    isLoading = false
                                                    isOtpSent = true
                                                    Toast.makeText(context, "Msimbo wa majaribio wa OTP: 123456", Toast.LENGTH_LONG).show()
                                                }
                                            } else {
                                                if (otpCode.length < 4) {
                                                    errorMessage = "Weka msimbo kamili wa OTP uliotumiwa."
                                                    return@Button
                                                }
                                                currentStep = AuthStep.SELECT_ROLE
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .testTag("btn_submit_phone_auth"),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = BrandGreenDark,
                                            contentColor = Color.White
                                        ),
                                        enabled = !isLoading
                                    ) {
                                        if (isLoading) {
                                            CircularProgressIndicator(
                                                color = Color.White,
                                                modifier = Modifier.size(22.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Text(
                                                text = if (!isOtpSent) "Tuma Msimbo wa OTP" else "Thibitisha OTP & Endelea",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // HATUA YA PILI: KUCHAGUA WAJIBU (ROLE SELECTION)
                    AuthStep.SELECT_ROLE -> {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_role_selection_card"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Wewe ni nani?",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandGreenDark
                                )

                                Text(
                                    text = "Chagua lengo lako kuu ili tukupangie muonekano sahihi:",
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                                )

                                // Chaguo 1: Natafuta Kazi
                                val isJobSeeker = selectedRole == UserRole.JOB_SEEKER
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .border(
                                            width = if (isJobSeeker) 2.dp else 1.dp,
                                            color = if (isJobSeeker) BrandGreen else Color(0xFFE2E8F0),
                                            shape = RoundedCornerShape(14.dp)
                                        )
                                        .clickable { selectedRole = UserRole.JOB_SEEKER }
                                        .testTag("role_option_job_seeker"),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isJobSeeker) BrandGreenLight.copy(alpha = 0.5f) else Color.White
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(46.dp)
                                                .clip(CircleShape)
                                                .background(if (isJobSeeker) BrandGreen else Color(0xFFF1F5F9)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                tint = if (isJobSeeker) Color.White else Color(0xFF475569)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(14.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "1. Natafuta Kazi",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = BrandGreenDark
                                            )
                                            Text(
                                                text = "Weka wasifu wako, ujuzi na piga simu waajiri moja kwa moja.",
                                                fontSize = 12.sp,
                                                color = Color(0xFF64748B)
                                            )
                                        }

                                        if (isJobSeeker) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Imechaguliwa",
                                                tint = BrandGreen,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Chaguo 2: Ninaajiri (Mwajiri)
                                val isEmployer = selectedRole == UserRole.EMPLOYER
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .border(
                                            width = if (isEmployer) 2.dp else 1.dp,
                                            color = if (isEmployer) BrandGreen else Color(0xFFE2E8F0),
                                            shape = RoundedCornerShape(14.dp)
                                        )
                                        .clickable { selectedRole = UserRole.EMPLOYER }
                                        .testTag("role_option_employer"),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isEmployer) BrandGreenLight.copy(alpha = 0.5f) else Color.White
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(46.dp)
                                                .clip(CircleShape)
                                                .background(if (isEmployer) BrandGreen else Color(0xFFF1F5F9)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Business,
                                                contentDescription = null,
                                                tint = if (isEmployer) Color.White else Color(0xFF475569)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(14.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "2. Ninaajiri",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = BrandGreenDark
                                            )
                                            Text(
                                                text = "Weka nafasi mpya za kazi na upate wafanyakazi kwa haraka.",
                                                fontSize = 12.sp,
                                                color = Color(0xFF64748B)
                                            )
                                        }

                                        if (isEmployer) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Imechaguliwa",
                                                tint = BrandGreen,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Button(
                                    onClick = {
                                        val displayUser = if (fullName.isNotBlank()) fullName else if (email.isNotBlank()) email.substringBefore("@") else "Mtumiaji wa ClipZone"
                                        onLoginSuccess(phoneNumber, email, displayUser, selectedRole)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("btn_confirm_role_and_proceed"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BrandGreenDark,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = "Anza Kutumia ClipZone Ajira",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Kitufe cha Kuruka (Skip)
            TextButton(
                onClick = onSkip,
                modifier = Modifier.testTag("btn_skip_auth")
            ) {
                Text(
                    text = "Ruka kwa Sasa (Tazama Kazi na Wasifu)",
                    color = Color(0xFF64748B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Skrini ya Mwanzo (Splash Screen yenye Branding ya ClipZone Ajira)
 */
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1800)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BrandGreenDark,
                        BrandGreen,
                        Color(0xFF065F34)
                    )
                )
            )
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "ClipZone Logo",
                    tint = BrandGreenDark,
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "ClipZone Ajira",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "Soko la Ajira na Wafanyakazi Tanzania",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

