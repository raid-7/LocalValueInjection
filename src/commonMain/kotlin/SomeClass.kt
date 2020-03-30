package raid.neuroide.reproto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class SomeClass(private val context: Context) {
    val remoteId = context?.siteId

    @Transient
    val localId = context.siteId
}
