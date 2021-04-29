package sync;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.util.Date;

public class Update {

    private static String filePath = System.getProperty("user.dir") + "/README.md";
    private static String hostsPath = System.getProperty("user.dir") + "/hosts";
    private static String templatePath = System.getProperty("user.dir") + "/template.md";

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn.jsdelivr.net/gh/ineo6/hosts/hosts"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        String content = response.body().substring(0,response.body().indexOf("#",2));
        String fileContent = readFileContent(templatePath, content);
        writeFile(filePath, fileContent);
        writeFile(hostsPath,getHostsContent(content));
    }

    public static String getHostsContent(String content) {
        StringBuffer bufAll = new StringBuffer();
        bufAll.append(content);
        bufAll.append("# Please Star : https://github.com/coderluojust/github-hosts");
        bufAll.append(System.getProperty("line.separator"));
        bufAll.append("# Update at:").append(DateFormat.getDateInstance().format(new  Date()));
        bufAll.append(System.getProperty("line.separator"));
        bufAll.append("# GitHub Host End");
        return bufAll.toString();
    }

    public static String readFileContent(String filePath, String content) {
        BufferedReader br = null;
        String line;
        //保存修改过后的所有内容，不断增加
        StringBuffer bufAll = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                StringBuffer buf = new StringBuffer();
                //修改内容核心代码
                if (line.startsWith("# GitHub Host Start")) {
                    for (;;) {
                        line = br.readLine();
                        if (!line.startsWith("# Please Star")) {
                            continue;
                        } else {
                            break;
                        }
                    }
                    buf.append(content);
                    buf.append(line);
                } else if (line.startsWith("# Update at")){
                    buf.append(line);
                    buf.replace(13,line.length(),DateFormat.getDateInstance().format(new  Date()));
                } else if (line.startsWith("内容定时更新，最近更新时间：")) {
                    buf.append(line);
                    buf.replace(14,line.length(),DateFormat.getDateInstance().format(new  Date()));
                } else {
                    buf.append(line);

                }
                buf.append(System.getProperty("line.separator"));
                bufAll.append(buf);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bufAll.toString();


    }

    //写回文件
    public static void writeFile(String filePath, String content) {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
