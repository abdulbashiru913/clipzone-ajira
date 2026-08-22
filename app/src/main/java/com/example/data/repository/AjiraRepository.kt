package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.local.AppDatabase
import com.example.data.local.JobEntity
import com.example.data.local.ProfileEntity
import com.example.data.local.SeedData
import com.example.data.model.CurrentUser
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile
import com.example.data.model.UserRole
import com.example.util.AppLanguage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class AjiraRepository(private val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val jobDao = db.jobDao()
    private val profileDao = db.profileDao()

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    // Firebase instances
    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.e("AjiraRepository", "FirebaseFirestore initialization failed: ${e.message}")
            null
        }
    }

    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.e("AjiraRepository", "FirebaseAuth initialization failed: ${e.message}")
            null
        }
    }

    private var jobsListener: ListenerRegistration? = null
    private var profilesListener: ListenerRegistration? = null

    private val prefs = context.getSharedPreferences("clipzone_user_prefs", Context.MODE_PRIVATE)

    // Mfumo wa Lugha (Swahili / English)
    private val _appLanguage = MutableStateFlow(
        try {
            AppLanguage.valueOf(prefs.getString("app_language", AppLanguage.SWAHILI.name) ?: AppLanguage.SWAHILI.name)
        } catch (e: Exception) {
            AppLanguage.SWAHILI
        }
    )
    val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

    // Hali ya Mtumiaji (Inapakia moja kwa moja kutoka kwenye hifadhi ya simu bila akaunti au password)
    private val _currentUser = MutableStateFlow(
        CurrentUser(
            id = prefs.getString("user_id", null) ?: "user_${UUID.randomUUID().toString().take(6)}".also {
                prefs.edit().putString("user_id", it).apply()
            },
            phoneNumber = prefs.getString("user_phone", "") ?: "",
            email = prefs.getString("user_email", "") ?: "",
            fullName = prefs.getString("user_name", "Mtumiaji wa ClipZone") ?: "Mtumiaji wa ClipZone",
            location = prefs.getString("user_location", "Dar es Salaam") ?: "Dar es Salaam",
            profession = prefs.getString("user_profession", "") ?: "",
            bio = prefs.getString("user_bio", "") ?: "",
            avatarUrl = prefs.getString("user_avatar", "") ?: "",
            role = try {
                UserRole.valueOf(prefs.getString("user_role", UserRole.JOB_SEEKER.name) ?: UserRole.JOB_SEEKER.name)
            } catch (e: Exception) {
                UserRole.JOB_SEEKER
            },
            isLoggedIn = true
        )
    )
    val currentUser: StateFlow<CurrentUser> = _currentUser.asStateFlow()

    // Offline / Online Status Indicator
    private val _isOfflineMode = MutableStateFlow(false)
    val isOfflineMode: StateFlow<Boolean> = _isOfflineMode.asStateFlow()

    // Flow kutoka Room Database (Inasaidia Offline Cache 100%)
    val allJobsFlow: Flow<List<Job>> = jobDao.getAllJobsFlow().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val allProfilesFlow: Flow<List<JobSeekerProfile>> = profileDao.getAllProfilesFlow().map { entities ->
        entities.map { it.toDomainModel() }
    }

    init {
        // Pre-populate Room DB with Tanzanian seed data if empty
        repositoryScope.launch {
            seedDatabaseIfEmpty()
            setupRealtimeFirestoreListeners()
        }
    }

    private suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        val currentJobs = jobDao.getJobById("job_1")
        if (currentJobs == null) {
            val jobEntities = SeedData.sampleJobs.map { JobEntity.fromDomainModel(it) }
            jobDao.insertJobs(jobEntities)

            val profileEntities = SeedData.sampleProfiles.map { ProfileEntity.fromDomainModel(it) }
            profileDao.insertProfiles(profileEntities)
            Log.d("AjiraRepository", "Database seeded with initial jobs & profiles.")
        }
    }

    /**
     * Weka Firebase Firestore addSnapshotListener kwa ajili ya updates za moja kwa moja (live)
     */
    private fun setupRealtimeFirestoreListeners() {
        val fs = firestore ?: run {
            _isOfflineMode.value = true
            return
        }

        try {
            // Live listener kwa "jobs"
            jobsListener?.remove()
            jobsListener = fs.collection("jobs")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.w("AjiraRepository", "Jobs snapshot error: ${error.message}")
                        _isOfflineMode.value = true
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        _isOfflineMode.value = false
                        val remoteJobs = snapshot.documents.mapNotNull { doc ->
                            try {
                                val id = doc.id
                                val title = doc.getString("jobTitle") ?: ""
                                val company = doc.getString("company") ?: ""
                                val salary = doc.getString("salary") ?: ""
                                val location = doc.getString("location") ?: ""
                                val jobType = doc.getString("jobType") ?: "Muda Wote"
                                val category = doc.getString("category") ?: "Jumla"
                                val description = doc.getString("description") ?: ""
                                val requirements = doc.getString("requirements") ?: ""
                                val postedBy = doc.getString("postedBy") ?: ""
                                val contactPhone = doc.getString("contactPhone") ?: ""
                                val contactEmail = doc.getString("contactEmail") ?: ""
                                val timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()

                                Job(
                                    id = id,
                                    jobTitle = title,
                                    company = company,
                                    salary = salary,
                                    location = location,
                                    jobType = jobType,
                                    category = category,
                                    description = description,
                                    requirements = requirements,
                                    postedBy = postedBy,
                                    contactPhone = contactPhone,
                                    contactEmail = contactEmail,
                                    timestamp = timestamp
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }

                        if (remoteJobs.isNotEmpty()) {
                            repositoryScope.launch(Dispatchers.IO) {
                                jobDao.insertJobs(remoteJobs.map { JobEntity.fromDomainModel(it) })
                            }
                        }
                    }
                }

            // Live listener kwa "profiles"
            profilesListener?.remove()
            profilesListener = fs.collection("profiles")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.w("AjiraRepository", "Profiles snapshot error: ${error.message}")
                        _isOfflineMode.value = true
                        return@addSnapshotListener
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        _isOfflineMode.value = false
                        val remoteProfiles = snapshot.documents.mapNotNull { doc ->
                            try {
                                val id = doc.id
                                val userId = doc.getString("userId") ?: ""
                                val fullName = doc.getString("fullName") ?: ""
                                val title = doc.getString("title") ?: ""
                                @Suppress("UNCHECKED_CAST")
                                val skills = (doc.get("skills") as? List<String>) ?: emptyList()
                                val experience = doc.getString("experience") ?: ""
                                val education = doc.getString("education") ?: ""
                                val location = doc.getString("location") ?: ""
                                val phone = doc.getString("phone") ?: ""
                                val email = doc.getString("email") ?: ""
                                val bio = doc.getString("bio") ?: ""
                                val salaryExpectation = doc.getString("salaryExpectation") ?: ""
                                val availability = doc.getString("availability") ?: "Tayari Kuanza"
                                val avatarUrl = doc.getString("avatarUrl") ?: ""
                                val timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()

                                JobSeekerProfile(
                                    id = id,
                                    userId = userId,
                                    fullName = fullName,
                                    title = title,
                                    skills = skills,
                                    experience = experience,
                                    education = education,
                                    location = location,
                                    phone = phone,
                                    email = email,
                                    bio = bio,
                                    salaryExpectation = salaryExpectation,
                                    availability = availability,
                                    avatarUrl = avatarUrl,
                                    timestamp = timestamp
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }

                        if (remoteProfiles.isNotEmpty()) {
                            repositoryScope.launch(Dispatchers.IO) {
                                profileDao.insertProfiles(remoteProfiles.map { ProfileEntity.fromDomainModel(it) })
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            Log.e("AjiraRepository", "Listener registration failed: ${e.message}")
            _isOfflineMode.value = true
        }
    }

    /**
     * Weka Kazi Mpya (Firestore + Room Cache)
     */
    suspend fun postJob(job: Job): Result<Job> = withContext(Dispatchers.IO) {
        try {
            val jobId = if (job.id.isBlank()) "job_${UUID.randomUUID().toString().take(8)}" else job.id
            val finalJob = job.copy(
                id = jobId,
                timestamp = System.currentTimeMillis()
            )

            // Hifadhi kwenye Local Room Database kwanza (Offline-First)
            jobDao.insertJob(JobEntity.fromDomainModel(finalJob))

            // Jaribu kutuma Firestore
            firestore?.let { fs ->
                val jobData = hashMapOf(
                    "jobTitle" to finalJob.jobTitle,
                    "company" to finalJob.company,
                    "salary" to finalJob.salary,
                    "location" to finalJob.location,
                    "jobType" to finalJob.jobType,
                    "category" to finalJob.category,
                    "description" to finalJob.description,
                    "requirements" to finalJob.requirements,
                    "postedBy" to finalJob.postedBy,
                    "contactPhone" to finalJob.contactPhone,
                    "contactEmail" to finalJob.contactEmail,
                    "timestamp" to finalJob.timestamp
                )
                fs.collection("jobs").document(jobId).set(jobData)
            }

            Result.success(finalJob)
        } catch (e: Exception) {
            Log.e("AjiraRepository", "Failed to post job: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Weka Wasifu wa Mtafuta Kazi Mpya (Firestore + Room Cache)
     */
    suspend fun postProfile(profile: JobSeekerProfile): Result<JobSeekerProfile> = withContext(Dispatchers.IO) {
        try {
            val profileId = if (profile.id.isBlank()) "prof_${UUID.randomUUID().toString().take(8)}" else profile.id
            val finalProfile = profile.copy(
                id = profileId,
                timestamp = System.currentTimeMillis()
            )

            // Hifadhi kwenye Local Room Database kwanza (Offline-First)
            profileDao.insertProfile(ProfileEntity.fromDomainModel(finalProfile))

            // Jaribu kutuma Firestore
            firestore?.let { fs ->
                val profileData = hashMapOf(
                    "userId" to finalProfile.userId,
                    "fullName" to finalProfile.fullName,
                    "title" to finalProfile.title,
                    "skills" to finalProfile.skills,
                    "experience" to finalProfile.experience,
                    "education" to finalProfile.education,
                    "location" to finalProfile.location,
                    "phone" to finalProfile.phone,
                    "email" to finalProfile.email,
                    "bio" to finalProfile.bio,
                    "salaryExpectation" to finalProfile.salaryExpectation,
                    "availability" to finalProfile.availability,
                    "avatarUrl" to finalProfile.avatarUrl,
                    "timestamp" to finalProfile.timestamp
                )
                fs.collection("profiles").document(profileId).set(profileData)
            }

            Result.success(finalProfile)
        } catch (e: Exception) {
            Log.e("AjiraRepository", "Failed to post profile: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Weka Taarifa za Mtumiaji (Bila Akaunti / Password - Huokolewa Moja kwa Moja)
     */
    fun updateUserProfile(
        fullName: String,
        phoneNumber: String,
        email: String,
        location: String,
        profession: String,
        bio: String,
        avatarUrl: String = _currentUser.value.avatarUrl,
        role: UserRole = _currentUser.value.role
    ) {
        val updated = _currentUser.value.copy(
            fullName = fullName.ifBlank { "Mtumiaji wa ClipZone" },
            phoneNumber = phoneNumber,
            email = email,
            location = location.ifBlank { "Dar es Salaam" },
            profession = profession,
            bio = bio,
            avatarUrl = avatarUrl,
            role = role,
            isLoggedIn = true
        )
        _currentUser.value = updated

        // Save to SharedPreferences
        prefs.edit()
            .putString("user_name", updated.fullName)
            .putString("user_phone", updated.phoneNumber)
            .putString("user_email", updated.email)
            .putString("user_location", updated.location)
            .putString("user_profession", updated.profession)
            .putString("user_bio", updated.bio)
            .putString("user_avatar", updated.avatarUrl)
            .putString("user_role", updated.role.name)
            .apply()
    }

    fun setAppLanguage(language: AppLanguage) {
        _appLanguage.value = language
        prefs.edit().putString("app_language", language.name).apply()
    }

    fun setUserLogin(phoneNumber: String, email: String, fullName: String, role: UserRole) {
        updateUserProfile(
            fullName = fullName,
            phoneNumber = phoneNumber,
            email = email,
            location = _currentUser.value.location,
            profession = _currentUser.value.profession,
            bio = _currentUser.value.bio,
            avatarUrl = _currentUser.value.avatarUrl,
            role = role
        )
    }

    fun updateUserRole(role: UserRole) {
        val updated = _currentUser.value.copy(role = role)
        _currentUser.value = updated
        prefs.edit().putString("user_role", role.name).apply()
    }

    suspend fun getJobById(id: String): Job? = withContext(Dispatchers.IO) {
        jobDao.getJobById(id)?.toDomainModel()
    }

    suspend fun getProfileById(id: String): JobSeekerProfile? = withContext(Dispatchers.IO) {
        profileDao.getProfileById(id)?.toDomainModel()
    }
}
