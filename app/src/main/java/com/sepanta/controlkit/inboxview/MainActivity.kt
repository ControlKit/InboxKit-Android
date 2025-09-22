package com.sepanta.controlkit.inboxview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sepanta.controlkit.inboxviewkit.config.InboxViewServiceConfig
import com.sepanta.controlkit.inboxviewkit.view.config.InboxViewConfig
import com.inboxview.view.config.InboxViewStyle
import com.sepanta.controlkit.inboxview.ui.theme.TestInboxViewTheme
import com.sepanta.controlkit.inboxviewkit.InboxViewKit
import com.sepanta.controlkit.inboxviewkit.inboxViewKitHost
import com.sepanta.controlkit.inboxviewkit.theme.Black100
import com.sepanta.controlkit.inboxviewkit.theme.Black80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestInboxViewTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    color = Color.White
                ) {
                    val inboxViewKit = inboxViewKitHost(
                        InboxViewServiceConfig(
                            version = "1",
                            appId = "9fee1663-e80e-46ad-8cd9-357263375a9c",
                            viewConfig = InboxViewConfig(
                                InboxViewStyle.FullScreen1
                            )
                        ),
                        onDismiss = {

                        }
                    )

                    inboxViewKit.showView()
                }
            }
        }



    }
}
