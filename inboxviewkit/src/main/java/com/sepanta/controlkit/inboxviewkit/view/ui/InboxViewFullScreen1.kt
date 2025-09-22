package com.sepanta.controlkit.inboxviewkit.view.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.R
import com.sepanta.controlkit.inboxviewkit.service.model.InboxViewResponse
import com.sepanta.controlkit.inboxviewkit.theme.LocalMarginDimensions
import com.sepanta.controlkit.inboxviewkit.theme.LocalSizeDimensions
import com.sepanta.controlkit.inboxviewkit.utils.AutoResizeText
import com.sepanta.controlkit.inboxviewkit.utils.FontSizeRange
import com.inboxview.view.config.InboxContract
import com.sepanta.controlkit.inboxviewkit.view.config.InboxViewConfig
import com.sepanta.controlkit.inboxviewkit.view.ui.detailPage.ShowDetailView
import com.sepanta.controlkit.inboxviewkit.theme.Black100
import com.sepanta.controlkit.inboxviewkit.theme.Blue20
import com.sepanta.controlkit.inboxviewkit.theme.Gray10
import com.sepanta.controlkit.inboxviewkit.theme.Gray100
import com.sepanta.controlkit.inboxviewkit.theme.Gray80
import com.sepanta.controlkit.inboxviewkit.theme.Typography
import com.sepanta.controlkit.inboxviewkit.theme.White100
import com.sepanta.controlkit.inboxviewkit.theme.White20
import com.sepanta.controlkit.inboxviewkit.view.ui.model.InboxItemModel

class InboxViewFullScreen1 : InboxContract {

