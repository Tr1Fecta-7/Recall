package nl.recall.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DestinationsNavHost(navGraph = NavGraphs.root) }
    }
}
