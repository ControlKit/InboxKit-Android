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
import com.inboxview.view.config.InboxViewConfig
import com.inboxview.view.config.InboxViewStyle
import com.sepanta.controlkit.inboxview.ui.theme.TestInboxViewTheme
import com.sepanta.controlkit.inboxviewkit.InboxViewKit
import com.sepanta.controlkit.inboxviewkit.theme.Black100
import com.sepanta.controlkit.inboxviewkit.theme.Black80
lateinit var inboxViewKit: InboxViewKit

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
                    Column {
                        Example()
                    }
                }
            }
        }



    }
}
@Composable
fun TextField(text: MutableState<String>) {


    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text("platformId", color = Black100) },
        placeholder = { Text("Enter your platformId") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "User icon",
                tint = Black80
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = Black100,
            unfocusedTextColor = Black100,
            focusedPlaceholderColor = Black80
        )
    )
}

@Composable
fun Example(){
    val showDialog = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("9fb42682-ebd0-4553-a131-2620ca7f2f63") }


    Column(Modifier.fillMaxSize()) {

        TextField(text)

       Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                showDialog.value = true
            },
            modifier = Modifier
                .padding(10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .height(46.dp)
        ) {
            Text("show InboxView")
        }
    }

    inboxViewKit= InboxViewKit(
        InboxViewServiceConfig(
            version = "1",
            appId = text.value,
            deviceId = "dsd",
            route = "https://tauri.ir/api/inbox-view",
            viewConfig = InboxViewConfig(
                InboxViewStyle.FullScreen1,
                /*          backButtonView  = { onClick ->

                              Button(onClick = onClick) {

                              }
                          }*/
                /*                inboxItemView = { data, onClick ->

                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .background(Black80)
                                            .clickable { onClick() }) {
                                        Text(
                                            data.title,
                                            style = Typography.bodySmall,
                                            color = Gray80,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp


                                        )
                                        Text(
                                            data.date,
                                            style = Typography.bodySmall,
                                            color = Gray80,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            data.description,
                                            style = Typography.bodySmall,
                                            color = Gray80,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp
                                        )
                                    }


                                }*/
            )

        )
    )

    if (showDialog.value) {
        inboxViewKit.Configure(
            onDismiss = {
                showDialog.value = false

            },
        )
    }
}