package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PuzzleState {
	private int[][] grid;
	private int blankRow;
	private int blankCol;

	public PuzzleState(int[][] grid) {
		this.grid = grid;
		PuzzleStateAux.findAndAssignBlankPosition(this);
	}

	public PuzzleState(int[][] newState, int blankRow, int blankCol) {
		grid = newState;
		this.blankRow = blankRow;
		this.blankCol = blankCol;
	}

	public PuzzleState(int rows, int cols, int blankRow, int blankCol) {
		grid = new int[rows][cols];
		this.blankRow = blankRow;
		this.blankCol = blankCol;

		PuzzleStateAux.shuffle(this, blankRow, blankCol);
	}

	public PuzzleState transform(Direction direction) {
		int[][] newState = copyState();
		int newBlankRow = blankRow;
		int newBlankCol = blankCol;

		switch (direction) {
		case UP:
			if (blankRow > 0) {
				swap(newState, blankRow, blankCol, blankRow - 1, blankCol);
				newBlankRow = blankRow - 1;
			}
			break;
		case DOWN:
			if (blankRow < grid.length - 1) {
				swap(newState, blankRow, blankCol, blankRow + 1, blankCol);
				newBlankRow = blankRow + 1;
			}
			break;
		case LEFT:
			if (blankCol > 0) {
				swap(newState, blankRow, blankCol, blankRow, blankCol - 1);
				newBlankCol = blankCol - 1;
			}
			break;
		case RIGHT:
			if (blankCol < grid[0].length - 1) {
				swap(newState, blankRow, blankCol, blankRow, blankCol + 1);
				newBlankCol = blankCol + 1;
			}
			break;
		}

		return new PuzzleState(newState, newBlankRow, newBlankCol);
	}
	
	public boolean canMove(Direction direction) {
        int targetRow = blankRow;
        int targetCol = blankCol;

        switch (direction) {
            case UP:
                targetRow--;
                break;
            case DOWN:
                targetRow++;
                break;
            case LEFT:
                targetCol--;
                break;
            case RIGHT:
                targetCol++;
                break;
        }

        // Check if the target position is within the bounds of the puzzle grid
        if (targetRow < 0 || targetRow >= grid.length || targetCol < 0 || targetCol >= grid[0].length) {
            return false;
        }

        // Check if the target position is adjacent to the blank tile
        if (Math.abs(targetRow - blankRow) + Math.abs(targetCol - blankCol) != 1) {
            return false;
        }

        return true;
    }


	public int[][] copyState() {
		int[][] newState = new int[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			newState[i] = Arrays.copyOf(grid[i], grid[i].length);
		}
		return newState;
	}

	private void swap(int[][] arr, int row1, int col1, int row2, int col2) {
		int temp = arr[row1][col1];
		arr[row1][col1] = arr[row2][col2];
		arr[row2][col2] = temp;
	}

	public int[] flattenGrid() {
		int[] flattenedState = new int[gridSize()];
		int index = 0;

		for (int[] row : grid) {
			for (int tile : row) {
				flattenedState[index++] = tile;
			}
		}

		return flattenedState;
	}

	public int getBlankRowFromBottom() {
		return grid.length - blankRow;
	}

	public int gridSize() {
		return grid.length * grid[0].length;
	}

	public int gridWidth() {
		return grid[0].length;
	}

	public int[][] getGrid() {
		return grid;
	}
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public int getBlankRow() {
		return blankRow;
	}
	public void setBlankRow(int blankRow) {
		this.blankRow = blankRow;
	}

	public int getBlankCol() {
		return blankCol;
	}
	public void setBlankCol(int blankCol) {
		this.blankCol = blankCol;
	}
	
	public boolean isResolved() {
	    int n = grid.length;
	    int m = grid[0].length;
	    int[] flatGrid = new int[n * m];
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < m; j++) {
	            flatGrid[i * m + j] = grid[i][j];
	        }
	    }
	    for (int i = 0; i < n * m - 1; i++) {
	        if (flatGrid[i] != i + 1) {
	            return false;
	        }
	    }
	    return flatGrid[n * m - 1] == 0;
	}


	// Override toString() to print the state for debugging or display purposes
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : grid) {
			for (int val : row) {
				sb.append(val).append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }

	    PuzzleState other = (PuzzleState) obj;

	    // Compare the grid contents
	    return Arrays.deepEquals(this.grid, other.grid);
	}

	@Override
	public int hashCode() {
	    return Arrays.deepHashCode(this.grid);
	}


	public static class PuzzleStateAux {
		public static void shuffle(PuzzleState puzzleState, int emptyRow, int emptyCol) {
			int[][] state = puzzleState.getGrid();
			int rows = state.length;
			int cols = state[0].length;

			List<Integer> numbers = generateNumberList(puzzleState.gridSize());
			Collections.shuffle(numbers);

			int index = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (i == emptyRow && j == emptyCol) {
						state[i][j] = 0;
						puzzleState.setBlankRow(i);
						puzzleState.setBlankCol(j);
					} else {
						int num = numbers.get(index++);
						state[i][j] = num;
					}
				}
			}

			// Check if the puzzle is solvable
			while (!isSolvable(puzzleState)) {
				performParityAdjustment(puzzleState);
			}
		}

		private static List<Integer> generateNumberList(int size) {
			List<Integer> numbers = new ArrayList<>(size);
			for (int i = 1; i < size; i++) {
				numbers.add(i);
			}
			return numbers;
		}

		public static boolean isSolvable(PuzzleState puzzleState) {
			int[] flattenedState = puzzleState.flattenGrid();

			int inversions = countInversions(flattenedState);
			int blankRowFromBottom = puzzleState.getBlankRowFromBottom();
//			int gridSize = puzzleState.gridSize();
			int gridSize = puzzleState.gridWidth();

			if (isOdd(gridSize)) {
				return isEven(inversions);
			}

			if (isEven(gridSize)) {
				if (isEven(blankRowFromBottom))
					return isOdd(inversions);
				if (isOdd(blankRowFromBottom))
					return isEven(inversions);
			}

			return false;
		}

		public static int countInversions(int[] flattenedState) {
			int inversions = 0;
			int length = flattenedState.length;

			for (int i = 0; i < length - 1; i++) {
				int currentTile = flattenedState[i];

				if (currentTile != 0) {
					for (int j = i + 1; j < length; j++) {
						int nextTile = flattenedState[j];

						if (nextTile != 0 && currentTile > nextTile) {
							inversions++;
						}
					}
				}
			}

			return inversions;
		}

		public static int countInversions(int[][] puzzleGrid) {
			return countInversions(flattenGrid(puzzleGrid));
		}

		public static int countInversions(PuzzleState puzzleState) {
			return countInversions(puzzleState.flattenGrid());
		}

		public static int[] flattenGrid(int[][] puzzleGrid) {
			int[] flattenedState = new int[puzzleGrid.length * puzzleGrid[0].length];
			int index = 0;

			for (int[] row : puzzleGrid) {
				for (int tile : row) {
					flattenedState[index++] = tile;
				}
			}

			return flattenedState;
		}

		public static void performParityAdjustment(PuzzleState puzzleState) {
			if(!isSolvable(puzzleState)) {

				int[][] puzzleGrid = puzzleState.getGrid();

				int inversions = countInversions(puzzleState.flattenGrid());
//				int blankRow = puzzleState.getBlankRow() + 1;
				int blankRowFromBottom = puzzleState.getBlankRowFromBottom();
//				int gridSize = puzzleState.gridSize();
				int gridSize = puzzleState.gridWidth();

				if (isOdd(gridSize) && !isEven(inversions)) {
					swapNonBlankTilesInLastRow(puzzleGrid);
				} else if (isEven(gridSize)) {
					if (isEven(blankRowFromBottom + inversions)) {
						if (isOdd(blankRowFromBottom))
							swapNonBlankTilesInFirstRow(puzzleGrid);
						if (isEven(blankRowFromBottom))
							swapNonBlankTilesInLastRow(puzzleGrid);
					}
				}

				puzzleState.setGrid(puzzleGrid);
			}
		}

		public static void swapNonBlankTilesInFirstRow(int[][] puzzleGrid) {
		    int row = 0;
		    int col1 = -1;
		    int col2 = -1;

		    // Try to find the positions of the first two non-blank tiles starting from the first row
		    while (col1 == -1 || col2 == -1) {
		        for (int col = 0; col < puzzleGrid[0].length; col++) {
		            if (puzzleGrid[row][col] != 0) {
		                if (col1 == -1) {
		                    col1 = col;
		                } else {
		                    col2 = col;
		                    break;
		                }
		            }
		        }

		        // If not enough non-blank tiles found in the current row, move to the next row
		        if (col2 == -1) {
		            row++;
		            // If all rows have been checked and not enough non-blank tiles found, break the loop
		            if (row >= puzzleGrid.length) {
		                break;
		            }
		        }
		    }

		    // Swap the two non-blank tiles
		    swapTiles(puzzleGrid, row, col1, row, col2);
		}

		public static void swapNonBlankTilesInLastRow(int[][] puzzleGrid) {
		    int row = puzzleGrid.length - 1;
		    int col1 = -1;
		    int col2 = -1;

		    // Try to find the positions of the first two non-blank tiles starting from the last row
		    while (col1 == -1 || col2 == -1) {
		        for (int col = 0; col < puzzleGrid[0].length; col++) {
		            if (puzzleGrid[row][col] != 0) {
		                if (col1 == -1) {
		                    col1 = col;
		                } else {
		                    col2 = col;
		                    break;
		                }
		            }
		        }

		        // If not enough non-blank tiles found in the current row, move to the previous row
		        if (col2 == -1) {
		            row--;
		            // If all rows have been checked and not enough non-blank tiles found, break the loop
		            if (row < 0) {
		                break;
		            }
		        }
		    }

		    // Swap the two non-blank tiles
		    swapTiles(puzzleGrid, row, col1, row, col2);
		}


		public static void swapTiles(int[][] puzzleGrid, int row1, int col1, int row2, int col2) {
			int temp = puzzleGrid[row1][col1];
			puzzleGrid[row1][col1] = puzzleGrid[row2][col2];
			puzzleGrid[row2][col2] = temp;
		}

		public static boolean isEven(int number) {
			return number % 2 == 0;
		}

		public static boolean isOdd(int number) {
			return !isEven(number);
		}

		public static void findAndAssignBlankPosition(PuzzleState puzzleState) {
			int[][] grid = puzzleState.getGrid();
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j] == 0) {
						puzzleState.setBlankRow(i);
						puzzleState.setBlankCol(j);
						return;
					}
				}
			}
		}
		
		public static String toString(int[][] grid) {
			StringBuilder sb = new StringBuilder();
			for (int[] row : grid) {
				for (int val : row) {
					sb.append(val).append("\t");
				}
				sb.append("\n");
			}
			return sb.toString();
		}
	}



}
