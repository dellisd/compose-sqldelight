package io.github.dellisd

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    val scope = MainScope()
    val dataRepository = DataRepository()
    scope.launch { dataRepository.loadData() }

    renderComposable(rootElementId = "root") {
        val notes by dataRepository.data.collectAsState()

        Div({ style { padding(25.px) } }) {
            if (notes.isEmpty()) {
                Text("No Data / Loading Data")
            } else {
                notes.forEach { note ->
                    Div {
                        Div({ style { fontSize(24.px) } }) { Text(note.title) }
                        Div { Text(note.content) }
                    }
                }
            }
        }
    }
}