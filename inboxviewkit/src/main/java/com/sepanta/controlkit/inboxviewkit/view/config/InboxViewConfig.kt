package com.inboxview.view.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.sepanta.controlkit.inboxviewkit.utils.FontSizeRange
import com.sepanta.controlkit.inboxviewkit.view.ui.model.DetailPageItemModel
import com.sepanta.controlkit.inboxviewkit.view.ui.model.InboxItemModel

data class InboxViewConfig(
    var viewStyle: InboxViewStyle = InboxViewStyle.FullScreen1,

    var popupViewLayoutModifier: Modifier? = null,
    var popupViewBackGroundColor: Color? = null,
    var popupViewCornerRadius: Dp? = null,
    var inboxItemHeight: Dp? = null,

    var inboxItemColumnLayoutModifier: Modifier? = null,
    var inboxItemRowLayoutModifier: Modifier? = null,
    var inboxItemColumnVerticalArrangement: Arrangement.Vertical? = null,
    var inboxItemColumnHorizontalAlignment: Alignment.Horizontal? = null,
    var inboxItemRowHorizontalArrangement: Arrangement.Horizontal? = null,
    var inboxItemRowVerticalAlignment: Alignment.Vertical? = null,
    var inboxItemArrowIcon: Int? = null,
    var inboxItemArrowColor: Color? = null,
    var inboxItemArrowModifier: Modifier? = null,

    var inboxItemTitleModifier: Modifier? = null,
    var inboxItemTitleFontSizeRange: FontSizeRange? = null,
    var inboxItemTitleTextStyle: TextStyle? = null,

    var inboxItemSubjectModifier: Modifier? = null,
    var inboxItemSubjectFontSizeRange: FontSizeRange? = null,
    var inboxItemSubjectTextStyle: TextStyle? = null,
    var inboxItemDateTextStyle: TextStyle? = null,

    var searchBoxLayoutModifier: Modifier? = null,
    var searchBoxIconLayoutModifier: Modifier? = null,
    var searchBoxTextFieldModifier: Modifier? = null,
    var searchBoxCornerRadius: Shape? = null,
    var searchBoxColor: Color? = null,

    var textFieldTextStyle: TextStyle? = null,
    var textFieldCornerRadius: Shape? = null,
    var textFieldColors: TextFieldColors? = null,

    var headerTitle: String = "Inbox",
    var headerLayoutModifier: Modifier? = null,
    var headerTitleColor: Color? = null,
    var headerTitleTextStyle: TextStyle? = null,

    var lineColor: Color? = null,
    var detailPageLineColor: Color? = null,
    var lineViewLayoutModifier: Modifier? = null,
    var detailPageLineViewLayoutModifier: Modifier? = null,

    var backButtonColor: Color? = null,
    var backButtonDrawable:  Int? = null,
    var backButtonModifier: Modifier? = null,
    var nextButtonModifier: Modifier? = null,
    var previousButtonModifier: Modifier? = null,

    var nextButtonActiveColor: Color? = null,
    var nextButtonColor: Color? = null,

    var previousButtonActiveColor: Color? = null,
    var previousButtonColor: Color? = null,

    var detailPagePopupViewLayoutModifier: Modifier? = null,
    var detailPagePopupViewBackGroundColor: Color? = null,
    var detailPagePopupViewCornerRadius: Dp? = null,


    var detailPageBackButtonModifier: Modifier? = null,

    var detailPageIndexViewBackGroundColor: Color? = null,
    var detailPageIndexViewCornerRadius: Dp? = null,
    var detailPageBackButtonColor: Color? = null,
    var detailPageTitleTextStyle: TextStyle? = null,
    var detailPageTitleModifier: Modifier? = null,

    var detailPageDateTextStyle: TextStyle? = null,
    var detailPageDateModifier: Modifier? = null,

    var detailPageTimeTextStyle: TextStyle? = null,
    var detailPageTimeModifier: Modifier? = null,

    var detailPageDescriptionTextStyle: TextStyle? = null,
    var detailPageDescriptionModifier: Modifier? = null,

    var headerTitleView: @Composable ((String) -> Unit)? = null,
    var detailPageIndexView: @Composable ((String) -> Unit)? = null,
    var detailPageTitleView: @Composable ((String) -> Unit)? = null,
    var detailPageDateView: @Composable ((String) -> Unit)? = null,
    var detailPageDescriptionView: @Composable ((String) -> Unit)? = null,
    var detailPageTimeView: @Composable ((String) -> Unit)? = null,
    var backButtonView: (@Composable (onClick: () -> Unit) -> Unit)? = null,
    var detailPageBackButtonView: (@Composable (onClick: () -> Unit) -> Unit)? = null,
    var inboxItemView: @Composable ((InboxItemModel,onClick: () -> Unit ) -> Unit)? = null,
    var detailPageView: @Composable ((DetailPageItemModel, onClick: () -> Unit ) -> Unit)? = null,

    var previousButtonView:  (@Composable (onClick: () -> Unit) -> Unit)? = null,
    var nextButtonView: (@Composable (onClick: () -> Unit) -> Unit)? = null,

    var lineView: @Composable (() -> Unit)? = null,
    var detailPageLineView: @Composable (() -> Unit)? = null,
    var inboxItemArrowView: @Composable (() -> Unit)? = null,
    var textFieldPlaceholderView: @Composable (() -> Unit)? = null,
    var searchBoxIconView: @Composable (() -> Unit)? = null,
)
