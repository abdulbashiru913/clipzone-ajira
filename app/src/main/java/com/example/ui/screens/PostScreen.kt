package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium
import com.example.ui.viewmodel.AjiraViewModel
import com.example.ui.viewmodel.PostUiState

/**
 * 5. PostScreen.kt - Fomu Mbili Katika Moja (Tangaza Kazi au Weka Wasifu)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: AjiraViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val postState by viewModel.postState.collectAsState()
    val context = LocalContext.current

    // Chaguo la Fomu: Tangaza Kazi (Mwajiri) au Wasifu Wangu (Mtafuta Kazi)
    var selectedFormType by remember {
        mutableStateOf(
            if (currentUser.role == UserRole.EMPLOYER) FormType.JOB else FormType.PROFILE
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(postState) {
        when (postState) {
            is PostUiState.Success -> {
                val msg = (postState as PostUiState.Success).message
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                viewModel.resetPostState()
                onSuccess()
            }
            is PostUiState.Error -> {
                val err = (postState as PostUiState.Error).error
                snackbarHostState.showSnackbar(err)
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedFormType == FormType.JOB) "Tangaza Kazi Mpya" else "Weka Wasifu Wako",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Rudi",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandGreen
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Switcher Tab: "1. Tangazo la Kazi" | "2. Wasifu Wangu"
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp)),
                color = Color(0xFFE2E8F0)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .height(44.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isJob = selectedFormType == FormType.JOB
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isJob) BrandGreen else Color.Transparent)
                            .clickable { selectedFormType = FormType.JOB }
                            .padding(vertical = 8.dp)
                            .testTag("post_tab_job"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = null,
                                tint = if (isJob) Color.White else NeutralDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Tangaza Kazi",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isJob) Color.White else NeutralDark
                            )
                        }
                    }

                    val isProfile = selectedFormType == FormType.PROFILE
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isProfile) BrandGreen else Color.Transparent)
                            .clickable { selectedFormType = FormType.PROFILE }
                            .padding(vertical = 8.dp)
                            .testTag("post_tab_profile"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = if (isProfile) Color.White else NeutralDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Weka Wasifu",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isProfile) Color.White else NeutralDark
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fomu inayofaa
            if (selectedFormType == FormType.JOB) {
                JobPostForm(
                    defaultPostedBy = currentUser.fullName,
                    defaultPhone = currentUser.phoneNumber,
                    defaultEmail = currentUser.email,
                    defaultLocation = currentUser.location,
                    isLoading = postState is PostUiState.Loading,
                    onSubmit = { title, company, salary, loc, type, cat, desc, req, postedBy, phone, email ->
                        viewModel.submitJob(title, company, salary, loc, type, cat, desc, req, postedBy, phone, email)
                    }
                )
            } else {
                ProfilePostForm(
                    defaultName = currentUser.fullName,
                    defaultPhone = currentUser.phoneNumber,
                    defaultEmail = currentUser.email,
                    defaultLocation = currentUser.location,
                    defaultProfession = currentUser.profession,
                    defaultBio = currentUser.bio,
                    isLoading = postState is PostUiState.Loading,
                    onSubmit = { name, title, skills, exp, edu, loc, phone, email, bio, sal, avail ->
                        viewModel.submitProfile(name, title, skills, exp, edu, loc, phone, email, bio, sal, avail)
                    }
                )
            }
        }
    }
}

enum class FormType {
    JOB, PROFILE
}

/**
 * Fomu ya Tangazo la Kazi (Mwajiri)
 */
