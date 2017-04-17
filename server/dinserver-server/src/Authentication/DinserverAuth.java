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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverAuth {

    private final String DATAPATH = "./data/auth.bin";
    
    public String Register(String nick, String pass, Socket socket) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{
        /*MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(pass.getBytes("UTF-8"));
        byte[] passDigest = md.digest();*/
        boolean free = true;
        String result = "";
        
        for (String temp : LoadData()) {
            if(temp.split("\\:", -1)[0].equals(nick)){
                free = false;
                break;
            }
	}
        if(free){
            String str = nick+":" + /*new String(passDigest, "UTF-8")*/ pass + "\n";
        
            try (FileOutputStream output = new FileOutputStream(DATAPATH, true)) {
                output.write(str.getBytes("UTF-8"));
                output.flush();
                output.close();
                System.out.println("Registered new user: "+ str);
                result = "200";
                Login(nick, pass, socket);
            }catch(IOException e){
                    result = "Error "+e;
            }
        }else{
            result = "403 Nick taken";
        }
        return result;
    }
    public boolean Login(String nick, String pass, Socket socket) throws IOException{
        boolean logged = false;
        String input = nick+":"+pass; 
        
        for(String temp : LoadData()){
            if(temp.equals(input)){
                logged = true;
                break;
            }
        }
        if(logged){
            if(DinserverMaster.addUser(nick, socket)){
                System.out.println("User "+nick+" logged in");
            }else{
                logged = false;
            }
        }
        System.out.println(DinserverMaster.listUsers());
        return logged;
    }
    public ArrayList<String> LoadData() throws IOException{
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATAPATH))){
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                data.add(sCurrentLine);
            }
            br.close();
        }catch (FileNotFoundException e){
            CreateDataFile();
        }
        System.out.println("Loaded auth data");
        return data;
    }
    private void CreateDataFile(){
        try {
            File DATAFILE = new File(DATAPATH);
            DATAFILE.getParentFile().mkdirs(); 
            DATAFILE.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DinserverAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
