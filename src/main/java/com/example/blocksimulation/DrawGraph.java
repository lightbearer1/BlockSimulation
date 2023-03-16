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
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

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
    public static void drawGraph2(List<Integer> listX, Map<String,List<Double>> listY){
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
        for (String valueName : listY.keySet()) {
            chart.addSeries(valueName,listX,listY.get(valueName));
        }


        //添加绘图数据
        /*chart.addSeries("hash size",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.3,0.22,0.15));
        chart.addSeries("attack number",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.44,0.22,0.55));
        chart.addSeries("block number",Arrays.asList(5,6,7,8,9),Arrays.asList(0.5,0.4,0.35,0.42,0.35));*/

        //进行展示
        new SwingWrapper<CategoryChart>(chart).displayChart();
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
