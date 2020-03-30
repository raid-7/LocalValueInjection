package raid.neuroide.reproto


class ClientNode(site: String) {
    private val context = Context(site)
    private val processor = Processor()
    private val pSerializer = PrototypeSerializationManager(context)

    private lateinit var gateway: ClientGateway

    private var currentPrototype: Prototype? = null
    private var currentPrototypeId: String? = null
    private var requestedPrototypeId: String? = null
    private val pendingPrototypeCallbacks: MutableList<(Prototype?) -> Unit> = mutableListOf()

    fun getPrototype(id: String, callback: (Prototype?) -> Unit) {
        if (currentPrototypeId == id) {
            callback(currentPrototype)
        } else {
            if (requestedPrototypeId != id) {
                pendingPrototypeCallbacks.clear()
                requestedPrototypeId = id
                pendingPrototypeCallbacks.add(callback)
                gateway.loadAndSubscribe(id)
            } else {
                pendingPrototypeCallbacks.add(callback)
            }
        }
    }

    fun setGateway(g: ClientGateway) {
        g.setReceiver(processor)
        gateway = g
    }

    private fun receivePrototype(id: String, proto: Prototype) {
        if (id != requestedPrototypeId)
            return

        currentPrototype = proto
        currentPrototypeId = requestedPrototypeId
        requestedPrototypeId = null

        for (callback in pendingPrototypeCallbacks) {
            callback(proto)
        }
        pendingPrototypeCallbacks.clear()
    }

    private inner class Processor : PrototypeReceiver {
        override fun receivePrototype(id: String, proto: String) {
            val prototype = pSerializer.deserialize(proto)
            receivePrototype(id, prototype)
        }
    }

    private inner class Context private constructor(override val siteId: LocalSiteId) :
        NodeContext {
        private var idCounter: Int = 0

        constructor(site: String) : this(LocalSiteId(site))

        override fun issueId(): String {
            idCounter++
            return "${siteId.id}::${idCounter}"
        }
    }
}
