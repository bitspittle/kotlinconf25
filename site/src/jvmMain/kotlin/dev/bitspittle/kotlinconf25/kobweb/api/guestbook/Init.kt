package dev.bitspittle.kotlinconf25.kobweb.api.guestbook

import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import dev.bitspittle.kotilnconf25.kobweb.model.GuestbookEntry

class Guestbook {
    val entries = mutableListOf<GuestbookEntry>()
}

@InitApi
fun initGuestbook(ctx: InitApiContext) {
    ctx.data.add(Guestbook())
}

