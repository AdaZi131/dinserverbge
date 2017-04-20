/*
 * Copyright (C) 2017 Adam Žingor <AdaZi131 at github.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Authentication;

import Network.DinserverMaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.*;
import org.apache.commons.io.IOUtils;



/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverAuth {

   
    public static boolean Login(String nick, String pass, Socket socket) throws IOException{
        boolean logged = false;
        
        JSONObject authRequest = new JSONObject();
        authRequest.put("username",nick);
        authRequest.put("auth_id", pass);
        
            String query = "http://127.0.0.1:5000/token";

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(authRequest.toString().getBytes(Charset.forName("UTF-8")));
            os.close();
            String output = new String();
            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                output = IOUtils.toString(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
            } else {
                System.out.println(conn.getResponseMessage());
            }

           
            JSONObject jsonObject = new JSONObject(output);
            logged = jsonObject.getBoolean("ok");

            conn.disconnect();

            
        if(logged){
            if(DinserverMaster.addUser(nick, socket)){
                System.out.println("User "+nick+" logged in");
            }else{
                logged = false;
            }
        }
        return logged;
    }
}