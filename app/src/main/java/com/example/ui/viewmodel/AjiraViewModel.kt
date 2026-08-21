package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.SeedData
import com.example.data.model.AppNotification
import com.example.data.model.CurrentUser
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.data.model.UserRole
import com.example.data.repository.AjiraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class FeedTab(val title: String) {
    JOB_SEEKERS("WATAFUTA KAZI"),
    EMPLOYERS("WAJIRI")
}

sealed interface PostUiState {
    object Idle : PostUiState
    object Loading : PostUiState
    data class Success(val message: String) : PostUiState
    data class Error(val error: String) : PostUiState
}

class AjiraViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AjiraRepository(application.applicationContext)

    // Current tab on FeedScreen
    private val _selectedTab = MutableStateFlow(FeedTab.JOB_SEEKERS)
    val selectedTab: StateFlow<FeedTab> = _selectedTab.asStateFlow()

    // Search & Filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedLocation = MutableStateFlow("Mikoa Yote")
    val selectedLocation: StateFlow<String> = _selectedLocation.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Kada Zote")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Active Selection for Detail Screens
    private val _selectedJob = MutableStateFlow<Job?>(null)
    val selectedJob: StateFlow<Job?> = _selectedJob.asStateFlow()

    private val _selectedProfile = MutableStateFlow<JobSeekerProfile?>(null)
    val selectedProfile: StateFlow<JobSeekerProfile?> = _selectedProfile.asStateFlow()

    // Notifications
    private val _notifications = MutableStateFlow(SeedData.sampleNotifications)
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    // User State & Offline Mode
    val currentUser: StateFlow<CurrentUser> = repository.currentUser
    val isOfflineMode: StateFlow<Boolean> = repository.isOfflineMode

    // Post State
    private val _postState = MutableStateFlow<PostUiState>(PostUiState.Idle)
    val postState: StateFlow<PostUiState> = _postState.asStateFlow()

    // Filtered Jobs
    val filteredJobs: StateFlow<List<Job>> = combine(
        repository.allJobsFlow,
        _searchQuery,
        _selectedLocation,
        _selectedCategory
    ) { jobs, query, location, category ->
        jobs.filter { job ->
            val matchesQuery = query.isBlank() ||
                    job.jobTitle.contains(query, ignoreCase = true) ||
                    job.company.contains(query, ignoreCase = true) ||
                    job.location.contains(query, ignoreCase = true) ||
                    job.description.contains(query, ignoreCase = true)

            val matchesLocation = location == "Mikoa Yote" || job.location.contains(location, ignoreCase = true)
            val matchesCategory = category == "Kada Zote" || job.category.contains(category, ignoreCase = true)

            matchesQuery && matchesLocation && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Profiles
    val filteredProfiles: StateFlow<List<JobSeekerProfile>> = combine(
        repository.allProfilesFlow,
        _searchQuery,
        _selectedLocation,
        _selectedCategory
    ) { profiles, query, location, category ->
        profiles.filter { profile ->
            val matchesQuery = query.isBlank() ||
                    profile.fullName.contains(query, ignoreCase = true) ||
                    profile.title.contains(query, ignoreCase = true) ||
                    profile.location.contains(query, ignoreCase = true) ||
                    profile.skills.any { it.contains(query, ignoreCase = true) }

            val matchesLocation = location == "Mikoa Yote" || profile.location.contains(location, ignoreCase = true)
            val matchesCategory = category == "Kada Zote" || profile.title.contains(category, ignoreCase = true) ||
                    profile.skills.any { it.contains(category, ignoreCase = true) }

            matchesQuery && matchesLocation && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setSelectedTab(tab: FeedTab) {
        _selectedTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedLocation(location: String) {
        _selectedLocation.value = location
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectJob(job: Job) {
        _selectedJob.value = job
    }

    fun selectProfile(profile: JobSeekerProfile) {
        _selectedProfile.value = profile
    }

    fun updateUserProfile(
        fullName: String,
        phoneNumber: String,
        email: String,
        location: String,
        profession: String,
        bio: String,
        role: UserRole
    ) {
        repository.updateUserProfile(fullName, phoneNumber, email, location, profession, bio, role)
    }

    fun loginUser(phone: String, email: String, fullName: String, role: UserRole) {
        repository.setUserLogin(phone, email, fullName, role)
    }

    fun loginUser(phone: String, fullName: String, role: UserRole) {
        repository.setUserLogin(phone, "", fullName, role)
    }

    fun switchUserRole(role: UserRole) {
        repository.updateUserRole(role)
    }

    fun resetPostState() {
        _postState.value = PostUiState.Idle
    }

    fun submitJob(
        jobTitle: String,
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
    ) {
        if (jobTitle.isBlank() || company.isBlank() || location.isBlank() || contactPhone.isBlank()) {
            _postState.value = PostUiState.Error("Tafadhali jaza nafasi zote zenye alama ya nyota (*).")
            return
        }

        viewModelScope.launch {
            _postState.value = PostUiState.Loading
            val newJob = Job(
                jobTitle = jobTitle.trim(),
                company = company.trim(),
                salary = salary.trim().ifBlank { "Makubaliano" },
                location = location.trim(),
                jobType = jobType.trim(),
                category = category.trim(),
                description = description.trim(),
                requirements = requirements.trim(),
                postedBy = postedBy.trim().ifBlank { company.trim() },
                contactPhone = contactPhone.trim(),
                contactEmail = contactEmail.trim()
            )

            val result = repository.postJob(newJob)
            if (result.isSuccess) {
                _postState.value = PostUiState.Success("Kazi imewekwa kikamilifu kwenye ClipZone Ajira!")
                // Add notification
                val newNotif = AppNotification(
                    id = "notif_${System.currentTimeMillis()}",
                    title = "Kazi yako imechapishwa!",
                    message = "${newJob.jobTitle} - ${newJob.company} sasa inaonekana kwa watafuta kazi.",
                    timeAgo = "Sasa hivi",
                    type = "job"
                )
                _notifications.value = listOf(newNotif) + _notifications.value
            } else {
                _postState.value = PostUiState.Error("Imeshindikana kuweka kazi: ${result.exceptionOrNull()?.localizedMessage ?: "Hitilafu ya mtandao"}")
            }
        }
    }

    fun submitProfile(
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
    ) {
        if (fullName.isBlank() || title.isBlank() || phone.isBlank() || location.isBlank()) {
            _postState.value = PostUiState.Error("Tafadhali jaza Jina, Cheo, Simu na Mkoa (*).")
            return
        }

        viewModelScope.launch {
            _postState.value = PostUiState.Loading
            val skillsList = skillsString.split(",", "•", "\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val newProfile = JobSeekerProfile(
                userId = currentUser.value.id,
                fullName = fullName.trim(),
                title = title.trim(),
                skills = if (skillsList.isEmpty()) listOf(title.trim()) else skillsList,
                experience = experience.trim().ifBlank { "Mzoefu katika fani hii" },
                education = education.trim().ifBlank { "Elimu ya Sekondari / Chuo" },
                location = location.trim(),
                phone = phone.trim(),
                email = email.trim(),
                bio = bio.trim(),
                salaryExpectation = salaryExpectation.trim().ifBlank { "Makubaliano" },
                availability = availability.trim()
            )

            val result = repository.postProfile(newProfile)
            if (result.isSuccess) {
                _postState.value = PostUiState.Success("Wasifu wako umewekwa kikamilifu kwenye ClipZone Ajira!")
                // Add notification
                val newNotif = AppNotification(
                    id = "notif_${System.currentTimeMillis()}",
                    title = "Wasifu wako umepakiwa!",
                    message = "${newProfile.fullName} (${newProfile.title}) sasa unapatikana kwa waajiri wote.",
                    timeAgo = "Sasa hivi",
                    type = "profile"
                )
                _notifications.value = listOf(newNotif) + _notifications.value
            } else {
                _postState.value = PostUiState.Error("Imeshindikana kuweka wasifu: ${result.exceptionOrNull()?.localizedMessage ?: "Hitilafu ya mtandao"}")
            }
        }
    }
}
