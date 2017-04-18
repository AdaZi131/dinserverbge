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
package Network;
import Authentication.DinserverAuth;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import org.json.*;

/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */

public class DinserverProtocol {
    private static final boolean SKIPAUTH = false;
    
    private static final int IDLE = 0;
    private static final int AUTH = 1;
    private static final int LOGGED = 2;
    private static final int INIT = 3;
    private static final int SETUP = 4;

    private int state = IDLE;

    public String processInput(String input, Socket socket) throws IOException, NoSuchAlgorithmException {
        String output = null;

        if(state == IDLE){
            if(!SKIPAUTH){ 
                output = "Authorize";
                state = AUTH;  
            }else{
                output = "200";
                state = LOGGED;
            }
        } else if(state == AUTH) {
            try {
                JSONArray auth = new JSONArray(input);
                if ("register".equals(auth.getString(0).toLowerCase())) {
                    if (!auth.getString(1).contains(":") && !auth.getString(2).contains(":")) {
                        String result = DinserverAuth.Register(auth.getString(1), auth.getString(2), socket);
                        if (result.equals("200")) {
                            output = "200";
                            state = LOGGED;
                        } else {
                            output = result;
                        }
                    } else {
                        output = "400 Wrong character";
                    }
                } else if (auth.get(0) != "register") {
                    if (DinserverAuth.Login(auth.getString(0), auth.getString(1), socket)) {
                        output = "200";
                        state = LOGGED;
                    }else{
                        output = "403";
                    }
                }
            } catch (JSONException e) {
                output = "400 JSON error "+e+"\nTry again";
            }
            if(output.equals("200") && DinserverMaster.getUsers().size() > 0){
                output = "Initialize";
                state = INIT;
            }
        } else if(state == INIT){
            try{
                JSONArray init = new JSONArray(input);
                if(init.length() > 0){
                    
                }else{
                    output = "400 No objects recieved";
                }
                
            } catch (JSONException e) {
                output = "400 JSON error "+e+"\nTry again";
            }
        } else if(state == LOGGED){
            if(input.equals("getObjects")){
                
            }else if(input.equals("exit")){
                output = "Disconnecting";
            }
        } 
        return output;
    }
}
