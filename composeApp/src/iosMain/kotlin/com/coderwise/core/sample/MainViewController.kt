import androidx.compose.ui.window.ComposeUIViewController
import com.coderwise.core.sample.di.initKoin
import com.coderwise.core.sample.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }