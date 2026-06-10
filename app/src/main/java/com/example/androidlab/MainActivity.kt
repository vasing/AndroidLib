package com.example.androidlab
import android.os.Bundle // For passing data between Android activities and saving state
import androidx.activity.compose.setContent // Binds a Compose UI layout to the activity
import androidx.activity.enableEdgeToEdge // Enables full-screen layout drawing behind system bars
import androidx.compose.foundation.layout.fillMaxSize // Modifier to make a component expand to maximum size
import androidx.compose.foundation.layout.padding // Modifier to add spacing/margins around a component
import androidx.compose.material3.Icon // Material Design component used to display vector graphics or symbols
import androidx.compose.material3.Scaffold // Layout structure providing slots for top bars, snackbars, and FABs
import androidx.compose.material3.Text // Basic Material Design component for displaying text on screen
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold // Adaptive layout that switches between navigation bar and rail based on screen size
import androidx.compose.runtime.Composable // Annotation that marks a function as a UI-generating block
import androidx.compose.runtime.getValue // Extension operator property to read state cleanly without .value
import androidx.compose.runtime.mutableStateOf // Creates a reactive, observable state holder that triggers recomposition when changed
import androidx.compose.runtime.saveable.rememberSaveable // Retains state across configuration changes like screen rotations
import androidx.compose.runtime.setValue // Extension operator property to write to state cleanly without .value
import androidx.compose.ui.Modifier // Ordered collection of elements used to decorate or style UI components
import androidx.compose.ui.res.painterResource // Loads drawable or vector assets from the project resources
import androidx.compose.ui.tooling.preview.Preview // Annotation to view individual Composable components in the IDE design tab
import androidx.compose.ui.tooling.preview.PreviewScreenSizes // Multi-preview annotation to test UI layouts on multiple device sizes at once
import androidx.compose.ui.viewinterop.AndroidViewBinding // Allows embedding XML View Binding layouts inside Compose layouts
import androidx.fragment.app.FragmentActivity // Base class for activities that need to support and manage legacy fragments
import androidx.fragment.app.commit // Inline extension for executing fragment transactions (add, replace) safely
import com.example.androidlab.databinding.FragmentContainerBinding // Generated class for interacting with the XML fragment container layout
import com.example.androidlab.databinding.FragmentProfileBinding // Generated class for interacting with the XML profile layout
import com.example.androidlab.ui.home.HomeFragment // Custom fragment class representing the application's home screen
import com.example.androidlab.ui.theme.AndroidLabTheme // Custom theme wrapper applying specific colors, shapes, and typography to the app

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidLabTheme {
                AndroidLabApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun AndroidLabApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (currentDestination) {
                AppDestinations.HOME -> {
                    AndroidViewBinding(
                        factory = FragmentContainerBinding::inflate,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        val fragmentManager = (root.context as FragmentActivity).supportFragmentManager
                        if (fragmentManager.findFragmentById(fragmentContainerView.id) == null) {
                            fragmentManager.commit {
                                replace(fragmentContainerView.id, HomeFragment())
                            }
                        }
                    }
                }
                AppDestinations.PROFILE -> {
                    AndroidViewBinding(
                        factory = FragmentProfileBinding::inflate,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                else -> {
                    Greeting(
                        name = currentDestination.label,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    FAVORITES("Favorites", R.drawable.ic_favorite),
    PROFILE("Profile", R.drawable.ic_account_box),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLabTheme {
        Greeting("Android")
    }
}
