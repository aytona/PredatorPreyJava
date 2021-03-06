package com.chrisaytona;

import java.util.Random;
import java.util.ArrayList;

public class Doodlebug extends Organism
{
    private int index;
    private int currentStepLife;
    private int currentStarveLife;
    private int stepsToBreed = 8;
    private int stepsToStarve = 3;
    private String name = "Doodlebug";
    private boolean endTurn;

    public Doodlebug(int index)
    {
        this.currentStepLife = 0;
        this.currentStarveLife = 0;
        this.index = index;
        this.endTurn = true;
    }

    public void ToggleTurn()
    {
        this.endTurn = false;
    }

    public boolean GetTurn()
    {
        return this.endTurn;
    }

    public String GetName()
    {
        return this.name;
    }

    private void move(Organism[] grid, int width)
    {
        ArrayList<Integer> directions = new ArrayList<Integer>(4);

        if (this.index % width != 0 && this.index - 1 >= 0)
        {
            directions.add(this.index - 1);
        }
        if (this.index + 1 % width != 0 && this.index + 1 < grid.length)
        {
            directions.add(this.index + 1);
        }
        if (this.index - width >= 0)
        {
            directions.add(this.index - width);
        }
        if (this.index + width < grid.length)
        {
            directions.add(this.index + width);
        }

        int dirSize = directions.size();
        for(int i = 0; i < dirSize; i++)
        {
            int newIndex = directions.get(i);
            if (grid[newIndex] != null)
            {
                if (grid[newIndex].GetName().equals("Ant"))
                {
                    grid[newIndex] = this;
                    grid[this.index] = null;
                    this.index = newIndex;
                    return;
                }
            }
        }
        for (int i = 0; i < dirSize; i++)
        {
            Random rand = new Random();
            int randDir = rand.nextInt(directions.size());
            int newIndex = directions.get(randDir);
            if(grid[newIndex] == null)
            {
                grid[newIndex] = this;
                grid[this.index] = null;
                this.index = newIndex;
                return;
            }
            directions.remove(randDir);
        }

    }

    private void breed(Organism[] grid, int width)
    {
        ArrayList<Integer> directions = new ArrayList<Integer>(4);

        if (this.index % width != 0 && this.index - 1 >= 0)
        {
            directions.add(this.index - 1);
        }
        if (this.index + 1 % width != 0 && this.index + 1 < grid.length)
        {
            directions.add(this.index + 1);
        }
        if (this.index - width >= 0)
        {
            directions.add(this.index - width);
        }
        if (this.index + width < grid.length)
        {
            directions.add(this.index + width);
        }

        int dirSize = directions.size();
        for (int i = 0; i < dirSize; i++)
        {
            Random rand = new Random();
            int randDir = rand.nextInt(directions.size());
            int newIndex = directions.get(randDir);
            if(grid[newIndex] == null || grid[newIndex].GetName().equals("Ant"))
            {
                grid[newIndex] = new Doodlebug(newIndex);
                this.currentStepLife = 0;
                return;
            }
            directions.remove(randDir);
        }
    }

    private void starve(Organism[] grid)
    {
        grid[this.index] = null;
    }

    public void simStep(Organism[] grid, int width)
    {
        this.currentStarveLife++;
        this.currentStepLife++;
        if (this.currentStarveLife >= this.stepsToStarve)
        {
            this.starve(grid);
            return;
        }
        if (this.currentStepLife == this.stepsToBreed)
        {
            this.breed(grid, width);
        }
        this.move(grid, width);
        this.endTurn = true;
    }
}
