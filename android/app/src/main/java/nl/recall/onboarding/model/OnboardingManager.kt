package nl.recall.onboarding.model

import android.content.Context
import androidx.core.content.edit

object OnboardingManager {
    private const val PREFS_NAME = "OnboardingPrefs"
    private const val PREFS_KEY_ONBOARDING_COMPLETED = "OnboardingCompleted"

    fun isOnboardingCompleted(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREFS_KEY_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean(PREFS_KEY_ONBOARDING_COMPLETED, true)
        }
    }
}