@Composable
fun JobPostForm(
    defaultPostedBy: String = "",
    defaultPhone: String = "",
    defaultEmail: String = "",
    defaultLocation: String = "Dar es Salaam",
    isLoading: Boolean,
    onSubmit: (
        title: String,
        company: String,
        salary: String,
        location: String,
        jobType: String,
        category: String,
        description: String,
        requirements: String,
        postedBy: String,
        contactPhone: String,
        contactEmail: String
    ) -> Unit
) {
    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var location by remember(defaultLocation) { mutableStateOf(defaultLocation.ifBlank { "Dar es Salaam" }) }
    var jobType by remember { mutableStateOf("Muda Wote") }
    var category by remember { mutableStateOf("Uuzaji na Masoko") }
    var description by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    var postedBy by remember(defaultPostedBy) { mutableStateOf(defaultPostedBy) }
    var contactPhone by remember(defaultPhone) { mutableStateOf(defaultPhone) }
    var contactEmail by remember(defaultEmail) { mutableStateOf(defaultEmail) }

    var validationError by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Taarifa za Nafasi ya Kazi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NeutralDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Cheo cha Kazi
            OutlinedTextField(
                value = jobTitle,
                onValueChange = { jobTitle = it },
                label = { Text("Cheo cha Kazi (Job Title) *") },
                placeholder = { Text("Mfano: Afisa Mauzo, Mhasibu, Dereva") },
                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_post_job_title"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Jina la Kampuni / Mwajiri
            OutlinedTextField(
                value = company,
                onValueChange = { company = it },
                label = { Text("Jina la Kampuni / Biashara *") },
                placeholder = { Text("Mfano: Kilima Goods Ltd") },
                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_post_company"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Eneo / Mkoa
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Mkoa / Eneo la Kazi *") },
                placeholder = { Text("Mfano: Dar es Salaam, Kariakoo") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_post_job_location"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Kiwango cha Mshahara
            OutlinedTextField(
                value = salary,
                onValueChange = { salary = it },
                label = { Text("Mshahara / Malipo") },
                placeholder = { Text("Mfano: TZS 800,000 - 1,200,000 au Makubaliano") },
                leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null, tint = BrandAmber) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Namba ya Simu ya Kupokea Maombi
            OutlinedTextField(
                value = contactPhone,
                onValueChange = { contactPhone = it },
                label = { Text("Namba ya Simu ya Maombi *") },
                placeholder = { Text("+255 7XX XXX XXX") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BrandGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_post_job_phone"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Barua Pepe
            OutlinedTextField(
                value = contactEmail,
                onValueChange = { contactEmail = it },
                label = { Text("Barua Pepe (Email ya CV)") },
                placeholder = { Text("ajira@kampuni.co.tz") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BrandGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Maelezo ya Majukumu
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Maelezo ya Majukumu ya Kazi") },
                placeholder = { Text("Eleza shughuli atakazofanya mfanyakazi...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .testTag("input_post_job_desc"),
                shape = RoundedCornerShape(10.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Vigezo na Sifa
            OutlinedTextField(
                value = requirements,
                onValueChange = { requirements = it },
                label = { Text("Vigezo na Sifa za Mwombaji") },
                placeholder = { Text("1. Elimu ya Stashahada au Shahada\n2. Uzoefu wa miaka 2+") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(10.dp),
                maxLines = 4
            )

            if (validationError.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = validationError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (jobTitle.isBlank() || company.isBlank() || location.isBlank() || contactPhone.isBlank()) {
                        validationError = "Tafadhali jaza sehemu zote zenye alama ya nyota (*)."
                        return@Button
                    }
                    validationError = ""
                    onSubmit(
                        jobTitle,
                        company,
                        salary,
                        location,
                        jobType,
                        category,
                        description,
                        requirements,
                        postedBy,
                        contactPhone,
                        contactEmail
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_submit_job"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chapisha Kazi Sasa",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * Fomu ya Wasifu wa Mtafuta Kazi
 */
@Composable
fun ProfilePostForm(
    defaultName: String = "",
    defaultPhone: String = "",
    defaultEmail: String = "",
    defaultLocation: String = "Dar es Salaam",
    defaultProfession: String = "",
    defaultBio: String = "",
    isLoading: Boolean,
    onSubmit: (
        fullName: String,
        title: String,
        skillsString: String,
        experience: String,
        education: String,
        location: String,
        phone: String,
        email: String,
        bio: String,
        salaryExpectation: String,
        availability: String
    ) -> Unit
) {
    var fullName by remember(defaultName) { mutableStateOf(defaultName) }
    var title by remember(defaultProfession) { mutableStateOf(defaultProfession) }
    var skillsString by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var education by remember { mutableStateOf("") }
    var location by remember(defaultLocation) { mutableStateOf(defaultLocation.ifBlank { "Dar es Salaam" }) }
    var phone by remember(defaultPhone) { mutableStateOf(defaultPhone) }
    var email by remember(defaultEmail) { mutableStateOf(defaultEmail) }
    var bio by remember(defaultBio) { mutableStateOf(defaultBio) }
    var salaryExpectation by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("Tayari Kuanza Mara Moja") }

    var validationError by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Taarifa za Wasifu Wako",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = NeutralDark
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Jina Kamili
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Jina Lako Kamili *") },
                placeholder = { Text("Mfano: Emmanuel Baraka Mushi") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_profile_name"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Cheo / Kada Unayotafuta
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Kazi / Taaluma Yako (Title) *") },
                placeholder = { Text("Mfano: Mhasibu, Dereva, Afisa Masoko") },
                leadingIcon = { Icon(Icons.Default.Work, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_profile_title"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Ujuzi / Skills
            OutlinedTextField(
                value = skillsString,
                onValueChange = { skillsString = it },
                label = { Text("Ujuzi na Uwezo Maalum (Tenganisha kwa mkato)") },
                placeholder = { Text("Mfano: QuickBooks, Tally, Excel, TRA EFD") },
                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null, tint = BrandAmber) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_profile_skills"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Mkoa
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Mkoa Unaoishi *") },
                placeholder = { Text("Mfano: Dar es Salaam, Kinondoni") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Namba ya Simu
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Namba ya Simu ya Kupigiwa *") },
                placeholder = { Text("+255 7XX XXX XXX") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = BrandGreen) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_profile_phone"),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Uzoefu wa Kazi
            OutlinedTextField(
                value = experience,
                onValueChange = { experience = it },
                label = { Text("Uzoefu wa Kazi") },
                placeholder = { Text("Mfano: Miaka 3 katika kampuni ya usambazaji") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Elimu
            OutlinedTextField(
                value = education,
                onValueChange = { education = it },
                label = { Text("Kiwango cha Elimu") },
                placeholder = { Text("Mfano: Shahada ya Uhasibu (CBE / UDSM)") },
                leadingIcon = { Icon(Icons.Default.School, contentDescription = null, tint = BrandGreen) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Mshahara Unaotegemea
            OutlinedTextField(
                value = salaryExpectation,
                onValueChange = { salaryExpectation = it },
                label = { Text("Mshahara Unaotarajia") },
                placeholder = { Text("Mfano: TZS 700,000 - 1,000,000 / mwezi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Muhtasari (Bio)
            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Kwanini Mwajiri Akuajiri Wewe? (Muhtasari)") },
                placeholder = { Text("Eleza kwa ufupi sifa zako za kipekee na jinsi utakavyoongeza thamani...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(10.dp),
                maxLines = 4
            )

            if (validationError.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = validationError,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (fullName.isBlank() || title.isBlank() || phone.isBlank() || location.isBlank()) {
                        validationError = "Tafadhali jaza Jina, Cheo, Simu na Mkoa (*)."
                        return@Button
                    }
                    validationError = ""
                    onSubmit(
                        fullName,
                        title,
                        skillsString,
                        experience,
                        education,
                        location,
                        phone,
                        email,
                        bio,
                        salaryExpectation,
                        availability
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_submit_profile"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Weka Wasifu Wangu",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
