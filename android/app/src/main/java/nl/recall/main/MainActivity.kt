package nl.recall.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import nl.recall.NavGraphs
import nl.recall.theme.AndroidAppTheme
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidAppTheme {
                val navEngine = rememberNavHostEngine()
                val navController = navEngine.rememberNavController()

                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    engine = navEngine,
                    navController = navController,
                )
            }
        }
    }
}
