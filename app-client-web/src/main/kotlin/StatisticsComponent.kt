import com.smalser.common.Game
import com.smalser.common.Statistics
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderLeft
import react.*
import react.dom.*
import styled.*

object TableStyles : StyleSheet("TableStyles") {
    val table by css {
        border(width = 1.px, style = BorderStyle.solid, color = Color("#1C6EA4"))
        backgroundColor = Color("#EEEEEE")
        width = 100.pct
        textAlign = TextAlign.left
        borderCollapse = BorderCollapse.collapse

        listOf(td, th).forEach {
            it {
                border(width = 1.px, style = BorderStyle.solid, color = Color("#AAAAAA"))
                padding(3.px, 8.px)
            }
        }

        thead {
            backgroundColor = Color("#1C6EA4")
            borderBottom(width = 2.px, style = BorderStyle.solid, color = Color("#444444"))
        }

        listOf(thead, th).forEach {
            it {
                fontSize = 15.px
                fontWeight = FontWeight.bold
                color = Color("#FFFFFF")
                textAlign = TextAlign.center
                borderLeft(width = 2.px, style = BorderStyle.solid, color = Color("#D0E4F5"))
            }
            firstChild {
                borderLeft = "none"
            }
        }
    }
}

interface StatisticsProps : RProps {
    var currentGame: Game
    var statistics: Statistics
}

interface StatisticsState : RState {
}

class StatisticsComponent : RComponent<StatisticsProps, StatisticsState>() {
    override fun StatisticsState.init() {
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                margin(10.px)
            }
            styledTable {
                css {
                    +TableStyles.table
                }
                thead {
                    tr {
                        th { +"Rang" }
                        th { +"Name" }
                        th { +"Rating" }
                        th { +"Won" }
                        th { +"Lost" }
//                        th { +"SessionId" }
                    }
                }
                tbody {
                    props.statistics.games.forEachIndexed { idx, game ->
                        val isCurrentGame = game.sessionId == props.currentGame.sessionId
                        if (isCurrentGame || idx < 5) {
                            styledTr {
                                css {
                                    fontWeight = if(isCurrentGame) FontWeight.bold else FontWeight.normal
                                }
                                td { +"${idx + 1}" }
                                td { +game.name }
                                td { +game.rating.format(2) }
                                td { +"${game.won}" }
                                td { +"${game.lost}" }
//                                td { +game.sessionId }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.statistics(handler: StatisticsProps.() -> Unit): ReactElement {
    return child(StatisticsComponent::class) {
        attrs(handler)
    }
}

fun Double.format(digits: Int): String = this.asDynamic().toFixed(digits)