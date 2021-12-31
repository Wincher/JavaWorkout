package test;

import javax.swing.*;
import java.awt.Color;

public class TestSwing {
    /**{
     * 创建并显GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {

        // 创建及设置窗口
        JFrame frame = new JFrame("TestSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
        frame.setBackground(Color.red);
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}