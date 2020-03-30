package raid.neuroide.reproto

import kotlinx.serialization.ContextSerializer
import kotlinx.serialization.Serializable

@OptIn(kotlinx.serialization.ImplicitReflectionSerializer::class)
@Serializable(with = ContextSerializer::class)
data class LocalSiteId(val id: String)
