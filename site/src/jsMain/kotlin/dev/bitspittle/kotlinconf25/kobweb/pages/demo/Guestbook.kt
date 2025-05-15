package dev.bitspittle.kotlinconf25.kobweb.pages.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.icons.fa.FaArrowRightFromBracket
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
private fun LabeledInput(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxWidth().position(Position.Relative)) {
        Input(
            InputType.Text, value, onValueChange,
            Modifier.fillMaxWidth().then(modifier),
            placeholder = label
        )
        SpanText(
            label,
            Modifier
                .position(Position.Absolute).left(0.px).top(((-1).cssRem))
                .fontSize(0.7.em).color(Colors.Gray)
        )
    }
}

//@Page
//@Composable
//fun GuestbookPage() {
//    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Text("TODO: Guestbook form")
//    }
//}


//@Page
//@Composable
//fun GuestbookPage() {
//    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Column(Modifier.gap(2.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
//            SpanText("Contact Me:", Modifier.align(Alignment.Start))
//            SimpleGrid(numColumns(2), Modifier.gap(1.cssRem)) {
//                LabeledInput("First Name", "", onValueChange = {})
//                LabeledInput("Last Name", "", onValueChange = {})
//            }
//            LabeledInput("Subject", "", onValueChange = {})
//            LabeledInput("Message", "", onValueChange = {})
//            Button(
//                onClick = {
//                    // TODO: Revisit this later in the talk
//                }
//            ) {
//                SpanText("Send message ")
//                FaArrowRightFromBracket()
//            }
//        }
//    }
//}



@Page
@Composable
fun GuestbookPage() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.gap(2.cssRem), horizontalAlignment = Alignment.CenterHorizontally) {
            var firstName by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var subject by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }

            SpanText("Contact Me:", Modifier.align(Alignment.Start))
            SimpleGrid(numColumns(2), Modifier.gap(1.cssRem)) {
                LabeledInput("First Name", firstName, onValueChange = { firstName = it })
                LabeledInput("Last Name", lastName, onValueChange = { lastName = it })
            }
            LabeledInput("Subject", subject, onValueChange = { subject = it })
            LabeledInput("Message", message, onValueChange = { message = it })

            val allValuesFilledOut = firstName.isNotBlank() && lastName.isNotBlank() && subject.isNotBlank() && message.isNotBlank()

            Button(
                onClick = {
                    // TODO: Revisit this later in the talk
                },
                enabled = allValuesFilledOut,
            ) {
                SpanText("Send message ")
                FaArrowRightFromBracket()
            }

            if (!allValuesFilledOut) {
                Tooltip(ElementTarget.PreviousSibling, "All fields must be filled out before you can send your message.")
            }

            Link("http://localhost:8080/kobweb/basics/page-exercise#999", "(Back to the talk!)")
        }
    }
}