package tornadofx

import javafx.collections.ObservableList
import javafx.scene.chart.Axis
import javafx.scene.chart.LineChart
import javafx.scene.chart.PieChart
import javafx.scene.chart.XYChart
import javafx.scene.layout.Pane

/**
 * Create a PieChart with optional title data and add to the parent pane. The optional op will be performed on the new instance.
 */
fun Pane.piechart(title: String? = null, data: ObservableList<PieChart.Data>? = null, op: PieChart.() -> Unit): PieChart {
    val chart = if (data != null) PieChart(data) else PieChart()
    chart.title = title
    return opcr(this, chart, op)
}

/**
 * Add and create a PieChart.Data entry. The optional op will be performed on the data instance,
 * a good place to add event handlers to the PieChart.Data.node for example.
 *
 * @return The new Data entry
 */
fun PieChart.data(name: String, value: Double, op: (PieChart.Data.() -> Unit)? = null) = PieChart.Data(name, value).apply {
    data.add(this)
    if (op != null) op(this)
}

/**
 * Add and create multiple PieChart.Data entries from the given map.
 */
fun PieChart.data(value: Map<String, Double>) = value.forEach { data(it.key, it.value) }


/**
 * Create a LineChart with optional title, axis and add to the parent pane. The optional op will be performed on the new instance.
 */
fun <X, Y> Pane.linechart(title: String? = null, x: Axis<X>, y: Axis<Y>, op: (LineChart<X, Y>.() -> Unit)? = null): LineChart<X, Y> {
    val chart = LineChart(x, y)
    chart.title = title
    return opcr(this, chart, op)
}

/**
 * Add a new XYChart.Series with the given name to the given Chart. Optionally specify a list data for the new series or
 * add data with the optional op that will be performed on the created series object.
 */
fun <X, Y, ChartType : XYChart<X, Y>> ChartType.series(name: String, elements: ObservableList<XYChart.Data<X, Y>>? = null, op: ((XYChart.Series<X, Y>).() -> Unit)? = null): XYChart.Series<X, Y> {
    val series = XYChart.Series<X, Y>()
    series.name = name
    if (elements != null) data.add(series)
    if (op != null) op(series)
    data.add(series)
    return series
}

/**
 * Add and create a XYChart.Data entry with x, y and optional extra value. The optional op will be performed on the data instance,
 * a good place to add event handlers to the Data.node for example.
 *
 * @return The new Data entry
 */
fun <X, Y> XYChart.Series<X, Y>.data(x: X, y: Y, extra: Any? = null, op: ((XYChart.Data<X, Y>).() -> Unit)? = null) = XYChart.Data<X, Y>(x, y).apply {
    if (extra != null) extraValue = extra
    data.add(this)
    if (op != null) op(this)
}
