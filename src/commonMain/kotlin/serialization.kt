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
            }

class SerializationManager(site: String) {
    val context: NodeContext = Context(site)
    private val module = getSerialModuleForContext(context)

    fun serialize(prototype: SomeClass): String {
        return getJson().stringify(SomeClass.serializer(), prototype)
    }

    fun deserialize(data: String): SomeClass {
        return getJson().parse(SomeClass.serializer(), data)
    }

    private fun getJson() = Json(JsonConfiguration.Stable, module)

    private inner class Context(override val siteId: String) : NodeContext
}
