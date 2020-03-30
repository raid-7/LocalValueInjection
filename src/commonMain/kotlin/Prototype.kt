package raid.neuroide.reproto

import kotlinx.serialization.Serializable

@Serializable
class Prototype constructor(private val context: NodeContextWrapper) {
    private val layer = Layer(context.siteId)
}
