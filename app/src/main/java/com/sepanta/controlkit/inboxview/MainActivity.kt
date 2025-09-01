package com.sepanta.controlkit.inboxview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.inboxview.config.InboxViewServiceConfig
import com.inboxview.view.config.InboxViewConfig
import com.inboxview.view.config.InboxViewStyle
import com.sepanta.controlkit.inboxview.ui.theme.TestInboxViewTheme
import com.sepanta.controlkit.inboxviewkit.InboxViewKit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestInboxViewTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    InboxViewKit(
                        InboxViewServiceConfig(
                            version = "1",
                            appId = "9fb42682-ebd0-4553-a131-2620ca7f2f63",
                            deviceId = "dsd",
                            route = "https://tauri.ir/api/inbox-view",
                            viewConfig = InboxViewConfig(
                                InboxViewStyle.FullScreen1,
//                                backButtonView  = { onClick ->
//
//                                    Button(onClick = onClick) {
//
//                                    }
//                                }
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
                    ).Configure()
                }
            }
        }
    }
}
