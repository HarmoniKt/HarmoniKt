package it.unibo.harmonikt.utils

import com.orbitz.consul.Consul
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.util.AttributeKey

class ConsulPlugin(private var consulUrl: String) {
    class Config {
        var consulUrl: String = System.getenv("CONSUL_URL") ?: "http://localhost:8500"
        internal fun build(): ConsulPlugin = ConsulPlugin(consulUrl)
    }

    companion object Plugin : HttpClientPlugin<Config, ConsulPlugin> {
        private var currentNodeIndex: Int = 0

        override val key = AttributeKey<ConsulPlugin>("ConsulPlugin")

        override fun prepare(block: Config.() -> Unit): ConsulPlugin = Config().apply(block).build()

        override fun install(plugin: ConsulPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
                val consulClient = Consul.builder().withUrl(plugin.consulUrl).build()
                val nodes = consulClient.healthClient()
                    .getHealthyServiceInstances(context.url.host).response

                if (nodes.isNotEmpty()) {
                    val selectedNode = nodes[currentNodeIndex]
                    context.url.host = selectedNode.service.address
                    context.url.port = selectedNode.service.port
                    currentNodeIndex = (currentNodeIndex + 1) % nodes.size
                    println("Calling ${selectedNode.service.id}: ${context.url}")
                }
            }
        }
    }
}
