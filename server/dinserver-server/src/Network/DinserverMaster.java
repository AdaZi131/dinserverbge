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
import java.util.Map;

/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverMaster {
    private static Map<String, Socket> users;
    
    public boolean addUser(String nick, Socket socket){
        boolean success = true;
        if(!users.containsKey(nick)){
            users.put(nick, socket);
        }else{
            success = false;
        }
        return success;
    }
    public ArrayList<String> listUsers(){
        ArrayList<String> list = new ArrayList<>();
        users.entrySet().forEach((entry) -> {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            list.add(entry.getKey()+" {"+entry.getValue()+"}");
        });
        return list;
    }
}