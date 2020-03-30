@file:OptIn(kotlinx.serialization.UnstableDefault::class)

package raid.neuroide.reproto

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerializersModule


private fun getSerialModuleForContext(context: NodeContext) =
            SerializersModule {
                contextual(NodeContextWrapper::class,
                    ContextualInjectorSerializer(context.wrapped())
                )
                contextual(LocalSiteId::class,
                    ContextualInjectorSerializer(context.siteId)
                )
            }

internal class PrototypeSerializationManager(private val context: NodeContext) {
    private val module = getSerialModuleForContext(context)

    fun serialize(prototype: Prototype): String {
        return getJson().stringify(Prototype.serializer(), prototype)
    }

    fun deserialize(data: String): Prototype {
        return getJson().parse(Prototype.serializer(), data)
    }

    private fun getJson() = Json(JsonConfiguration.Stable.copy(classDiscriminator = "_type"), module)
}
