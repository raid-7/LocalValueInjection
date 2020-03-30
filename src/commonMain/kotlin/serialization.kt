@file:OptIn(kotlinx.serialization.UnstableDefault::class)

package raid.neuroide.reproto

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.SerializersModule


private fun getSerialModuleForContext(context: Context) =
            SerializersModule {
                contextual(Context::class,
                    ContextualInjectorSerializer(context)
                )
            }

class SerializationManager(site: String) {
    private val context = Context(site)
    private val module = getSerialModuleForContext(context)

    fun serialize(prototype: SomeClass): String {
        return getJson().stringify(SomeClass.serializer(), prototype)
    }

    fun deserialize(data: String): SomeClass {
        return getJson().parse(SomeClass.serializer(), data)
    }

    private fun getJson() = Json(JsonConfiguration.Stable, module)
}
