import androidx.compose.ui.window.ComposeUIViewController
import com.coderwise.core.sample.App
import com.coderwise.core.sample.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }