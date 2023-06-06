package nl.recall.domain.repositories

interface OnboardingRepository {
    fun getOnboardingCompleted() : Boolean
    suspend fun setOnboardingCompleted(completed: Boolean)
}