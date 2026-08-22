package com.example.data.model

import com.example.util.AppLanguage

/**
 * Muundo wa data ya Kazi iliyowekwa na Mwajiri
 */
data class Job(
    val id: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val salary: String = "",
    val location: String = "",
    val jobType: String = "Muda Wote", // Muda Wote, Muda Mfupi, Mkataba, n.k.
    val category: String = "Jumla",
    val description: String = "",
    val requirements: String = "",
    val postedBy: String = "",
    val contactPhone: String = "",
    val contactEmail: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Muundo wa data ya Wasifu wa Mtafuta Kazi
 */
data class JobSeekerProfile(
    val id: String = "",
    val userId: String = "",
    val fullName: String = "",
    val title: String = "", // Mfano: Mhasibu, Dereva, Mhandisi
    val skills: List<String> = emptyList(),
    val experience: String = "",
    val education: String = "",
    val location: String = "",
    val phone: String = "",
    val email: String = "",
    val bio: String = "",
    val salaryExpectation: String = "",
    val availability: String = "Tayari Kuanza",
    val avatarUrl: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Aina ya Mtumiaji
 */
enum class UserRole(val displayNameSwahili: String, val displayNameEnglish: String) {
    JOB_SEEKER("Natafuta Kazi", "Looking for Job"),
    EMPLOYER("Ninaajiri", "Hiring / Employer");

    fun getDisplayName(lang: AppLanguage): String = when (lang) {
        AppLanguage.SWAHILI -> displayNameSwahili
        AppLanguage.ENGLISH -> displayNameEnglish
    }
}

/**
 * Taarifa ya Mtumiaji aliyepo kwenye App
 */
data class CurrentUser(
    val id: String = "user_guest",
    val phoneNumber: String = "",
    val email: String = "",
    val fullName: String = "Mtumiaji wa ClipZone",
    val location: String = "Dar es Salaam",
    val profession: String = "",
    val bio: String = "",
    val avatarUrl: String = "",
    val role: UserRole = UserRole.JOB_SEEKER,
    val isLoggedIn: Boolean = true
)

/**
 * Taarifa ya Arifa
 */
data class AppNotification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val timeAgo: String = "",
    val type: String = "job", // job, profile, system
    val isRead: Boolean = false
)
