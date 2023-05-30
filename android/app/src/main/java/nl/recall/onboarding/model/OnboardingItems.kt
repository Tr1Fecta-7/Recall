package nl.recall.onboarding.model

import nl.recall.R

class OnboardingItems(
    val image: Int,
    val title: Int,
    val description: Int,
    val indicatorText: Int
) {
    companion object{
        fun getData(): List<OnboardingItems>{
            return listOf(
                OnboardingItems(R.drawable.first_onboarding, R.string.onboarding_welcome_title_text, R.string.onboarding_welcome_subtitle_text, R.string.onboarding_welcome_indicator_text),
                OnboardingItems(R.drawable.second_onboarding, R.string.onboarding_create_study_title_text, R.string.onboarding_create_study_subtitle_text, R.string.onboarding_create_study_indicator_text),
                OnboardingItems(R.drawable.third_onboarding, R.string.onboarding_discover_title_text, R.string.onboarding_discover_subtitle_text, R.string.onboarding_discover_indicator_text)
            )
        }
    }
}