package raid.neuroide.reproto

import kotlinx.serialization.Serializable

@Serializable
class SomeClass constructor(private val context: NodeContextWrapper) {
    val id = context.siteId
}
