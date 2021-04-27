package sync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.util.Date;

public class update {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://cdn.jsdelivr.net/gh/ineo6/hosts/hosts"))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        String content = response.body().substring(0,response.body().indexOf("#",2));
        createFile(content);
    }

    public static void createFile(String content) throws IOException {
        String filePath = "/Users/sevenluo/IdeaProjects/github-hosts";
        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + "/README.md");
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile, false);
            writer.append(content);
            writer.write("# Please Star : https://github.com/ineo6/hosts");
            writer.write("\n");//换行
            writer.write("# Update at: ");
            Date d =  new  Date();
            writer.write(DateFormat.getDateInstance().format(d));
            writer.write("\n\n");//换行
            writer.write("# GitHub Host End");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
    }


}
