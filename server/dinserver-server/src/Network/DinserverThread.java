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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverThread  extends Thread {
    private Socket socket = null;

    public DinserverThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        SocketAddress clientAddr = socket.getRemoteSocketAddress();
        
        System.out.println("[ + ]" + clientAddr + " connected.");
        
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            DinserverProtocol dsp = new DinserverProtocol();
            outputLine = dsp.processInput(null, null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = dsp.processInput(inputLine, socket);
                out.println(outputLine);
                if (outputLine.equals("Disconnecting")){
                    break;
                }
            }
            System.out.println("[ - ]" + clientAddr + " disconnected.");
            socket.close();
        } catch (IOException | NoSuchAlgorithmException ex) {
            Logger.getLogger(DinserverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
