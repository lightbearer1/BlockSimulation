package com.example.blocksimulation;

import java.awt.*;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.JFrame;
/**
 * Author: wyq
 * Date  : 2023/2/21 23:32
 * Desc  :
 */
public class DrawGraph {

    /**
     * 使用xchart绘制直方图
     * @param listX 横坐标数据
     * @param listY 纵坐标数据
     */
    public static void drawGraph2(Map<String,List<Integer>> listX, Map<String,List<Double>> listY){
        for (String valueName : listY.keySet()) {
            //创建绘图对象
            CategoryChart chart = new CategoryChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Number-Probability Histogram")  //标题
                    .xAxisTitle("Value number")             //横坐标
                    .yAxisTitle("Error probability")        //纵坐标
                    .build();

            //设置标签是否可见
            chart.getStyler().setLegendVisible(true);
            //设置网格是否可见
            chart.getStyler().setPlotBorderVisible(true);

        //添加绘图数据
        //q: 这个循环是如何运作的？
        //a: listY里面是一个map，key是valueName，value是一个list，list里面是double类型的数据

            //q: listY里面是什么？
            //a: listY里面是一个map，key是valueName，value是一个list，list里面是double类型的数据
            chart.addSeries(valueName,listX.get(valueName),listY.get(valueName));
            //进行展示
            new SwingWrapper<>(chart).displayChart();
        }


        //添加绘图数据
        /*chart.addSeries("hash size",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.3,0.22,0.15));
        chart.addSeries("attack number",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.44,0.22,0.55));
        chart.addSeries("block number",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.35,0.42,0.35));*/


    }

    //画折线图
    public static void drawLineChart(Map<String,List<Integer>> listX, Map<String,List<Double>> listY){

        for (String valueName : listY.keySet()) {
            // 创建Chart
            CategoryChart chart = new CategoryChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Number-Probability") // 图表标题)
                    .xAxisTitle(valueName)
                    .yAxisTitle("Error probability")
                    .build();

            //设置图表样式
            chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Line);

            //添加数据源

            //q: listY里面是什么？
            //a: listY里面是一个map，key是valueName，value是一个list，list里面是double类型的数据
            chart.addSeries(valueName,listX.get(valueName),listY.get(valueName));
            // 进行展示
            new SwingWrapper<CategoryChart>(chart).displayChart();
        }


    }
    //画双y轴折线图
    public static void drawDoubleLineChart(Map<String,List<Integer>> listX, Map<String,List<List<Double>>> listY) {

        XYChart chart = null;
        for (String valueName : listY.keySet()) {
            // 创建Chart
            chart = new XYChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Number-Probability") // 图表标题)
                    .xAxisTitle(valueName)
                    .yAxisTitle("Error probability")
                    .build();

            //设置图表样式
            //chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Line);
            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            // Series
            XYSeries series1 = chart.addSeries(valueName+" probability", listX.get(valueName), listY.get(valueName).get(0));
            XYSeries series2 =  chart.addSeries(valueName+" numOfIllegalChain", listX.get(valueName), listY.get(valueName).get(1));
            series1.setYAxisGroup(0);
            series2.setYAxisGroup(1);

            // Y-Axis
            chart.setYAxisGroupTitle(0, "probability");
            chart.setYAxisGroupTitle(1, "numOfIllegalChain");


            chart.getStyler().setYAxisGroupPosition(0, Styler.YAxisPosition.Left);
            chart.getStyler().setYAxisGroupPosition(1, Styler.YAxisPosition.Right);


            //添加数据源
            // Series


            //q: listY里面是什么？
            //a: listY里面是一个map，key是valueName，value是一个list，list里面是double类型的数据
            //chart.addSeries(valueName,listX.get(valueName),listY.get(valueName));
            // 进行展示
            new SwingWrapper<>(chart).displayChart();
        }


    }

    public static void drawGraph(double[] values,int value,String valueName) {


        // 创建数据集
        //double[] values = new double[]{1.0, 1.5, 2.0, 2.5, 2.5, 3.0, 3.5, 3.5, 3.5, 4.0, 4.0, 4.0, 4.0, 4.5, 5.0};
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("数据集", values, value); // value表示将数据分为value个区间

        // 创建直方图
        //JFreeChart chart = ChartFactory.createHistogram("直方图示例", null, null, dataset, PlotOrientation.VERTICAL, false, false, false);
        JFreeChart chart = ChartFactory.createHistogram(
                "histogram of attack "+valueName, // 图表标题
                "X-occurrences", // 横坐标标签
                "Y-number of time", // 纵坐标标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向
                false, // 是否包含图例
                false, // 是否包含提示
                false // 是否包含URL链接
        );
        // 设置直方图的颜色
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setForegroundAlpha(0.85f);

        // 创建图形界面并显示直方图
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        JFrame frame = new JFrame("Histogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);

    }
}
