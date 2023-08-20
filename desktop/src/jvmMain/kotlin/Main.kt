import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.happy.common.app
import com.happy.common.getPlatformName


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "happy ${getPlatformName()}") {
        app()
    }
}
