package nl.recall.data.onboarding

import android.content.SharedPreferences
import nl.recall.domain.repositories.OnboardingRepository
import org.koin.core.annotation.Factory

@Factory
class RemoteOnboardingRepository(private val sharedPreferences: SharedPreferences) : OnboardingRepository  {
    override suspend fun getOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("OnboardingCompleted", false)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean("OnboardingCompleted", completed ).apply()
    }

}
