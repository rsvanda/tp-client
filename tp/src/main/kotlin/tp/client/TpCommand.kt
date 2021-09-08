/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package tp.client

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.fileProperties
import tp.api.ApiUrlFactory
import tp.api.time.TimeApi
import tp.client.json.DateDeserializer
import tp.client.time.TimeCommand
import tp.client.time.show.ShowCommand
import java.time.LocalDateTime

class TpCommand : CliktCommand() {

    override fun run() {
        // nothing here yet
    }

}

fun main(args: Array<String>) {

    startKoin {
        printLogger()
        modules(
            timesheetModule
        )
        fileProperties()
    }

    TpCommand()
        .subcommands(
            TimeCommand()
                .subcommands(
                    ShowCommand()
                )
        )
        .main(args)
}

val timesheetModule = module {
    val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer {
                registerTypeAdapter(LocalDateTime::class.java, DateDeserializer())
            }
        }
    }

    single {
        httpClient
    }

    single { ApiUrlFactory() }
    single { TimeApi() }

}
