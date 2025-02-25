import androidx.compose.ui.window.ComposeUIViewController
import com.coderwise.core.di.initKoin
import com.coderwise.core.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }