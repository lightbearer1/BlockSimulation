package com.example.blocksimulation;

import java.awt.*;
import java.util.Arrays;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import javax.swing.JFrame;
/**
 * Author: wyq
 * Date  : 2023/2/21 23:32
 * Desc  :
 */
public class DrawGraph {


    public static void drawGraph(double[] values) {


        // 创建数据集
        //double[] values = new double[]{1.0, 1.5, 2.0, 2.5, 2.5, 3.0, 3.5, 3.5, 3.5, 4.0, 4.0, 4.0, 4.0, 4.5, 5.0};
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("数据集", values, 10); // 5表示将数据分为5个区间

        // 创建直方图
        //JFreeChart chart = ChartFactory.createHistogram("直方图示例", null, null, dataset, PlotOrientation.VERTICAL, false, false, false);
        JFreeChart chart = ChartFactory.createHistogram(
                "Number of attacks - number of links", // 图表标题
                "X-number of attacks", // 横坐标标签
                "Y-number of chains", // 纵坐标标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向
                true, // 是否包含图例
                true, // 是否包含提示
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
