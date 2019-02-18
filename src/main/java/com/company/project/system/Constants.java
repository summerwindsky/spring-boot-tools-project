package com.company.project.system;


public interface Constants {

    String char_separator = "##";
    
    public static String es_cluster_name = SysConfig.getProp("es.cluster");
    
    public static String es_cluster_ip = SysConfig.getProp("es.ip");

    public static int es_cluster_port = Integer.valueOf(SysConfig.getProp("es.port"));


//    public static String es_cluster_name = SysConfig.getProp("es.cluster.test");
//
//    public static String es_cluster_ip = SysConfig.getProp("es.ip.test");
//
//    public static int es_cluster_port = Integer.valueOf(SysConfig.getProp("es.port.test"));

    
    public static String es_index = SysConfig.getProp("es.index");

    public static String es_case_type = SysConfig.getProp("es.type");

    public static String es_writAnalyzer_type = SysConfig.getProp("es.writAnalyzer.type");

    public static long es_scroll_timeout = Long.valueOf(SysConfig.getProp("es.scroll.timeout"));
    
    public static int es_scroll_size = Integer.valueOf(SysConfig.getProp("es.scroll.size"));

    public static String es_index_tj = SysConfig.getProp("es.tj.index");

    public static String WSURL = SysConfig.getProp("wsURL.writExtract");



}
