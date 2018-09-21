package com.vz.vue.ui;

import com.vz.vue.common.CONST;
import com.vz.vue.common.JDBC;
import com.vz.vue.model.Column;
import com.vz.vue.model.MyTableModel;
import com.vz.vue.model.Table;
import com.vz.vue.service.Creater;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

public class UI extends JFrame {
    JLabel jlDBType = new JLabel("类型：",JLabel.LEFT);
    JLabel jlIp = new JLabel("IP：");
    JLabel jlPort = new JLabel("端口：");
    JLabel jlUserName = new JLabel("用户名：");
    JLabel jlPassword = new JLabel("密码：");
    JLabel jlDBName = new JLabel("库名：");

    JComboBox jcbDBType=new JComboBox();
    JComboBox jcbTable=new JComboBox();

    JTextField jtfIp = new JTextField(7);
    JTextField jtfPort = new JTextField(3);
    JTextField jtfUserName = new JTextField(4);
    JPasswordField jtfPassword = new JPasswordField(6);
    JTextField jtfDBName = new JTextField(8);

    JButton jbtCreateAll = new JButton("一键生成全部");
    JButton jbtReadTables = new JButton("读表");
    JButton jbtReadFields = new JButton("读字段");
    JButton jbtHelp = new JButton("查看帮助");
    JButton jbtChooseFile = new JButton("自定义模板");
    JButton jbtCreateSingle = new JButton("按配置生成一个页面");

    JTable jtable = new JTable();

    private static Table currTable = null;


