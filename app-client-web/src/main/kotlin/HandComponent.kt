import kotlinx.css.*
import react.*
import react.dom.div
import react.dom.h3
import styled.css
import styled.styledDiv
import styled.styledH4

external interface HandProps : RProps {
    var hand: Hand
    var visible: Boolean
}

class HandComponent : RComponent<HandProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                borderStyle = BorderStyle.solid
                borderWidth = 1.px
                borderRadius = 3.px
                margin(10.px)
                padding(10.px)
            }
            h3 {
                +props.hand.name
            }

            styledDiv {
                css.minHeight = 30.pct
                for (card in props.hand.cards) {
                    div {
                        if (props.visible) {
                            +card.name
                        } else {
                            +"XXX"
                        }
                    }
                }
            }
            styledH4 {
                css {
//                    position = Position.absolute
//                    bottom = 0.px
                }

                if (props.visible) {
                    +"Score: ${props.hand.score()}"
                } else {
                    +"Score: XXX"
                }
            }
        }
    }
}

fun RBuilder.hand(handler: HandProps.() -> Unit): ReactElement {
    return child(HandComponent::class) {
        attrs(handler)
    }
}