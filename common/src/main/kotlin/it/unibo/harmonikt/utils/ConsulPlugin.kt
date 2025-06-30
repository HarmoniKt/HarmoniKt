package it.unibo.harmonikt.utils

import com.orbitz.consul.Consul
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey

/**
 * A Ktor HTTP client plugin that integrates with Consul for service discovery.
 * This plugin intercepts HTTP requests and dynamically routes them to healthy service instances
 * registered in Consul, implementing client-side load balancing using a round-robin strategy.
 *
 * @property consulUrl The URL of the Consul server to connect to.
 */
class ConsulPlugin(private var consulUrl: String) {
    /**
     * Configuration class for the ConsulPlugin.
     * Allows customization of the Consul server URL.
     */
    class Config {
        /**
         * The URL of the Consul server.
         * Defaults to the CONSUL_URL environment variable or "http://localhost:8500" if not set.
         */
        var consulUrl: String = System.getenv("CONSUL_URL") ?: "http://localhost:8500"

        /**
         * Builds a ConsulPlugin instance with the configured parameters.
         *
         * @return A new ConsulPlugin instance.
         */
        internal fun build(): ConsulPlugin = ConsulPlugin(consulUrl)
    }

    /**
     * Plugin companion object that implements the HttpClientPlugin interface.
     * This is used by Ktor to install and configure the plugin.
     */
    companion object Plugin : HttpClientPlugin<Config, ConsulPlugin> {
        /**
         * Index for round-robin load balancing among service instances.
         */
        private var currentNodeIndex: Int = 0

        /**
         * Unique key to identify this plugin in the Ktor client.
         */
        override val key = AttributeKey<ConsulPlugin>("ConsulPlugin")

        /**
         * Prepares the plugin configuration.
         *
         * @param block Configuration block to customize the plugin.
         * @return A configured ConsulPlugin instance.
         */
        override fun prepare(block: Config.() -> Unit): ConsulPlugin = Config().apply(block).build()

        /**
         * Installs the plugin into the HTTP client.
         * Intercepts requests at the Render phase to modify the target host and port
         * based on service discovery results from Consul.
         *
         * @param plugin The configured plugin instance.
         * @param scope The HTTP client to install the plugin into.
         */
        override fun install(plugin: ConsulPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
                // Create a Consul client and query for healthy service instances
                val consulClient = Consul.builder().withUrl(plugin.consulUrl).build()
                val nodes = consulClient.healthClient()
                    .getHealthyServiceInstances(context.url.host).response

                if (nodes.isNotEmpty()) {
                    // Select a node using round-robin and update the request URL
                    val selectedNode = nodes[currentNodeIndex]
                    context.url.host = selectedNode.service.address
                    context.url.port = selectedNode.service.port
                    // Update the index for the next request (round-robin)
                    currentNodeIndex = (currentNodeIndex + 1) % nodes.size
                    println("Calling ${selectedNode.service.id}: ${context.url}")
                }
            }
        }
    }
}