    public UI() {
        super();

        //数据初始化
        for(JDBC j : JDBC.values()){
            jcbDBType.addItem(j.getDesc());
        }

        jtfIp.setText(JDBC.MYSQL.getIp());
        jtfPort.setText(JDBC.MYSQL.getPort());
        jtfUserName.setText(JDBC.MYSQL.getUser());
        jtfPassword.setText(JDBC.MYSQL.getPassword());
        jtfDBName.setText(JDBC.MYSQL.getDbName());
        jcbTable.setEnabled(false);
        jbtReadFields.setEnabled(false);
        jbtCreateSingle.setEnabled(false);

        //顶部
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.add(jlDBType);
        topPanel.add(jcbDBType);
        topPanel.add(jlIp);
        topPanel.add(jtfIp);
        topPanel.add(jlPort);
        topPanel.add(jtfPort);
        topPanel.add(jlUserName);
        topPanel.add(jtfUserName);
        topPanel.add(jlPassword);
        topPanel.add(jtfPassword);
        topPanel.add(jlDBName);
        topPanel.add(jtfDBName);
        topPanel.add(jbtCreateAll);
        topPanel.add(jbtReadTables);
        topPanel.add(jbtReadFields);
        topPanel.add(jcbTable);

        jlDBType.setBounds(10,30,50,25);
        jcbDBType.setBounds(50,30,80,25);
        jlIp.setBounds(140,30,50,25);
        jtfIp.setBounds(160,30,90,25);
        jlPort.setBounds(260,30,50,25);
        jtfPort.setBounds(300,30,50,25);
        jlUserName.setBounds(360,30,60,25);
        jtfUserName.setBounds(410,30,50,25);
        jlPassword.setBounds(470,30,50,25);
        jtfPassword.setBounds(510,30,65,25);

        jlDBName.setBounds(10,70,50,25);
        jtfDBName.setBounds(50,70,90,25);
        jbtCreateAll.setBounds(150,70,120,25);
        jbtReadTables.setBounds(280,70,60,25);
        jcbTable.setBounds(350,70,135,25);
        jbtReadFields.setBounds(495,70,80,25);

        Border topTitle = BorderFactory.createTitledBorder("基础配置");
        topPanel.setBorder(topTitle);
        topPanel.setPreferredSize(new Dimension(600,110));
        this.add(topPanel, BorderLayout.NORTH);


        //中间表格部分
        jtable.setModel(new MyTableModel());
        JScrollPane jsp = new JScrollPane(jtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Border centerTitle = BorderFactory.createTitledBorder("字段配置");
        jsp.setBorder(centerTitle);
        this.add(jsp, BorderLayout.CENTER);

        //底部
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(jbtHelp);
        bottomPanel.add(jbtChooseFile);
        bottomPanel.add(jbtCreateSingle);
        this.add(bottomPanel, BorderLayout.SOUTH);

        //以下未事件处理

        //数据库切换
        jcbDBType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(ItemEvent.SELECTED == e.getStateChange()){
                    String selectName = e.getItem().toString();
                    JDBC jdbc = JDBC.getByDesc(selectName);

                    jtfIp.setText(jdbc.getIp());
                    jtfPort.setText(jdbc.getPort());
                    jtfUserName.setText(jdbc.getUser());
                    jtfPassword.setText(jdbc.getPassword());
                    jtfDBName.setText(jdbc.getDbName());

                    jcbTable.removeAllItems();
                    jcbTable.setEnabled(false);
                    jbtReadFields.setEnabled(false);
                }
            }
        });

        //读表
        jbtReadTables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDBC jdbc = getJDBC();
                String dbName = jtfDBName.getText();

                if(dbName==null || "".equals(dbName.trim())){
                    showMsg("请输入库名");
                    return;
                }

                java.util.List<Table> tables = Creater.findTable(dbName,jdbc);


                if(tables==null || tables.isEmpty()){
                    showMsg("数据库["+dbName+"]不存在");
                    return;
                }

                jcbTable.removeAllItems();
                for(Table t : tables){
                    jcbTable.addItem(t.getTableName());
                }
                jcbTable.setEnabled(true);
                jbtReadFields.setEnabled(true);
            }
        });

        //读字段
        jbtReadFields.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDBC jdbc = getJDBC();
                String dbName = jtfDBName.getText();
                String tName = jcbTable.getSelectedItem().toString();

                Table t = Creater.getTable(new Table(dbName,tName),jdbc);


                java.util.List<Column> columns = t.getColumns();

                if(columns == null){
                    showMsg("["+tName+"]表下无字段");
                    return;
                }

                currTable = t; //缓存当前表

                Object[][] tableData = new Object[columns.size()][6];
                for(int i=0; i<columns.size(); i++){
                    Column column = columns.get(i);
                    tableData[i][0] = column.getComment();
                    tableData[i][1] = column.getName();
                    tableData[i][2] = column.getIsField();
                    tableData[i][3] = column.getIsCond();
                    tableData[i][4] = column.getIsRule();
                }

                TableModel tableModel = new MyTableModel(tableData);
                jtable.setModel(tableModel);
                jtable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JCheckBox()));
                jtable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JCheckBox()));
                jtable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JCheckBox()));

                jbtCreateSingle.setEnabled(true);
            }
        });

        //一键生成全部
        jbtCreateAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Creater.inputPath == null){
                    showMsg("请先点击底部 “自定义模板” 指定模板文件");
                    return;
                }

                JDBC jdbc = getJDBC();
                String dbName = jtfDBName.getText();

                if(dbName==null || "".equals(dbName.trim())){
                    showMsg("请输入库名");
                    return;
                }

                Creater.createAll(dbName,jdbc);

                showMsg("所有页面已创建完成！文件路径为："+CONST.OUTPUT_PATH);
            }
        });

        //按配置生成单页面
        jbtCreateSingle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Creater.inputPath == null){
                    showMsg("请先点击底部 “自定义模板” 指定模板文件");
                    return;
                }

                if(currTable == null){
                    showMsg("当前表信息丢失");
                }

                java.util.List<Column> columns = currTable.getColumns();
                for(int i=0; i<columns.size(); i++){
                    Column column = columns.get(i);
                    column.setIsField((Boolean)jtable.getValueAt(i,2));
                    column.setIsCond((Boolean)jtable.getValueAt(i,3));
                    column.setIsRule((Boolean)jtable.getValueAt(i,4));
                }

                Creater.createSingle(currTable,null);

                showMsg(currTable.getTableName() + CONST.FILE_TYPE + " 创建完成！文件路径为："+CONST.OUTPUT_PATH);
            }
        });

        //自定义模板
        jbtChooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择");
                File file=jfc.getSelectedFile();
                if(file.isDirectory()){
                    showMsg("请选择一个文件");
                }else if(file.isFile()){
                    Creater.inputPath = file.getAbsolutePath();
                    showMsg("模板已应用");
                }
            }
        });

        //帮助
        jbtHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openHelp();
            }
        });

        this.init();
    }

    public void init(){
        this.setTitle("vue文件生成器");
        this.setEnabled(true);
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public JDBC getJDBC(){
        String dbType = jcbDBType.getSelectedItem().toString();
        String ip = jtfIp.getText();
        String port = jtfPort.getText();
        String user = jtfUserName.getText();
        String password = new String(jtfPassword.getPassword());

        if(JDBC.MYSQL.getDesc().equals(dbType)){
            return JDBC.MYSQL.init(ip,port,user,password);
        }else if(JDBC.ORACLE.getDesc().equals(dbType)){
            return JDBC.ORACLE.init(ip,port,user,password);
        }else{
            return null;
        }
    }

    public void showMsg(String msg){
        JOptionPane.showMessageDialog(null, msg, "提示",JOptionPane.WARNING_MESSAGE);
    }

    public void openHelp(){
        try {
            Runtime.getRuntime().exec("C:\\WINDOWS\\system32\\notepad.exe src\\main\\resources\\help.txt");
        } catch (IOException e1) {
            showMsg("打开帮助信息失败");
        }
    }

    public static void main(String[] args) {
        new UI();
    }
}
