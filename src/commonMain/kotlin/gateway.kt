package raid.neuroide.reproto

interface ClientGateway {
    fun loadAndSubscribe(id: String)
    fun setReceiver(receiver: PrototypeReceiver)
}

interface PrototypeReceiver {
    fun receivePrototype(id: String, proto: String)
}
