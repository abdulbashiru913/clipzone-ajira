package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Job
import com.example.data.model.JobSeekerProfile

@Entity(tableName = "jobs_table")
data class JobEntity(
    @PrimaryKey
    val id: String,
    val jobTitle: String,
    val company: String,
    val salary: String,
    val location: String,
    val jobType: String,
    val category: String,
    val description: String,
    val requirements: String,
    val postedBy: String,
    val contactPhone: String,
    val contactEmail: String,
    val timestamp: Long
) {
    fun toDomainModel(): Job = Job(
        id = id,
        jobTitle = jobTitle,
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

    companion object {
        fun fromDomainModel(job: Job): JobEntity = JobEntity(
            id = job.id,
            jobTitle = job.jobTitle,
            company = job.company,
            salary = job.salary,
            location = job.location,
            jobType = job.jobType,
            category = job.category,
            description = job.description,
            requirements = job.requirements,
            postedBy = job.postedBy,
            contactPhone = job.contactPhone,
            contactEmail = job.contactEmail,
            timestamp = job.timestamp
        )
    }
}

@Entity(tableName = "profiles_table")
data class ProfileEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val fullName: String,
    val title: String,
    val skillsRaw: String, // comma-separated
    val experience: String,
    val education: String,
    val location: String,
    val phone: String,
    val email: String,
    val bio: String,
    val salaryExpectation: String,
    val availability: String,
    val avatarUrl: String,
    val timestamp: Long
) {
    fun toDomainModel(): JobSeekerProfile = JobSeekerProfile(
        id = id,
        userId = userId,
        fullName = fullName,
        title = title,
        skills = if (skillsRaw.isBlank()) emptyList() else skillsRaw.split(",").map { it.trim() },
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

    companion object {
        fun fromDomainModel(profile: JobSeekerProfile): ProfileEntity = ProfileEntity(
            id = profile.id,
            userId = profile.userId,
            fullName = profile.fullName,
            title = profile.title,
            skillsRaw = profile.skills.joinToString(","),
            experience = profile.experience,
            education = profile.education,
            location = profile.location,
            phone = profile.phone,
            email = profile.email,
            bio = profile.bio,
            salaryExpectation = profile.salaryExpectation,
            availability = profile.availability,
            avatarUrl = profile.avatarUrl,
            timestamp = profile.timestamp
        )
    }
}
