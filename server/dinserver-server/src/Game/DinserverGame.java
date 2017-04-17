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
package Game;

import java.util.ArrayList;

/**
 *
 * @author Adam Žingor <AdaZi131 at github.com>
 */
public class DinserverGame {
    private ArrayList<String[][]> Objects = new ArrayList<>();
    /* Objects[type][index]
        name [name]
        position [x, y, z]
        scale [x, y, z]
        rotation [x, y, z]
    */
    
    public int createObject(String name, String[] position, String[] scale, String[] rotation){
        int exists = 000;
        String[][] result = new String[(5)][(3)];
        for(String[][] temp : Objects){
            if(temp[0][0].equals(name+exists)){
                exists++;
            }
        }
        result[0][0] = name + "." + exists; 
        result[1] = position; 
        result[2] = scale;
        result[3] = rotation;
        Objects.add(result);
        
        return Objects.size();
    }
}
