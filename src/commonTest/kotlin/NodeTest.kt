package raid.neuroide.reproto

import kotlin.test.Test
import kotlin.test.assertTrue

class NodeTest {
    private val server = MockServer()

    @Test
    fun basicSingle() {
        val node = node()
        var ready = false
        node.getPrototype("test") {
            ready = it != null
        }
        assertTrue(ready)
    }

    private fun node() =
        ClientNode(server.context.issueId()).apply {
            setGateway(server)
        }
}
