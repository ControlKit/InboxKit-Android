package com.inboxview.view.config

import com.inboxview.view.ui.InboxViewFullScreen1


enum class InboxViewStyle {
    FullScreen1,
    Popover1;
    companion object {
        fun checkViewStyle(style: InboxViewStyle): InboxContract {
            return when (style) {
                FullScreen1 -> InboxViewFullScreen1()
                Popover1 -> InboxViewFullScreen1()
            }

        }

    }

}
