import kotlinx.css.margin
import kotlinx.css.px
import kotlinx.css.width
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.div
import react.dom.form
import react.dom.input
import react.dom.label
import styled.css
import styled.styledDiv
import styled.styledInput

interface LoginProps : RProps {
    var onSubmitFunction: (event: Event, value: String) -> Unit
}

interface LoginState : RState {
    var name: String
    var submitDisabled: Boolean
}

class LoginComponent : RComponent<LoginProps, LoginState>() {
    override fun LoginState.init() {
        name = ""
        submitDisabled = true
    }

    private fun handleInputChange(event: Event) {
        val target = event.target as HTMLInputElement
        setState {
            name = target.value
            submitDisabled = name.isBlank()
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                margin(10.px)
            }

            form {
                attrs.onSubmitFunction = { props.onSubmitFunction(it, state.name) }

                label {
                    +"Your name:"

                    styledInput(type = InputType.text) {
                        css {
                            width = 100.px
                            margin(10.px)
                        }

                        attrs {
                            value = state.name
                            onChangeFunction = { handleInputChange(it) }
                        }
                    }
                }

                div {
                    input {
                        attrs.type = InputType.submit
                        attrs.value = "Submit"
                        attrs.disabled = state.submitDisabled
                    }
                }
            }
        }
    }
}

fun RBuilder.login(handler: LoginProps.() -> Unit): ReactElement {
    return child(LoginComponent::class) {
        attrs(handler)
    }
}