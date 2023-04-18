package nl.recall.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import io.ktor.client.features.json.Json as JsonFeature

@Single
class HttpClientProvider {

    val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            installDefaults()
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.installDefaults() {
        installBaseUrl()
        installJsonSerializer()
    }

    private fun HttpClientConfig<*>.installBaseUrl() = defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
        }
    }

    private fun HttpClientConfig<*>.installJsonSerializer() = JsonFeature {
        serializer = KotlinxSerializer(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    companion object {

        private const val BASE_URL = "some-endpoint.com"
    }
}