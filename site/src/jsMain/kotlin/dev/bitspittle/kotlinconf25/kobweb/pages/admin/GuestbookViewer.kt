package dev.bitspittle.kotlinconf25.kobweb.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.get
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.columnGap
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.grid
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotilnconf25.kobweb.model.GuestbookEntry
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Div

@Page
@Composable
fun GuestbookViewerPage() {
    var guestbookEntries by remember { mutableStateOf<List<GuestbookEntry>?>(null) }
    LaunchedEffect(Unit) {
        guestbookEntries = window.api.get<List<GuestbookEntry>>("/guestbook/entries")
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.gap(1.cssRem)) {
            Div(Modifier.grid {
                columns {
                    fitContent(100.percent)
                    fitContent(100.percent)
                }
            }.columnGap(1.cssRem).toAttrs()) {
                guestbookEntries?.last()?.let { lastGuestbookEntry ->
                    SpanText("Subject:"); SpanText(lastGuestbookEntry.subject)
                    SpanText("First Name:"); SpanText(lastGuestbookEntry.firstName)
                    SpanText("Last Name:"); SpanText(lastGuestbookEntry.lastName)
                    SpanText("Message:"); SpanText(lastGuestbookEntry.message)
                }
            }
            Link("/conclusion/header", "Proceed to Conclusion")
        }
    }
}
