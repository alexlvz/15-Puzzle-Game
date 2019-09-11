package com.example.alexl.puzzle_game;
import java.util.Random;
public class GameBoard
{
	public int [][]gameMatrix; //public for getting access to the main activity
	private final int MAX_ROWS=4;
	private final int MAX_COLS=4;
    private static final int EMPTY_SPOT=16;

	public GameBoard()
	{
		this.gameMatrix=new int[MAX_ROWS][MAX_COLS];
		this.gameMatrix=makeBoard(this.gameMatrix);
	}
	private int randomNumMaker()// makes a number between 1-16 (16 = empty)
	{
		Random rnd=new Random();
		int num=rnd.nextInt(16);

			while(checkDoubles(num))
				num=rnd.nextInt(17);
		return num;
	}

	private boolean checkDoubles(int num)// help function to check that the are no doubles
	{
		boolean isFound=false;
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
				if(this.gameMatrix[i][j]==num)
					isFound=true;
		}
		return isFound;
	}

	private int [][]makeBoard(int [][]matrix) //help function to create the board
	{
		for(int i=0;i<matrix.length;i++)
		{
			for(int j=0;j<matrix.length;j++)
			{
				matrix[i][j]=randomNumMaker();
			}

		}
		return matrix;
	}

	private boolean isSorted() //checks if board is sorted
	{
		int num=0;
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
			{
				if(this.gameMatrix[i][j]<num)
					return false;
				num=this.gameMatrix[i][j];
			}
		}
		return true;
	}

	private int findEmptySpotX() // find the x coordinate of the movable spot
	{
		int x=0;
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
				if(this.gameMatrix[i][j]==EMPTY_SPOT)
					x=i;
		}
		return x;
	}

	private int findEmptySpotY()// find the y coordinate of the movable spot
	{
		int y=0;
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
				if(this.gameMatrix[i][j]==EMPTY_SPOT)
					y=j;
		}
		return y;
	}

	public boolean isMoveable(int x, int y) // checks if the current spot is movable
	{
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
			{
				if(i==x && j==y)
				{
					System.out.println(i+" "+j);
					if(i==0 && j==0)
					{
						if(this.gameMatrix[i+1][j]==EMPTY_SPOT || this.gameMatrix[i][j+1]==EMPTY_SPOT)
							return true;
					}
					else if(i==3 && j==3)
					{
						if(this.gameMatrix[i-1][j]==EMPTY_SPOT || this.gameMatrix[i][j-1]==EMPTY_SPOT)
							return true;
					}
					else if(i==0 && j==3)
					{
						if(this.gameMatrix[i+1][j]==EMPTY_SPOT || this.gameMatrix[i][j-1]==EMPTY_SPOT)
							return true;
					}
					else if(i==3 && j==0)
					{
						if(this.gameMatrix[i-1][j]==EMPTY_SPOT || this.gameMatrix[i][j+1]==EMPTY_SPOT)
							return true;
					}
					else if(i==0)
					{
						if(this.gameMatrix[i+1][j]==EMPTY_SPOT || this.gameMatrix[i][j+1]==EMPTY_SPOT || this.gameMatrix[i][j-1]==EMPTY_SPOT)
							return true;
					}
					else if(i==3)
					{
						if(this.gameMatrix[i][j-1]==EMPTY_SPOT || this.gameMatrix[i][j+1]==EMPTY_SPOT|| this.gameMatrix[i-1][j]==EMPTY_SPOT)//==
							return true;
					}
					else if(j==0)
					{
						if(this.gameMatrix[i][j+1]==EMPTY_SPOT || this.gameMatrix[i+1][j]==EMPTY_SPOT || this.gameMatrix[i-1][j]==EMPTY_SPOT)
							return true;
					}
					else if(j==3)
					{
						if(this.gameMatrix[i][j-1]==EMPTY_SPOT || this.gameMatrix[i+1][j]==EMPTY_SPOT || this.gameMatrix[i-1][j]==EMPTY_SPOT)
							return true;
					}
					else if(this.gameMatrix[i][j-1]==EMPTY_SPOT || this.gameMatrix[i][j+1]==EMPTY_SPOT ||this.gameMatrix[i+1][j]==EMPTY_SPOT ||this.gameMatrix[i-1][j]==EMPTY_SPOT)
						return true;
				}
			}
		}
		return false;
	}

	public void move(int x, int y) //making the move
	{
		for(int i=0;i<this.gameMatrix.length;i++)
		{
			for(int j=0;j<this.gameMatrix.length;j++)
			{
				if((i==x && j==y) && isMoveable(x, y))//find the movable spot
				{
					this.gameMatrix[findEmptySpotX()][findEmptySpotY()]=this.gameMatrix[i][j];
					this.gameMatrix[i][j]=EMPTY_SPOT;
				}
			}
		}
	}

	public boolean isWon() //checks if you won the game
	{
		if(isSorted())
			return true;
		return false;

	}

}
