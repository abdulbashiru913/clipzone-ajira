package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.ui.theme.BrandAmber
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandBlueLight
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenLight
import com.example.ui.theme.NeutralDark
import com.example.ui.theme.NeutralMedium

/**
 * 3. JobDetailScreen.kt - Maelezo yote ya Ajira
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: Job,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Maelezo ya Ajira",
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
                actions = {
                    IconButton(
                        onClick = {
                            shareJob(context, job)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Shiriki",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandGreen
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Button: Piga Simu
                    OutlinedButton(
                        onClick = {
                            callPhone(context, job.contactPhone)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_call_job"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Piga Simu", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    // Button: Tuma Maombi SMS
                    Button(
                        onClick = {
                            sendJobApplicationSms(context, job)
                        },
                        modifier = Modifier
                            .weight(1.3f)
                            .height(50.dp)
                            .testTag("btn_sms_job"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Tuma Maombi SMS", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
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
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandGreenLight,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = job.jobType,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandGreenDark,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = job.jobTitle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = null,
                            tint = BrandGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = job.company,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandGreenDark
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Eneo la Kazi", fontSize = 12.sp, color = NeutralMedium)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = BrandGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = job.location,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = NeutralDark
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Kiwango cha Mshahara", fontSize = 12.sp, color = NeutralMedium)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = BrandAmber,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = job.salary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandAmber
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Maelezo ya Kazi
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Majukumu ya Kazi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = job.description.ifBlank { "Hakuna maelezo ya ziada yaliyowekwa." },
                        fontSize = 14.sp,
                        color = Color(0xFF334155),
                        lineHeight = 22.sp
                    )

                    if (job.requirements.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Vigezo na Sifa za Mwombaji",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeutralDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = job.requirements,
                            fontSize = 14.sp,
                            color = Color(0xFF334155),
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mawasiliano ya Mwajiri
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Mawasiliano ya Mwajiri",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = BrandGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = job.contactPhone.ifBlank { "Haikutajwa" },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NeutralDark
                        )
                    }

                    if (job.contactEmail.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = BrandBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = job.contactEmail,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BrandBlue
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * 4. ProfileDetailScreen.kt - Ukibonyeza kadi ya Mtafuta Kazi
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileDetailScreen(
    profile: JobSeekerProfile,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wasifu wa Mtafuta Kazi",
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
                actions = {
                    IconButton(
                        onClick = {
                            shareProfile(context, profile)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Shiriki",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BrandGreen
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // SMS Button
                    OutlinedButton(
                        onClick = {
                            sendProfileSms(context, profile)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_sms_profile"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Tuma SMS", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    // Button: "Ajiri Mtu Huyu - Piga Simu"
                    Button(
                        onClick = {
                            callPhone(context, profile.phone)
                        },
                        modifier = Modifier
                            .weight(1.4f)
                            .height(50.dp)
                            .testTag("btn_hire_call_profile"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Ajiri - Piga Simu",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
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
            // Profile Card Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(BrandGreenLight, Color(0xFFC8E6C9))
                                )
                            )
                            .border(2.dp, BrandGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = profile.fullName.split(" ")
                            .take(2)
                            .mapNotNull { it.firstOrNull()?.toString() }
                            .joinToString("")
                            .ifBlank { "MK" }

                        Text(
                            text = initials,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            color = BrandGreenDark
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile.fullName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )

                    Text(
                        text = profile.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandGreen,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = NeutralMedium,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = profile.location,
                                    fontSize = 12.sp,
                                    color = NeutralDark
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = BrandBlueLight
                        ) {
                            Text(
                                text = profile.availability,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandBlue,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ujuzi (Skills)
            if (profile.skills.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Ujuzi na Uwezo Maalum (Skills)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeutralDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            profile.skills.forEach { skill ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = BrandGreenLight
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = BrandGreenDark,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = skill,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = BrandGreenDark
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Muhtasari wa Wasifu (Bio)
            if (profile.bio.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Kuhusu Mtafuta Kazi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeutralDark
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = profile.bio,
                            fontSize = 14.sp,
                            color = Color(0xFF334155),
                            lineHeight = 22.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Uzoefu & Elimu
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Uzoefu wa Kazi na Elimu",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeutralDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = null,
                            tint = BrandGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Uzoefu wa Kazi",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeutralDark
                            )
                            Text(
                                text = profile.experience,
                                fontSize = 13.sp,
                                color = Color(0xFF475569)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = BrandBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Kiwango cha Elimu",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeutralDark
                            )
                            Text(
                                text = profile.education,
                                fontSize = 13.sp,
                                color = Color(0xFF475569)
                            )
                        }
                    }

                    if (profile.salaryExpectation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                tint = BrandAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Mshahara Anaoomba",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeutralDark
                                )
                                Text(
                                    text = profile.salaryExpectation,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = BrandAmber
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Helper Functions za Mawasiliano
private fun callPhone(context: Context, phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Haikuweza kufungua programu ya kupiga simu.", Toast.LENGTH_SHORT).show()
    }
}

private fun sendJobApplicationSms(context: Context, job: Job) {
    try {
        val message = "Habari ${job.company}, Ninaomba nafasi ya kazi ya ${job.jobTitle} niliyoiona kwenye ClipZone Ajira. Tafadhali naomba maelekezo ya kuwasilisha CV yangu."
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:${job.contactPhone}")
            putExtra("sms_body", message)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Haikuweza kufungua SMS.", Toast.LENGTH_SHORT).show()
    }
}

private fun sendProfileSms(context: Context, profile: JobSeekerProfile) {
    try {
        val message = "Habari ${profile.fullName}, nimeona wasifu wako kwenye ClipZone Ajira kuhusu nafasi ya ${profile.title}. Je, una nafasi ya kufanya usaili/mazungumzo?"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:${profile.phone}")
            putExtra("sms_body", message)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Haikuweza kufungua SMS.", Toast.LENGTH_SHORT).show()
    }
}

private fun shareJob(context: Context, job: Job) {
    val text = "Ajira Mpya: ${job.jobTitle} katika ${job.company}, ${job.location}.\nMshahara: ${job.salary}\nTazama kwenye ClipZone Ajira!"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Fursa ya Ajira - ClipZone Ajira")
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Shiriki Ajira Hii"))
}

private fun shareProfile(context: Context, profile: JobSeekerProfile) {
    val text = "Wasifu wa Mtafuta Kazi: ${profile.fullName} - ${profile.title} (${profile.location}). Uzoefu: ${profile.experience}.\nTazama kwenye ClipZone Ajira!"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Mtafuta Kazi - ClipZone Ajira")
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, "Shiriki Wasifu"))
}
