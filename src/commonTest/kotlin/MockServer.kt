package raid.neuroide.reproto

class MockServer : ClientGateway {
    val context = Context().wrapped()
    private val receivers: MutableList<PrototypeReceiver> = mutableListOf()
    private val prototypes: MutableMap<String, String> = mutableMapOf()

    private val pSerializer = PrototypeSerializationManager(context)

    override fun setReceiver(receiver: PrototypeReceiver) {
        receivers.add(receiver)
    }

    override fun loadAndSubscribe(id: String) {
        val proto = getPrototype(id)
        for (receiver in receivers) {
            receiver.receivePrototype(id, proto)
        }
    }

    private fun getPrototype(id: String) =
        prototypes.getOrPut(id) {
            pSerializer.serialize(Prototype(context))
        }

    private inner class Context : NodeContext {
        private var idCounter = 0

        override val siteId = LocalSiteId("")

        override fun issueId(): String {
            idCounter++
            return "$idCounter"
        }
    }
}