import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author John Doe
 */
public final class Seam {

    /**
     * Compute shortest path between {@code from} and {@code to}
     * 
     * @param successors
     *            adjacency list for all vertices
     * @param costs
     *            weight for all vertices
     * @param from
     *            first vertex
     * @param to
     *            last vertex
     * @return a sequence of vertices, or {@code null} if no path exists
     */

    // Best predecessor for each vertex( Algorithme Dijkstra)

    public static int[] path(int[][] successors, float[] costs, int from, int to) {
        // TODO path
        ArrayList<Integer> bestPredecessors = bestPredecessors(successors,
                costs, from, to);
        int current = to;
        ArrayList<Integer> tab = new ArrayList<Integer>();
        while (current != from) {
            current = bestPredecessors.get(current);
            tab.add(current);
        }
        tab.add(0, to);

        int bestPath[] = new int[tab.size()];

        for (int i = 0; i < tab.size(); ++i) {
            bestPath[i] = tab.get(tab.size() - 1 - i);
        }

        return bestPath;
    }

    // Best predecessor for each vertex( Algorithm Dijkstra)

    public static ArrayList<Integer> bestPredecessors(int[][] successors,
            float[] costs, int from, int to) {
        ArrayList<Integer> bestPredecessor = new ArrayList<Integer>();
        float[] distance = new float[costs.length];
        for (int v = 0; v < costs.length; v++) {
            distance[v] = Integer.MAX_VALUE;
            bestPredecessor.add(-1);
        }
        distance[from] = costs[from];
        boolean modified = true;
        while (modified) {
            modified = false;
            for (int v = 0; v < successors.length; ++v) {
                for (int n = 0; n < successors[v].length; ++n) {
                    int node = successors[v][n];
                    if (distance[node] > distance[v] + costs[node]) {
                        distance[node] = distance[v] + costs[node];
                        bestPredecessor.set(node, v);
                        modified = true;
                    }
                }
            }
        }
        return bestPredecessor;
    }

    /**
     * Find best seam
     * 
     * @param energy
     *            weight for all pixels
     * @return a sequence of x-coordinates (the y-coordinate is the index)
     */
    public static int[] find(float[][] energy) {
        // TODO find
        int numRows = energy.length;
        int numCols = energy[0].length;
        System.out.println(numCols);
        int maxId = numRows * numCols - 1;
        int startId = maxId + 1;
        int endId = maxId + 2;

        // tableau de successeurs
        int[][] successors = new int[numRows * numCols+2][];
        
        for (int i = 0; i < numRows-1; ++i) {
            for (int j = 0; j < numCols; ++j) {
                int index =getStateId(i,j,numCols);
                if (j == 0) {
                    successors[index] = new int[2];
                    successors[index][0] = getStateId(i + 1,j,numCols);
                    successors[index][1] = getStateId(i + 1,j + 1,numCols);
                } else if (j == numCols - 1) {
                    successors[index] = new int[2];
                    successors[index][0] = getStateId(i + 1,j - 1,numCols);
                    successors[index][1] = getStateId(i + 1,j, numCols);

                } else {
                    successors[index] = new int[3];
                    successors[index][0] = getStateId(i + 1,j - 1, numCols);
                    successors[index][1] = getStateId(i + 1,j,numCols);
                    successors[index][2] = getStateId(i + 1,j + 1,numCols);
                }              
            }
        }
        
        for(int j=0; j<numCols; ++j){
        	successors[numRows*numCols-1-j]= new int[1];
        	successors[numRows*numCols-1-j][0]=endId;
        }
        successors[startId]=new int[numCols];
        for(int n=0; n<numCols; ++n){
        	successors[startId][n]=n;
        }
        
        successors[endId]=new int []{};

        // tableau de couts
        float[] costs = new float[numRows * numCols+2];
        for (int n = 0; n < costs.length-2; ++n) {
            for (int i = 0; i < numRows; ++i) {
                for (int j = 0; j < numCols; ++j) {
                    costs[n] = energy[i][j];
                }
            }
        }
        costs[startId]=0;
        costs[endId]=0;
        
        int[] bestPath = path(successors, costs, startId, endId);
        for (int i =0; i< bestPath.length;++i){
        System.out.println(bestPath[i]);}
        int[] finalPath= new int[bestPath.length-2];
        for(int i=0; i< finalPath.length; ++i){
        	finalPath[i]=getCol(bestPath[i+1],numCols);
        }
        for(int i=0; i<finalPath.length; ++i){
        System.out.print(finalPath[i]);}
        return finalPath;

    }
    public static int getStateId(int row, int col, int maxCol){
    		int stateId=row*maxCol+col;
    	return stateId;
    }
    public static int getRow(int stateId, int maxCol){
    	return stateId/maxCol;
    }
    public static int getCol(int stateId, int maxCol){
    	return stateId%maxCol;
    }
    /**
     * Draw a seam on an image
     * 
     * @param image
     *            original image
     * @param seam
     *            a seam on this image
     * @return a new image with the seam in blue
     */
    public static int[][] merge(int[][] image, int[] seam) {
        // Copy image
        int width = image[0].length;
        int height = image.length;
        int[][] copy = new int[height][width];
        for (int row = 0; row < height; ++row)
            for (int col = 0; col < width; ++col)
                copy[row][col] = image[row][col];

        // Paint seam in blue
        for (int row = 0; row < height; ++row)
            copy[row][seam[row]] = 0x0000ff;

        return copy;
    }

    /**
     * Remove specified seam
     * 
     * @param image
     *            original image
     * @param seam
     *            a seam on this image
     * @return the new image (width is decreased by 1)
     */
    public static int[][] shrink(int[][] image, int[] seam) {
        int width = image[0].length;
        int height = image.length;
        int[][] result = new int[height][width - 1];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < seam[row]; ++col)
                result[row][col] = image[row][col];
            for (int col = seam[row] + 1; col < width; ++col)
                result[row][col - 1] = image[row][col];
        }
        return result;
    }

}
