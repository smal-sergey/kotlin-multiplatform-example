import react.dom.render
import kotlinx.browser.document

fun main() {
//  WA for tests: https://stackoverflow.com/questions/61839800/unit-testing-in-kotlin-js
    document.body!!.insertAdjacentHTML("afterbegin", "<div id='root'></div>" )
    render(document.getElementById("root")) {
        child(App::class) { }
    }
}