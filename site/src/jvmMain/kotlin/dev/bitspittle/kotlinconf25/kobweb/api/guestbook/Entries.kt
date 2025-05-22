package dev.bitspittle.kotlinconf25.kobweb.api.guestbook

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.readBody
import com.varabyte.kobweb.api.http.setBody
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

@Api
fun entries(ctx: ApiContext) {
    if (ctx.req.method == HttpMethod.POST) {
        ctx.req.readBody<GuestbookEntry>()?.let { entry ->
            ctx.data.getValue<Guestbook>().entries.add(entry)
            ctx.res.status = 200
        }
    }
    else if (ctx.req.method == HttpMethod.GET) {
       ctx.res.setBody(ctx.data.getValue<Guestbook>().entries)
   }
}
