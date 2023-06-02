package nl.recall.domain.repositories

interface OnboardingRepository {
    suspend fun getOnboardingCompleted() : Boolean
    suspend fun setOnboardingCompleted(completed: Boolean)
}