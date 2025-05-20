package dev.bitspittle.kotilnconf25.kobweb.model

import kotlinx.serialization.Serializable

@Serializable
class GuestbookEntry(
    val firstName: String,
    val lastName: String,
    val subject: String,
    val message: String,
)
