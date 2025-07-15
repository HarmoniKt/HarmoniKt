package it.unibo.harmonikt.utils

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import kotlin.uuid.Uuid

/**
 * Utility object for registering services with Consul.
 * This object provides a method to register a service with the Consul agent.
 */
object ConsulRegisterService {
    /**
     * Registers a service with Consul.
     *
     * @param consulUrl The URL of the Consul server.
     * @param serviceName The name of the service to register.
     * @param port The port on which the service is running.
     */
    fun registerConsulService(consulUrl: String, serviceName: String, address: String, port: Int) {
        val consulClient = Consul.builder().withUrl(consulUrl).build()
        val service = ImmutableRegistration.builder()
            .id("$serviceName-${Uuid.random()}")
            .name(serviceName)
//            .check(Registration.RegCheck.ttl(3L))
            .port(port)
            .address(address)
            .build()
        consulClient.agentClient().register(service) // Change this to the port your service is running on
    }
}
