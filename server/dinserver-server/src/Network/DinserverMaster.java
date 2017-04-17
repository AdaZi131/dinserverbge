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

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverMaster {
    private static Map<String, Socket> users = new HashMap<String, Socket>();
    
    public static boolean addUser(String nick, Socket socket){
        boolean success = true;
        if(!users.containsKey(nick)){
            users.put(nick, socket);
        }else{
            success = false;
        }
        return success;
    }
    public static boolean removeUser(String nick){
        boolean success = true;
        if(users.containsKey(nick)){
            users.remove(nick);
        }else{
            success = false;
        }
        return success;
    }
        public static boolean removeUser(Socket socket){
        boolean success = true;
        for(Iterator<Map.Entry<String, Socket>> it = users.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Socket> entry = it.next();
            if(entry.getValue().equals(socket)) {
                it.remove();
            }
        }
        return success;
    }
    public static ArrayList<String> listUsers(){
        ArrayList<String> list = new ArrayList<>();
        users.entrySet().forEach((entry) -> {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            list.add(entry.getKey()+" {"+entry.getValue()+"}");
        });
        return list;
    }
    public static Map<String, Socket> getUsers(){
        return users;
    }
}