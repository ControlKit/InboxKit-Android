package com.sepanta.controlkit.inboxviewkit.view.ui.detailPage

import android.view.Window
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.sepanta.controlkit.inboxviewkit.R
import com.sepanta.controlkit.inboxviewkit.view.viewModel.InboxViewModel
import com.sepanta.controlkit.inboxviewkit.theme.LocalMarginDimensions
import com.sepanta.controlkit.inboxviewkit.theme.LocalSizeDimensions
import com.inboxview.view.config.InboxViewConfig
import com.sepanta.controlkit.inboxviewkit.theme.Black100
import com.sepanta.controlkit.inboxviewkit.theme.Blue80
import com.sepanta.controlkit.inboxviewkit.theme.Gray10
import com.sepanta.controlkit.inboxviewkit.theme.Gray20
import com.sepanta.controlkit.inboxviewkit.theme.Gray60
import com.sepanta.controlkit.inboxviewkit.theme.Typography
import com.sepanta.controlkit.inboxviewkit.theme.White100
import com.sepanta.controlkit.inboxviewkit.theme.White70
import com.sepanta.controlkit.inboxviewkit.view.ui.model.DetailPageItemModel

@Composable
fun ShowDetailView(
    config: InboxViewConfig, viewModel: InboxViewModel
) {
    val sizeDime = LocalSizeDimensions.current
    val openDialog by viewModel.showDetailPage.collectAsState()

    @ReadOnlyComposable
    @Composable
    fun getDialogWindow(): Window? = (LocalView.current.parent as? DialogWindowProvider)?.window

    if (!openDialog) return
    Dialog(
        onDismissRequest = {
            viewModel.setShowDetailPage(false)
        }, properties = DialogProperties(
            dismissOnClickOutside = true, dismissOnBackPress = true, usePlatformDefaultWidth = false
        )
    ) {

        val onClickAction: () -> Unit = {


        }
        val dialogWindow = getDialogWindow()
        val currentModel by viewModel.currentModel.collectAsState()
        val currentIndex by viewModel.currentIndex.collectAsState()

        SideEffect {
            dialogWindow.let { window ->
                window?.setDimAmount(1f)
                window?.setWindowAnimations(-1)
            }
        }
        val marginDim = LocalMarginDimensions.current
        config.detailPageView?.let { view ->
            val model = DetailPageItemModel(
                title = currentModel.title.toString(),
                date = currentModel.date.toString(),
                time = currentModel.time.toString(),
                description = currentModel.description.toString(),
            )
            view(model, onClickAction)
        } ?: Surface(
            modifier = config.detailPagePopupViewLayoutModifier ?: Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(config.detailPagePopupViewCornerRadius ?: sizeDime.default),
            color = config.detailPagePopupViewBackGroundColor ?: Black100
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row(

                    Modifier
                        .padding(
                            top = marginDim.spaceXXSmall,
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    BackButton(config, viewModel)
                    config.detailPageIndexView?.let { view ->
                        view("${currentIndex + 1}")
                    } ?: Surface(
                        modifier = Modifier
                            .width(sizeDime.size45)
                            .height(sizeDime.size22),

                        shape = RoundedCornerShape(
                            config.detailPageIndexViewCornerRadius ?: sizeDime.size10
                        ), color = config.detailPageIndexViewBackGroundColor ?: Blue80
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "${currentIndex + 1}",
                                textAlign = TextAlign.Center,
                                style = Typography.bodySmall,
                                color = White100,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    PreviousButton(config, viewModel)

                    NextButton(config, viewModel)

                }



                config.detailPageTitleView?.let { textView ->
                    textView(currentModel.title.toString())
                } ?: Text(
                    modifier = config.detailPageTitleModifier ?: Modifier.padding(
                        start = marginDim.spaceMedium, top = marginDim.spaceXMedium
                    ),
                    text = currentModel.title.toString(),
                    style = config.detailPageTitleTextStyle ?: Typography.bodySmall,
                    color = config.detailPageTitleTextStyle?.color ?: White100,
                    fontWeight = config.detailPageTitleTextStyle?.fontWeight ?: FontWeight.Bold,
                    fontSize = config.detailPageTitleTextStyle?.fontSize ?: 14.sp
                )

                Row(
                    modifier = config.detailPageDateModifier ?: Modifier.padding(
                        start = marginDim.spaceMedium, top = marginDim.spaceXSmall
                    )
                ) {
                    config.detailPageDateView?.let { textView ->
                        textView(currentModel.date.toString())
                    } ?: Text(
                        text = currentModel.date.toString(),
                        style = config.detailPageDateTextStyle ?: Typography.bodySmall,
                        color = config.detailPageDateTextStyle?.color ?: Gray60,
                        fontWeight = config.detailPageDateTextStyle?.fontWeight
                            ?: FontWeight.Normal,
                        fontSize = config.detailPageDateTextStyle?.fontSize ?: 11.sp
                    )
                    config.detailPageTimeView?.let { textView ->
                        textView(currentModel.time.toString())
                    } ?: Text(
                        modifier = config.detailPageTimeModifier ?: Modifier.padding(
                            start = marginDim.spaceMedium,
                        ),
                        text = currentModel.time.toString(),
                        style = config.detailPageTimeTextStyle ?: Typography.bodySmall,
                        color = config.detailPageTimeTextStyle?.color ?: Gray60,
                        fontWeight = config.detailPageTimeTextStyle?.fontWeight
                            ?: FontWeight.Normal,
                        fontSize = config.detailPageTimeTextStyle?.fontSize ?: 11.sp
                    )

                }
                Box(
                    config.detailPageLineViewLayoutModifier ?: Modifier
                        .fillMaxWidth()
                        .padding(
                            top = marginDim.spaceXSmall
                        )

                ) {
                    config.detailPageLineView ?: HorizontalDivider(
                        modifier = Modifier
                            .padding(
                                end = marginDim.spaceXMedium, start = marginDim.spaceMedium

                            )
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        thickness = sizeDime.size1,
                        color = config.detailPageLineColor ?: Gray10
                    )
                }
                config.detailPageDescriptionView?.let { textView ->
                    textView(currentModel.description.toString())
                } ?: Text(
                    modifier = config.detailPageDescriptionModifier ?: Modifier.padding(
                        start = marginDim.spaceMedium, top = marginDim.spaceXSmall
                    ),
                    text = currentModel.description.toString(),
                    style = config.detailPageDescriptionTextStyle ?: Typography.bodySmall,
                    color = config.detailPageDescriptionTextStyle?.color ?: White70,
                    fontWeight = config.detailPageDescriptionTextStyle?.fontWeight
                        ?: FontWeight.Bold,
                    fontSize = config.detailPageDescriptionTextStyle?.fontSize ?: 12.sp
                )

            }
        }


    }
}

@Composable
private fun BackButton(
    config: InboxViewConfig, viewModel: InboxViewModel

) {
    val sizeDime = LocalSizeDimensions.current
    val marginDim = LocalMarginDimensions.current

    val onClickAction: () -> Unit = {
        viewModel.setShowDetailPage(false)
    }

    config.detailPageBackButtonView?.let { button ->
        button(onClickAction)
    } ?: Box(
        contentAlignment = Alignment.CenterStart,
        modifier = config.detailPageBackButtonModifier
            ?: Modifier.padding(start = marginDim.spaceXSmall)

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
            tint = config.detailPageBackButtonColor ?: White100,
            modifier = modifier,
            contentDescription = null
        )
    } else {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            tint = config.detailPageBackButtonColor ?: White100,
            modifier = modifier,
            contentDescription = null
        )
    }
}

