package com.example.util

/**
 * Mfumo wa Lugha Mbili: Kiswahili & English
 */
enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    SWAHILI("sw", "Kiswahili", "🇹🇿"),
    ENGLISH("en", "English", "🇬🇧")
}

object AppStrings {

    fun appTagline(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Ungana na Fursa za Tanzania"
        AppLanguage.ENGLISH -> "Connect with Opportunities in Tanzania"
    }

    // Bottom Navigation & Titles
    fun home(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Nyumbani"
        AppLanguage.ENGLISH -> "Home"
    }
    fun navHome(lang: AppLanguage) = home(lang)

    fun notifications(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Arifa"
        AppLanguage.ENGLISH -> "Alerts"
    }
    fun navNotifications(lang: AppLanguage) = notifications(lang)

    fun profile(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Wasifu"
        AppLanguage.ENGLISH -> "Profile"
    }
    fun navProfile(lang: AppLanguage) = profile(lang)

    fun navPost(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Tangaza"
        AppLanguage.ENGLISH -> "Post"
    }

    fun noNotifications(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Hakuna arifa mpya kwa sasa."
        AppLanguage.ENGLISH -> "No new notifications at this time."
    }

    // Feed Tabs
    fun tabJobSeekers(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "WATAFUTA KAZI"
        AppLanguage.ENGLISH -> "JOB SEEKERS"
    }

    fun tabEmployers(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "WAJIRI NA NAFASI"
        AppLanguage.ENGLISH -> "JOBS & EMPLOYERS"
    }

    fun allLocations(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Mikoa Yote"
        AppLanguage.ENGLISH -> "All Regions"
    }

    val TANZANIA_REGIONS = listOf(
        "Dar es Salaam",
        "Dodoma",
        "Arusha",
        "Mwanza",
        "Mbeya",
        "Morogoro",
        "Tanga",
        "Kilimanjaro",
        "Tabora",
        "Kigoma",
        "Kagera",
        "Mara",
        "Geita",
        "Simiyu",
        "Shinyanga",
        "Singida",
        "Iringa",
        "Njombe",
        "Ruvuma",
        "Lindi",
        "Mtwara",
        "Manyara",
        "Rukwa",
        "Katavi",
        "Songwe",
        "Pwani",
        "Zanzibar (Mjini Magharibi)",
        "Unguja Kaskazini",
        "Unguja Kusini",
        "Pemba Kaskazini",
        "Pemba Kusini"
    )

    // Roles
    fun role(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Jukumu Lako"
        AppLanguage.ENGLISH -> "Your Role"
    }

    fun roleJobSeeker(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Natafuta Kazi"
        AppLanguage.ENGLISH -> "Job Seeker"
    }

    fun roleEmployer(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Ninaajiri (Mwajiri)"
        AppLanguage.ENGLISH -> "Hiring (Employer)"
    }

    // Language
    fun language(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Lugha / Language"
        AppLanguage.ENGLISH -> "Language / Lugha"
    }

    // Card Actions
    fun viewDetails(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Maelezo"
        AppLanguage.ENGLISH -> "Details"
    }

    fun contact(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Wasiliana"
        AppLanguage.ENGLISH -> "Contact"
    }

    fun applyNow(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Tuma Maombi"
        AppLanguage.ENGLISH -> "Apply Now"
    }

    fun call(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Piga Simu"
        AppLanguage.ENGLISH -> "Call"
    }

    fun whatsapp(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "WhatsApp"
        AppLanguage.ENGLISH -> "WhatsApp"
    }

    // Profile Screen
    fun profileTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Wasifu na Taarifa Zangu"
        AppLanguage.ENGLISH -> "My Profile & Details"
    }

    fun profilePhoto(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Picha ya Wasifu"
        AppLanguage.ENGLISH -> "Profile Photo"
    }

    fun uploadPhoto(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Weka Picha"
        AppLanguage.ENGLISH -> "Upload Photo"
    }

    fun changePhoto(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Badili Picha"
        AppLanguage.ENGLISH -> "Change Photo"
    }

    fun removePhoto(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Ondoa Picha"
        AppLanguage.ENGLISH -> "Remove Photo"
    }

    fun fullName(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Jina Lako Kamili"
        AppLanguage.ENGLISH -> "Full Name"
    }
    fun fullNameLabel(lang: AppLanguage) = fullName(lang)

    fun phoneNumber(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Nambari ya Simu (WhatsApp/Kupigiwa)"
        AppLanguage.ENGLISH -> "Phone Number (WhatsApp/Call)"
    }
    fun phoneLabel(lang: AppLanguage) = phoneNumber(lang)

    fun email(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Barua Pepe (Email)"
        AppLanguage.ENGLISH -> "Email Address"
    }
    fun emailLabel(lang: AppLanguage) = email(lang)

    fun location(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Mkoa / Eneo Unapoishi"
        AppLanguage.ENGLISH -> "Region / City"
    }
    fun locationLabel(lang: AppLanguage) = location(lang)

    fun jobTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Taaluma / Cheo"
        AppLanguage.ENGLISH -> "Job Title / Profession"
    }
    fun professionLabel(lang: AppLanguage) = jobTitle(lang)

    fun bio(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Kuhusu Wewe / Maelezo"
        AppLanguage.ENGLISH -> "About You / Bio"
    }
    fun bioLabel(lang: AppLanguage) = bio(lang)

    fun save(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Hifadhi Taarifa"
        AppLanguage.ENGLISH -> "Save Details"
    }
    fun saveChanges(lang: AppLanguage) = save(lang)

    fun savedSuccess(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Taarifa zako zimehifadhiwa kikamilifu!"
        AppLanguage.ENGLISH -> "Your information has been saved successfully!"
    }

    // Post Screen
    fun postTitle(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Weka Tangazo Mpya"
        AppLanguage.ENGLISH -> "Create New Post"
    }

    fun postJobOption(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Tangaza Kazi"
        AppLanguage.ENGLISH -> "Post a Job"
    }

    fun postProfileOption(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Weka Wasifu Wako"
        AppLanguage.ENGLISH -> "Post Your Profile"
    }

    fun submitPost(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Chapisha Tangazo"
        AppLanguage.ENGLISH -> "Publish Post"
    }

    // Empty state
    fun emptyJobs(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Hakuna nafasi za kazi zilizopatikana kwa sasa."
        AppLanguage.ENGLISH -> "No job openings found at the moment."
    }

    fun emptyProfiles(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Hakuna wasifu wa watafuta kazi kwa sasa."
        AppLanguage.ENGLISH -> "No job seekers found at the moment."
    }

    // Privacy & Terms
    fun privacyPolicy(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Sera ya Faragha"
        AppLanguage.ENGLISH -> "Privacy Policy"
    }

    fun termsOfService(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Vigezo & Masharti"
        AppLanguage.ENGLISH -> "Terms & Conditions"
    }
    fun termsAndConditions(lang: AppLanguage) = termsOfService(lang)

    fun close(lang: AppLanguage) = when (lang) {
        AppLanguage.SWAHILI -> "Funga"
        AppLanguage.ENGLISH -> "Close"
    }
}
