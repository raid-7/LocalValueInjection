package raid.neuroide.reproto

import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
    @Test
    fun basicSingle() {
        val m1 = SerializationManager("local1")
        val m2 = SerializationManager("local2")
        val serialized = m1.serialize(SomeClass(m1.context.wrapped()))

        assertEquals("local2", m2.deserialize(serialized).id.id)
    }


}
