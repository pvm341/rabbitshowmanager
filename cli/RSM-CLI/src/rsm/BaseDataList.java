/*
 * Copyright (C) 2014 paul
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

package rsm;

import java.util.Vector;

/**
 *
 * @author paul
 */
public class BaseDataList {
    protected Vector<BaseDataItem> list;
    protected String heading;
    protected String formatString;
    protected int start = 0;
    protected int pageLength = 10;

    public BaseDataList() {
        list = new Vector<BaseDataItem>();
    }

    public void displayList() {
        BaseDataItem dataItem;
        Screen.locate(5, 4, heading);
        int idx;
        
        for (idx = 0; idx < pageLength && (idx + start) < list.size(); idx++) {
            dataItem = list.get(start + idx);
            Screen.locate(5, 5 + idx);
            if (dataItem.isDirty()) {
                Screen.ink(ScreenColours.YELLOW);
            } else if (dataItem.isDeleteOnWrite()) {
                Screen.ink(ScreenColours.RED);
            }
//Screen.inkStr(dataItem.isDirty()?ScreenColours.YELLOW:dataItem.isDeleteOnWrite()?ScreenColours.RED:ScreenColours.WHITE)+
            System.out.print(dataItem.toListString());
            Screen.ink(ScreenColours.WHITE);
        }
        Screen.locate(5, 24, String.format("Enter < Prev/> Next /q Quit or 0 - %d item",idx));
    }

    public int getStart() {
        return this.start;
    }
    
}