@Composable
private fun NextButton(
    config: InboxViewConfig,
    viewModel: InboxViewModel,
) {

    val sizeDime = LocalSizeDimensions.current
    val marginDim = LocalMarginDimensions.current

    val nextButton by viewModel.nextButtonState.collectAsState()
    val onClickAction: () -> Unit = {
        viewModel.next()
    }

    config.nextButtonView?.let { button ->
        button(onClickAction)
    } ?: Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            tint = if (nextButton) config.nextButtonActiveColor
                ?: Blue80 else config.nextButtonColor ?: Gray20,
            modifier = config.nextButtonModifier?.clickable {
                onClickAction()
            } ?: Modifier
                .padding(
                    end = marginDim.spaceXSmall
                )
                .clickable {
                    onClickAction()
                }
                .rotate(-90f)
                .size(sizeDime.size28),
            contentDescription = null)
    }

}

@Composable
private fun PreviousButton(
    config: InboxViewConfig,
    viewModel: InboxViewModel,
) {
    val sizeDime = LocalSizeDimensions.current
    val marginDim = LocalMarginDimensions.current

    val previousButton by viewModel.previousButtonState.collectAsState()
    val onClickAction: () -> Unit = {
        viewModel.previous()
    }

    config.previousButtonView?.let { button ->
        button(onClickAction)
    } ?: Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            tint = if (previousButton) config.previousButtonActiveColor
                ?: Blue80 else config.previousButtonColor ?: Gray20,
            modifier = config.previousButtonModifier?.clickable {
                onClickAction()
            } ?: Modifier
                .padding(
                    start = marginDim.spaceXSmall
                )
                .rotate(90f)
                .size(sizeDime.size28)
                .clickable {
                    onClickAction()
                },
            contentDescription = null
        )
    }

}