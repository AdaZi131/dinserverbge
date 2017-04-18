package Dinserver;

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
import Network.DinserverThread;
import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class Dinserver {

        /**
         * @param args the command line arguments
         * @throws java.io.IOException
         */
    public static void main(String[] args) throws IOException {
        int port = 2130;
        boolean listening = true;
        
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Listening on " + server.getLocalSocketAddress());
            while (listening) {
                new DinserverThread(server.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.out.println("Exiting...");
            System.exit(-1);
        }
    }
}
