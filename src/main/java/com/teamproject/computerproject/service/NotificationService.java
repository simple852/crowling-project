package com.teamproject.computerproject.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamproject.computerproject.dto.UserDto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NotificationService {
    private URL url = null;
    private String readLine = null;
    private StringBuilder buffer = null;
    private OutputStream outputStream = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private HttpURLConnection urlConnection = null;
    private final int connTimeout = 5000;
    private final int readTimeout = 3000;

    public void send_notification(String title,String body,String itemUrl,String imageUrl)//(String title,String body,ItemDto itemDto)
    {
        //나중에 itemDto로 변경하기

        JsonObject jsonObject = new JsonObject();
        JsonArray targetIdsArray = new JsonArray();
        //세그먼트 구독된 모든 기기에 전송할때
//        jsonObject.addProperty("targetType", "segment");
//        targetIdsArray.add("9d1ccc8c-e7e4-439b-bf2e-cb0ffafcffb9");
        jsonObject.addProperty("targetType", "device");
        targetIdsArray.add("38bc2c65-9fda-4ed7-8417-7953424d7fe4");
        
        jsonObject.add("targetIds", targetIdsArray);

        jsonObject.addProperty("url", itemUrl);
        jsonObject.addProperty("imageUrl", imageUrl);
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("body", body);

        String sendData = jsonObject.toString();
        String apiUrl = "https://api.flarelane.com/v1/projects/a8b75707-136f-4fc3-ab47-57801d352e5f/notifications";

        try
        {
            url = new URL(apiUrl);

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(connTimeout);
            urlConnection.setReadTimeout(readTimeout);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Bearer A3WP7VlPTOgdbYtgw9MAs");
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(true);

            outputStream = urlConnection.getOutputStream();

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            bufferedWriter.write(sendData);
            bufferedWriter.flush();

            buffer = new StringBuilder();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
                while((readLine = bufferedReader.readLine()) != null)
                {
                    buffer.append(readLine).append("\n");
                }
            }
            else
            {
                buffer.append("\"code\" : \""+urlConnection.getResponseCode()+"\"");
                buffer.append(", \"message\" : \""+urlConnection.getResponseMessage()+"\"");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedWriter != null) { bufferedWriter.close(); }
                if (outputStream != null) { outputStream.close(); }
                if (bufferedReader != null) { bufferedReader.close(); }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        System.out.println("결과 : " + buffer.toString());
    }
}
