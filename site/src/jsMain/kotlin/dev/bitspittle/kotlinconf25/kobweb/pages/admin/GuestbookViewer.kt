package dev.bitspittle.kotlinconf25.kobweb.pages.admin

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.get
import com.varabyte.kobweb.compose.css.ColumnSpan
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.columnGap
import com.varabyte.kobweb.compose.ui.modifiers.columnSpan
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.gridColumn
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import dev.bitspittle.kotilnconf25.kobweb.model.GuestbookEntries
import kotlinx.browser.window
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Br

@Page
@Composable
fun GuestbookViewerPage() {
    var guestbookEntries by remember { mutableStateOf<GuestbookEntries?>(null) }
    LaunchedEffect(Unit) {
        guestbookEntries = window.api.get<GuestbookEntries>("/guestbook/entries")
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(Modifier.gap(1.cssRem)) {
            SimpleGrid(numColumns(2), Modifier.columnGap(1.cssRem)) {
                guestbookEntries?.items?.last()?.let { lastGuestbookEntry ->
                    SpanText("Subject:"); SpanText(lastGuestbookEntry.subject)
                    SpanText("First Name:"); SpanText(lastGuestbookEntry.firstName)
                    SpanText("Last Name:"); SpanText(lastGuestbookEntry.lastName)
                    SpanText("Message:"); SpanText(lastGuestbookEntry.message)
                }
            }
            Link("/conclusion/header", "Conclusion")
        }
    }
}
