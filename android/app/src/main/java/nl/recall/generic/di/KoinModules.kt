package nl.recall.generic.di

import android.content.Context
import android.content.SharedPreferences
import nl.recall.data.DataModule
import nl.recall.domain.DomainModule
import nl.recall.presentation.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.ksp.generated.module

val appModule = module {
    // Uncomment after creating a binding annotation in the app module
    // Koin does not generate this file before creating a binding
    //includes(AppModule().module)
}

val domainModule = module {
    // Uncomment after creating a binding annotation in the domain module
    // Koin does not generate this file before creating a binding
    includes(DomainModule().module)
}

val presentationModule = module {
//     Uncomment after creating a binding annotation in the presentation module
//     Koin does not generate this file before creating a binding
   includes(PresentationModule().module)
}

val dataModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("OnboardingPrefs", Context.MODE_PRIVATE)
    }

    includes(DataModule().module)
}