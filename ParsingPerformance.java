package asg1;
import java.util.ArrayList;

public class ParsingPerformance {

    // 2d array with rows storing the gear information and column zero
    // the front chain ring teeth and column 1 the rear cassette teeth
    // Array list of int[] with position 0 the front chain ring teeth and position
    // 1 the rear cassette teeth
    protected int[][] array;
    protected ArrayList<int[]> list;
    public static void main(String[] args) {
        String data = URLContentDownloader.downloadUrlContent("https://raw.githubusercontent.com/kartoone/cosc310/refs/heads/main/data/recorddata.txt");
        ParsingPerformance object = new ParsingPerformance();
        object.parseData(data);

        long startArrayTime = System.nanoTime();
        int[] resultsArray = object.analyzeDataWithArray();
        long endArrayTime = System.nanoTime();
        printResult(resultsArray);
        System.out.println("Time taken with array: " + (endArrayTime - startArrayTime) + " nanoseconds");

        long startListTime = System.nanoTime();
        int[] resultsList = object.analyzeDataWithList();
        long endListTime = System.nanoTime();
        printResult(resultsList);
        System.out.println("Time taken with list: " + (endListTime - startListTime) + " nanoseconds");
    }

    protected void parseData(String data) {
        String[] records = data.split("\n");
        array = new int[records.length][2];
        list = new ArrayList<>();
        for (int i = 0; i < records.length; i++) {
            String[] fields = records[i].split(" ");
            String[] gearInfo = fields[10].split(",");
            String[] gears = gearInfo[0].split("x");
            int frontTeeth = Integer.parseInt(gears[0]);
            int rearTeeth = Integer.parseInt(gears[1]);
            array[i][0] = frontTeeth;
            array[i][1] = rearTeeth;
            list.add(new int[] {frontTeeth, rearTeeth});
        }

    for(int[] gears : array){
        System.out.println(java.util.Arrays.toString(gears));
    }

        System.out.println("Press the enter key to continue... ");
        try{
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int[] gears : list) {
            System.out.println(java.util.Arrays.toString(gears));
        }
    }
    protected int[] analyzeDataWithArray() {
        int minFront = Integer.MAX_VALUE;
        int maxFront = Integer.MIN_VALUE;

        int minBack = Integer.MAX_VALUE;
        int maxBack = Integer.MIN_VALUE;

        int totalFront = 0;
        int totalBack = 0;

        for (int[] gears : array) {
            int frontGear = gears[0];
            int backGear = gears[1];

            if (frontGear < minFront){minFront = frontGear;}
            if (backGear < minBack){minBack = backGear;}

            if (frontGear > maxFront){maxFront = frontGear;}
            if (backGear > maxBack){maxBack = backGear;}
            
            totalFront += frontGear;
            totalBack += backGear;
        }
        return new int[]{minFront, maxFront, minBack, maxBack, (int)(totalFront / (double) array.length), (int)(totalBack / (double) array.length)};

    }
    protected int[] analyzeDataWithList() {
        int minFront = Integer.MAX_VALUE;
        int maxFront = Integer.MIN_VALUE;

        int minBack = Integer.MAX_VALUE;
        int maxBack = Integer.MIN_VALUE;

        int totalFront = 0;
        int totalBack = 0;

        for (int[] gears : list) {
            int frontGear = gears[0];
            int backGear = gears[1];

            if (frontGear < minFront){minFront = frontGear;}
            if (backGear < minBack){minBack = backGear;}

            if (frontGear > maxFront){maxFront = frontGear;}
            if (backGear > maxBack){maxBack = backGear;}
            
            totalFront += frontGear;
            totalBack += backGear;
        }
        return new int[]{minFront, maxFront, minBack, maxBack, (int)(totalFront / (double) list.size()), (int)(totalBack / (double) list.size())};
    }
    public static void printResult(int[] results) {
        System.out.println("Front Chainring: Min = " + results[0] + ", Max = " + results[1] + ", Avg = " + results[4]);
        System.out.println("Rear Cassette: Min = " + results[2] + ", Max = " + results[3] + ", Avg = " + results[5]);
    }
}