    @Composable
    override fun ShowView(
        config: InboxViewConfig, viewModel: InboxViewModel
    ) {
        val sizeDime = LocalSizeDimensions.current
        val items by viewModel.dataList.collectAsState()
        val openDialog = viewModel.openDialog.collectAsState()
        ShowDetailView(config, viewModel)
        if (!openDialog.value) return
        Dialog(
            onDismissRequest = { viewModel.dismissDialog() },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = false,
                usePlatformDefaultWidth = false
            )
        ) {

            Surface(
                modifier = config.popupViewLayoutModifier ?: Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(config.popupViewCornerRadius ?: sizeDime.default),
                color = config.popupViewBackGroundColor ?: Black100
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    val marginDim = LocalMarginDimensions.current

                    BackButon(config, viewModel)
                    Box(
                        config.headerLayoutModifier ?: Modifier
                            .padding(
                                top = marginDim.spaceMedium,
                            )
                            .fillMaxWidth()
                            .padding(start = marginDim.spaceMedium),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        HeaderTitle(config, items[0])
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(items) { index, item ->
                            InboxItem(item, index, config, viewModel)
                        }
                    }


                }

            }

        }

    }


    @Composable
    private fun BackButon(
        config: InboxViewConfig, viewModel: InboxViewModel
    ) {
        val sizeDime = LocalSizeDimensions.current
        val marginDim = LocalMarginDimensions.current

        val onClickAction: () -> Unit = {
            viewModel.dismissDialog()
        }

        config.backButtonView?.let { button ->
            button(onClickAction)
        } ?: Box(
            contentAlignment = Alignment.CenterStart,
            modifier = config.backButtonModifier ?: Modifier
                .padding(
                    top = marginDim.spaceXXSmall,
                )
                .fillMaxWidth()
                .padding(start = marginDim.spaceXSmall)

        ) {
            BackIcon(
                config, modifier = Modifier
                    .size(sizeDime.size30)
                    .clickable { onClickAction() })
        }


    }

    @Composable
    fun BackIcon(
        config: InboxViewConfig,
        modifier: Modifier,

        ) {

        if (config.backButtonDrawable != null) {
            Icon(
                painter = painterResource(
                    id = config.backButtonDrawable ?: R.drawable.ic_arrow_right
                ),
                tint = config.backButtonColor ?: White100,
                modifier = modifier,
                contentDescription = null
            )
        } else {
            Icon(
                Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                tint = config.backButtonColor ?: White100,
                modifier = modifier,
                contentDescription = null
            )
        }
    }

    @Composable
    private fun HeaderTitle(
        config: InboxViewConfig, response: InboxViewResponse
    ) {

        config.headerTitleView?.let { textView ->
            textView((response.title ?: config.headerTitle))
        } ?: Text(
            modifier = Modifier.wrapContentSize(),
            text = response.title ?: config.headerTitle,
            style = config.headerTitleTextStyle ?: Typography.titleLarge,
            color = config.headerTitleColor ?: Typography.titleLarge.color

        )


    }

    @SuppressLint("UnusedBoxWithConstraintsScope")

    @Composable
    private fun SearchBox(config: InboxViewConfig) {
        var text by rememberSaveable { mutableStateOf("") }

        val sizeDime = LocalSizeDimensions.current
        val marginDim = LocalMarginDimensions.current
        Surface(
            modifier = config.searchBoxLayoutModifier ?: Modifier
                .padding(
                    start = marginDim.spaceMedium,
                    end = marginDim.spaceXMedium,
                    top = marginDim.spaceXSmall,

                    )
                .height(sizeDime.size35),
            shape = config.searchBoxCornerRadius ?: RoundedCornerShape(sizeDime.size16),
            color = config.searchBoxColor ?: White20,

            ) {
            Row(Modifier.fillMaxSize()) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clipToBounds()
                ) {
                    TextField(
                        modifier = config.searchBoxTextFieldModifier ?: Modifier.requiredHeight(
                            maxHeight + sizeDime.size16
                        ),
                        value = text,
                        singleLine = true,
                        placeholder = {
                            config.textFieldPlaceholderView ?: Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Search items...",
                                color = Gray100,
                                style = Typography.titleSmall,
                                textAlign = TextAlign.Start

                            )
                        },
                        textStyle = config.textFieldTextStyle ?: Typography.labelLarge,
                        shape = config.textFieldCornerRadius ?: RoundedCornerShape(20.dp),
                        colors = config.textFieldColors ?: TextFieldDefaults.colors(
                            disabledTextColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorContainerColor = Color.Transparent
                        ),
                        onValueChange = {

                            text = it

                        },
                    )
                }
                Surface(
                    modifier = config.searchBoxIconLayoutModifier ?: Modifier
                        .fillMaxHeight()
                        .width(sizeDime.size52)
                        .clickable { },
                    color = Color.Transparent,

                    ) {
                    config.searchBoxIconView ?: Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Gray80,
                        modifier = Modifier
                            .padding(marginDim.spaceXExtraSmall)
                            .width(sizeDime.size20)
                            .height(sizeDime.size20),
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    private fun InboxItem(
        item: InboxViewResponse, index: Int, config: InboxViewConfig, viewModel: InboxViewModel
    ) {

        val sizeDime = LocalSizeDimensions.current
        val marginDim = LocalMarginDimensions.current
        val onClickAction: () -> Unit = {
            viewModel.showDetailPage(index)
        }

        config.inboxItemView?.let { view ->
            val model = InboxItemModel(
                title = item.title.toString(),
                date = item.date.toString(),
                description = item.description.toString()
            )
            view(model, onClickAction)
        } ?: Box(
            Modifier
                .fillMaxWidth()
                .height(config.inboxItemHeight ?: sizeDime.size96)
                .clickable {
                    onClickAction()
                }) {
            Column(
                config.inboxItemColumnLayoutModifier ?: Modifier.fillMaxSize(),
                verticalArrangement = config.inboxItemColumnVerticalArrangement ?: Arrangement.Top,
                horizontalAlignment = config.inboxItemColumnHorizontalAlignment ?: Alignment.Start

            ) {

                Row(
                    config.inboxItemRowLayoutModifier ?: Modifier.padding(
                        start = marginDim.spaceXXMedium,
                        top = marginDim.spaceSmall,
                    ),
                    verticalAlignment = config.inboxItemRowVerticalAlignment ?: Alignment.Top,
                    horizontalArrangement = config.inboxItemRowHorizontalArrangement
                        ?: Arrangement.Start
                ) {

                    AutoResizeText(
                        text = item.title.toString(),
                        maxLines = 1,
                        modifier = config.inboxItemTitleModifier ?: Modifier.padding(
                            end = marginDim.spaceXXExtraSmall,
                        ),
                        fontSizeRange = config.inboxItemTitleFontSizeRange ?: FontSizeRange(
                            min = 14.sp,
                            max = 16.sp,
                        ),
                        overflow = TextOverflow.Visible,
                        style = config.inboxItemTitleTextStyle ?: Typography.bodyMedium,
                        textAlign = config.inboxItemTitleTextStyle?.textAlign ?: TextAlign.Center,
                        fontWeight = config.inboxItemTitleTextStyle?.fontWeight ?: FontWeight.Bold,
                        color = config.inboxItemTitleTextStyle?.color ?: Blue20
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = item.date.toString(),
                        style = config.inboxItemDateTextStyle ?: Typography.bodySmall,
                        color = config.inboxItemDateTextStyle?.color ?: Gray80,
                        fontWeight = config.inboxItemDateTextStyle?.fontWeight ?: FontWeight.Normal,
                        fontSize = config.inboxItemDateTextStyle?.fontSize ?: 12.sp
                    )

                    config.inboxItemArrowView ?: Icon(
                        painter = painterResource(
                            id = config.inboxItemArrowIcon ?: R.drawable.ic_arrow_right
                        ),
                        tint = config.inboxItemArrowColor ?: Color.Unspecified,
                        modifier = config.inboxItemArrowModifier ?: Modifier
                            .padding(
                                end = marginDim.spaceXMedium, start = marginDim.spaceSmall
                            )
                            .height(sizeDime.size16)
                            .width(sizeDime.size10),
                        contentDescription = null
                    )
                }


                AutoResizeText(
                    text = item.description.toString(),
                    maxLines = 1,
                    modifier = config.inboxItemSubjectModifier ?: Modifier.padding(
                        end = marginDim.spaceXXExtraSmall,
                        start = marginDim.spaceXXMedium,
                        top = marginDim.spaceSmall,

                        ),
                    fontSizeRange = config.inboxItemSubjectFontSizeRange ?: FontSizeRange(
                        min = 13.sp,
                        max = 15.sp,
                    ),
                    overflow = TextOverflow.Visible,
                    style = config.inboxItemSubjectTextStyle ?: Typography.bodyMedium,
                    textAlign = config.inboxItemSubjectTextStyle?.textAlign ?: TextAlign.Center,
                    fontWeight = config.inboxItemSubjectTextStyle?.fontWeight
                        ?: FontWeight.SemiBold,
                    color = config.inboxItemSubjectTextStyle?.color ?: Gray80,

                    )
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    config.lineViewLayoutModifier ?: Modifier.fillMaxWidth()

                ) {
                    config.lineView ?: HorizontalDivider(
                        modifier = Modifier
                            .padding(
                                end = marginDim.spaceXMedium, start = marginDim.spaceXXSmall

                            )
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        thickness = DividerDefaults.Thickness,
                        color = config.lineColor ?: Gray10
                    )
                }
            }
        }
    }

}