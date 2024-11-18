package com.poc.more

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.poc.datastore.settings.AppSettings
import com.poc.datastore.settings.AppSettingsSerializer
import com.poc.datastore.settings.Language
import com.poc.datastore.settings.Location
import com.poc.more.ui.theme.MoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var appSettingsDataStore: DataStore<AppSettings>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appSettingsDataStore = DataStoreFactory.create(
            serializer = AppSettingsSerializer(),
            produceFile = { dataStoreFile("app_settings.json") },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

        enableEdgeToEdge()
        setContent {
            MoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        modifier = Modifier.padding(innerPadding),
//                    )
                    SettingsScreen(appSettingsDataStore, modifier = Modifier.padding(innerPadding))

                }
            }
        }

        val counter = 100

        println(counter)
    }
}

@Composable
fun SettingsScreen(appSettingsDataStore: DataStore<AppSettings>, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val appSettings by appSettingsDataStore.data.collectAsState(initial = AppSettings())

    Column(modifier = Modifier.padding(50.dp)) {
        Text(text = "Language: " + appSettings.language)
        val newLocation = Location(37.4221, -122.0841)
        DropdownMenu(
            expanded = true,
            onDismissRequest = {},
        ) {
            Language.entries.forEach { language ->
                DropdownMenuItem(text = { Text(text = language.name) }, onClick = {
                    scope.launch {
                        appSettingsDataStore.updateData { currentSettings ->
                            currentSettings.copy(
                                language = language,
                                lastKnownLocations = currentSettings.lastKnownLocations.add(
                                    newLocation,
                                ),
                            )
                        }
                    }
                })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Last Known Locations:")

        appSettings.lastKnownLocations.forEach { location ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Lat: ${location.lat}, Long: ${location.lng}")
        }
    }
}

@Composable
@Suppress("FunctionNaming")
fun Greeting(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Base Url: ${BuildConfig.BASE_URL}!",
            modifier = modifier,
        )
        Text(
            text = "DB Version: ${BuildConfig.DB_VERSION}!",
            modifier = modifier,
        )
        Text(
            text = "Can Clear Cache: ${BuildConfig.CAN_CLEAR_CACHE}!",
            modifier = modifier,
        )
        Text(
            text = "Map Key: ${BuildConfig.MAP_KEY}!",
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("FunctionNaming")
fun GreetingPreview() {
    MoreTheme {
        Greeting()
    }
}