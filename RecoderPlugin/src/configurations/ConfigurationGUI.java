package configurations;

import service.TaskManagerSetting;

import javax.swing.*;

/**
 * ConfigurationGUI
 *
 * 配置页面界面
 *
 * */
public class ConfigurationGUI {

    private JTextField projectdir;
    private JTextField logdir;
    private JPanel root;
    private JButton pdchoosebtn;
    private JButton ldchoosebtn;
    private JTextArea eachTaskAssignedInTextArea;
    String pd = "/";
    String ld = "/";

    public ConfigurationGUI() {

        // 初始化目录信息
        initDirectories();
        // 绑定事件
        bindActions();
    }

    /**
     * 初始化目录信息
     * */
    private void initDirectories() {
        projectdir.setText(pd);
        logdir.setText(ld);
    }

    /**
     * 绑定事件
     * */
    private void bindActions() {
        // 这里给choose btn们加上actionlistenr，弹开选择文件的窗口来
        pdchoosebtn.addActionListener(
                e -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    //打开选择器面板
                    int returnVal = chooser.showOpenDialog(new JPanel());
                    //保存文件从这里入手，输出的是文件名
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        pd =  chooser.getSelectedFile().getAbsolutePath();
                        projectdir.setText(pd);
                    }
                });
        ldchoosebtn.addActionListener(
                e -> {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    //打开选择器面板
                    int returnVal = chooser.showOpenDialog(new JPanel());
                    //保存文件从这里入手，输出的是文件名
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        ld =  chooser.getSelectedFile().getAbsolutePath();
                        logdir.setText(ld);
                    }
                });
    }

    /**
     * 获取根节点组件
     * */
    public JComponent getRootJPanel(){
        return this.root;
    }

    /**
     * 持久化配置数据
     *
     * @param setting:持久化设置
     * */
    public void apply(TaskManagerSetting setting) {
        String pd = projectdir.getText();
        setting.setPD(pd);
        String ld = logdir.getText();
        setting.setLD(ld);
    }

    /**
     * 回退到保存的配置状态
     * */
    public void reset(TaskManagerSetting setting) {
        projectdir.setText(setting.getPD());
        logdir.setText(setting.getLD());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ConfigurationGUI");
        frame.setContentPane(new ConfigurationGUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
