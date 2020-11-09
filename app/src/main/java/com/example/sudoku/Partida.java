package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Partida extends AppCompatActivity {

    private final short mapWidth = 9;
    private final short mapHeight = 9;
    private short[][] finalGrid;
    private String finalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);


        short[][] map = genSudoku();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[i].length; j++)
            {
                sb.append(map[i][j] + " ");
            }
            sb.append('\n');
        }
        TextView txt = findViewById(R.id.textView);
        txt.setText(sb.toString());

        TextView tGrid = findViewById(R.id.txtGrid);
        tGrid.setText(finalValue);
        /*


        sb = new StringBuilder();
        for (int i = 0; i < finalGrid.length; i++)
        {
            for (int j = 0; j < finalGrid[i].length; j++)
            {
                sb.append(finalGrid[i][j] + " ");
            }
            sb.append('\n');
        }
        tGrid.setText(sb.toString() + "\n" + finalValue);
        */

    }

    private short[][] genSudoku()
    {

        short[][] map = null;
        gen: while (true)
        {
            map = new short[9][9];
            boolean next = false;
            int ai = 0;
            short n = 0;
            all: for (short i = 0; i < map.length; i++)
            {
                for (short j = 0; j < map[i].length; j++)
                {
                    ArrayList<Short> nLine = newLineArrayList();
                    ai = (int)(Math.random() * nLine.size());
                    n = nLine.get(ai);
                    removeFromAL(n, nLine);
                    while (!checkPostion(j,i,map,n))
                    {
                        if (nLine.size() == 0)
                        {
                            next = true;
                            break all;
                        }
                        ai = (int)(Math.random() * nLine.size());
                        n = nLine.get(ai);
                        removeFromAL(n, nLine);

                    }
                    finalValue = "";
                    map[i][j] = n;
                }
            }
            if (!next)
                break gen;
        }





        return map;
    }


    private boolean checkPostion(short x, short y, short[][] map, short value){
        short[] line = map[y];
        if (!checkPositionLine(value, x, line))
            return false;
        finalValue = "column";
        short[] column = new short[9];
        for (int i = 0; i < map.length; i++)
        {
            column[i] = map[i][x];
        }
        if (!checkPositionLine(value, y, column))
            return false;
        finalValue = "grid";
        short[][] grid = new short[3][3];
        short gridX = (short)(x % 3);
        short gridY = (short)(y % 3);
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                grid[i][j] = map[(y - gridY) + i][(x - gridX) + j];
            }
        }


        if (!checkPositionGrid(gridX, gridY, grid, value))
            return false;

        return true;
    }

    private boolean checkPositionGrid(short gridX, short gridY, short[][] grid, short value)
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                if (grid[i][j] == value)
                {
                    return false;
                }


            }
        }

        return true;
    }
    private boolean checkPositionLine(short value, short pos, short[] line)
    {
        for (int i = 0; i < line.length; i++)
        {
            if (i != pos)
            {
                if (line[i] == value)
                    return false;

            }
        }

        return true;
    }

    private ArrayList<Short> newLineArrayList()
    {
        ArrayList<Short> a = new ArrayList<Short>();
        for (short i = 1; i <= 9; i++)
            a.add(i);
        return a;
    }

    private void removeFromAL (short value, ArrayList<Short> al)
    {
        for (int i = 0; i < al.size(); i++)
        {
            if (al.get(i) == value)
            {
                al.remove(i);
            }

        }
    }


}