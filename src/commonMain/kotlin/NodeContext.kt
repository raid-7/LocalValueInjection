package raid.neuroide.reproto

import kotlinx.serialization.ContextSerializer
import kotlinx.serialization.Serializable

interface NodeContext {
    val siteId: String
}

@OptIn(kotlinx.serialization.ImplicitReflectionSerializer::class)
@Serializable(with = ContextSerializer::class)
class NodeContextWrapper(impl: NodeContext) : NodeContext by impl

fun NodeContext.wrapped(): NodeContextWrapper =
    if (this is NodeContextWrapper) this else NodeContextWrapper(this)
