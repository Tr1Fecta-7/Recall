package nl.recall.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class HttpClientProvider {

    val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            installDefaults()
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.installDefaults() {
        installBaseUrl()
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private fun HttpClientConfig<*>.installBaseUrl() = defaultRequest {
        url {
            host = BASE_URL
            protocol = URLProtocol.HTTPS
        }
    }

    companion object {
        private const val BASE_URL = "recall-production-c77d.up.railway.app"
    }
}