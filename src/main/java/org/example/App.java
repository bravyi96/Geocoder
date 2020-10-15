package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) throws IOException, ParseException {
        System.out.print("\n1 - адрес, 2 - координаты: ");
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine();
        String result;

        if ("1".equals(mode)) {
            System.out.print("Введите адрес: ");
            result = getCoords(scanner.nextLine());
        } else if ("2".equals(mode)) {
            System.out.print("Введите координаты: ");
            result = getAddress(scanner.nextLine());
        } else {
            result = "Выбранного режима не существует!";
        }

        System.out.println(result);
    }

    private static String getCoords(String address) throws IOException, ParseException {
        String host = "https://geocoder.ls.hereapi.com/6.2/geocode.json";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("apiKey", "VKocMk0mLprngzh3wIavR57I3Clwi6Pb3sPEkTDgcPw");
        parameters.put("searchtext", address.replaceAll("\\s", "+"));
        parameters.put("access_token", "eyJhbGciOiJSUzUxMiIsImN0eSI6IkpXVCIsImlzcyI6IkhFUkUiLCJhaWQiOiJMN1JkdWE4REhuODNZcWdnRElpdiIsImlhdCI6MTYwMjc4NTExMSwiZXhwIjoxNjAyODcxNTExLCJraWQiOiJqMSJ9.ZXlKaGJHY2lPaUprYVhJaUxDSmxibU1pT2lKQk1qVTJRMEpETFVoVE5URXlJbjAuLmVfYXdVNUhzcFV3Sk84X2hha0dzdHcuX0JZOUY0TFg3TkgyY2JETExnSkpQSUpzVEYwSVN6MGg2Q242ZWY1RDJ3QnlUamFPQWZURXBsVjJHX0tkVWFVbldRQy1fSGVSaEFqeEpHSkVsY1J2MVVjbkdnTk9nUFB4c1c3LXlfSW9zaTljODBUbjk2TEZGLUlaS09KWjZwbXBPSUZfUEJ6TGxqX20zNEs3a2hhcVNBLnhGcmtSdXd5ZXBwanNUVndwQmtOeVk4eHdLT0xtOFpkTHNiaWhQUVlGUXM.H8cP6Dbcs34e-V8JkOr2_Finomx0IS8tdA5OAR2Q_ZdkP622lOmrh-5U-SNdqeWo5PPt9I97IwD-kfQZ2DRTDUl1_w0J3go1EqyqnGmerFsXPKLJM_ta-KCKZKzvbxo1mE8SxrosfqSy064vhunNo_OInCf5o6_0DL4aKGf5qjokb7Szg9jA3apWIoT75Cq283MGQbp885mxorz8nLEthxOiXHeIgohMCM5fm5do-EaU879w_hm4loLzRMusrqGGrAaBaAKDO0mCnBPQQg0jVOgNyvqd7NBldCxoY_WZzJwNB1Bo9lANUmj4AL8ed1oCsr_Qn_JPVqcorlHZciNmGg");
        URL url = new URL(host + "?" + ParameterStringBuilder.getParamsString(parameters));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setInstanceFollowRedirects(false);
        int status = connection.getResponseCode();
        Reader streamReader;
        if (status > 299) streamReader = new InputStreamReader(connection.getErrorStream());
        else streamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) content.append(inputLine);
        in.close();
        connection.disconnect();
        String result = content.toString();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        jsonObject = (JSONObject) jsonObject.get("Response");
        jsonObject = (JSONObject) ((JSONArray) jsonObject.get("View")).get(0);
        jsonObject = (JSONObject) ((JSONArray) jsonObject.get("Result")).get(0);
        jsonObject = (JSONObject) jsonObject.get("Location");
        jsonObject = (JSONObject) jsonObject.get("DisplayPosition");
        Double latitude = (Double) jsonObject.get("Latitude");
        Double longitude = (Double) jsonObject.get("Longitude");
        result = latitude.toString() + ", " + longitude.toString();
        return "Координаты: " + result;
    }

    private static String getAddress(String coords) {
        return coords;
    }
}
