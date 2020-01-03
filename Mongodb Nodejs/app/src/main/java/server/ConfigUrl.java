package server;

public class ConfigUrl {

    public static String baseUrl = "http://172.32.1.19:5000";

    public static String getAllMhs = baseUrl + "/mahasiswa/getAllMhs";
    public static String inputDataMhs = baseUrl + "/mahasiswa/insert";
    public static String login = baseUrl + "/mahasiswa/login";
    public static String simpanMk = baseUrl + "/matakuliah/simpanmkandroid";
    public static String getMk = baseUrl + "/matakuliah/getmk";
    public static String ubahMk = baseUrl + "/matakuliah/updatemkandroid/";
    public static String deleteMk = baseUrl + "/matakuliah/deletemk/";
    public static String getMkDetail = baseUrl + "/matakuliah/getmk/";

}