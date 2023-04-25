package nl.recall.generic.di

import nl.recall.data.DataModule
import nl.recall.domain.DomainModule
import nl.recall.presentation.PresentationModule
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
    includes(DataModule().module)
}