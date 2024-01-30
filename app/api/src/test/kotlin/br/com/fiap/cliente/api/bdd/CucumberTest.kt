package br.com.fiap.cliente.api.bdd


import br.com.fiap.cliente.api.Application
import io.cucumber.spring.CucumberContextConfiguration
import okhttp3.mockwebserver.MockWebServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@CucumberContextConfiguration
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CucumberTest {
    val mockWebServer: MockWebServer = MockWebServer()
}
