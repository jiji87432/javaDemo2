package cn.microanswer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.net.URLEncoder;

public class Test41 {

    public static void main(String[] args) throws Exception {
        String[] letters = "abcdefghijklmnopqrstuvwxyz0123456789".split("");
        File resultFile = new File("C:\\Users\\Microanswer\\Desktop\\io可用域名.txt");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(resultFile), "UTF-8");
        BufferedReader reader1 = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        char[] datas = new char[2048];
        int datasize;
        while ((datasize = reader1.read(datas)) != -1) {
            stringBuilder.append(datas, 0, datasize);
        }
        String txt = stringBuilder.toString();
        reader1.close();
        reader.close();

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(resultFile), "UTF-8"), true);
        if (txt.length() < 5) {
            printWriter.println("下面是可用的 io  域名：");
        } else {
            printWriter.print(txt);
        }
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                for (int k = 0; k < letters.length; k++) {

                    String host = letters[i] + letters[j] + letters[k] + ".io";

                    if (txt.contains("http://" + host)) {
                        continue;
                    }

                    System.out.print("http://" + host + " ---> ");

                    String response = HttpUtil.get("https://find.godaddy.com/domainsapi/v1/bundles?q=" + URLEncoder.encode(host, "UTF-8"));

                    boolean success = false;

                    if (response != null && response.length() > 0) {
                        JSONArray arrs = JSON.parseArray(response);
                        success = arrs.size() > 0;
                    }

                    if (success) {
                        System.out.println("可以");
                        printWriter.println("http://" + host + " --> 【■】");
                    } else {
                        System.out.println("已注册");
                        printWriter.println("http://" + host + " --> 【□】");
                    }

                    printWriter.flush();
                }
            }
        }

        printWriter.close();

    }

}
