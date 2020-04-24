import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Write a description of Temperatures2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Temperatures2 {
    public CSVRecord hottestTempForFile(CSVParser parser) {
        CSVRecord largestSoFar = null;
        for (CSVRecord currentRow : parser) {
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar); 
        }
        return largestSoFar;
    }
    
    public CSVRecord findColdestInFile(CSVParser parser) {
        CSVRecord lowestRow = null;
        for (CSVRecord currentRow : parser) {
            lowestRow = findLowestCriteriaOfTwo(currentRow, lowestRow, "TemperatureF");
        }
        return lowestRow;
    }
    
     public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestRow = null;
        for (CSVRecord currentRow : parser) {
            lowestRow = findLowestCriteriaOfTwo(currentRow, lowestRow, "Humidity");
        }
        return lowestRow;
    }
    
    public double averageTempInFile(CSVParser parser) {
        double totalT = 0;
        int count = 0;
        for (CSVRecord currentRow : parser) {
            double currentT = Double.parseDouble(currentRow.get("TemperatureF"));
            totalT += currentT;
            count += 1;
        }
        double averageT = totalT / count;
        return averageT;        
    }
    
    public double avgTwHighHInFile(CSVParser parser, int value) {
        double TotalT = 0;
        int count = 0;
        for (CSVRecord currentRow : parser) {
            int H = Integer.parseInt(currentRow.get("Humidity"));
            if (H >= value) {
                double currentT = Double.parseDouble(currentRow.get("TemperatureF"));
                TotalT += currentT;
                count += 1;
            }
        }
        if (TotalT == 0 && count == 0) {
            return -1;
        }
        else {
        double averageT = TotalT / count;
        return averageT;
        }
    }
    
    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord largestSoFar) {
        if (largestSoFar == null) {
                largestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                if (currentTemp > largestTemp) {
                    largestSoFar = currentRow;
                }
            }
        return largestSoFar;
    }
    
    public CSVRecord findLowestCriteriaOfTwo(CSVRecord currentRow, CSVRecord lowestRow, String criteria) {
        if ((currentRow.get(criteria)).equals("N/A")) {
            return lowestRow;
        }
        double currentValue = Double.parseDouble(currentRow.get(criteria));
        if (lowestRow == null && (!(currentRow.get(criteria)).equals("N/A")) && currentValue != -9999) {
            lowestRow = currentRow;
        }
        else {
            double lowestValue = Double.parseDouble(lowestRow.get(criteria));
            if (currentValue != -9999 && currentValue < lowestValue) {
                lowestRow = currentRow;
            }
        }
        return lowestRow;
    }
    
    public CSVRecord findLowestOfTwo(CSVRecord currentRow, CSVRecord lowestRow) {
        if (lowestRow == null) {
            lowestRow = currentRow;
            }
        else {
            String lowestTemp = lowestRow.get("TemperatureF");
            double ltValue = Double.parseDouble(lowestTemp);
            String currentTemp = currentRow.get("TemperatureF");
            double ctValue = Double.parseDouble(currentTemp);
            if (ctValue == -9999) {
                return lowestRow;
            }
            if (ctValue < ltValue) {
                lowestRow = currentRow;
            }
        }
        return lowestRow;
    }
    
    public CSVRecord findLowestHOfTwo(CSVRecord currentRow, CSVRecord lowestRow) {
        if (lowestRow == null && (!(currentRow.get("Humidity")).equals("N/A"))) {
            lowestRow = currentRow;
        }
        else {
            if ((currentRow.get("Humidity")).equals("N/A")) {
                return lowestRow;
            }
            double lowestHvalue = Double.parseDouble(lowestRow.get("Humidity"));
            double currentHvalue = Double.parseDouble(currentRow.get("Humidity"));
            if (currentHvalue < lowestHvalue) {
                lowestRow = currentRow;
            }
        }
        return lowestRow;
    }
    
    public CSVRecord hottestTempInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord largestForFile = hottestTempForFile(fr.getCSVParser());
            largestSoFar = getLargestOfTwo(largestForFile, largestSoFar);
        }
        return largestSoFar;
    }
    
    public CSVRecord findColdestDay() {
        CSVRecord lowestRow = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentRow = findColdestInFile(parser);
            lowestRow = findLowestCriteriaOfTwo(currentRow, lowestRow, "TemperatureF");
        }
        return lowestRow;
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestH = null;
        DirectoryResource dr  = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentRow = lowestHumidityInFile(parser);
            lowestH = findLowestCriteriaOfTwo(currentRow, lowestH, "Humidity");
        }
        return lowestH;
    }
    
    public String fileWithColdestTemperature() {
        File coldestFile = null;
        CSVRecord lowestRow = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentRow = findColdestInFile(parser);
            CSVRecord lowestOfTwo = findLowestCriteriaOfTwo(currentRow, lowestRow, "TemperatureF");
            if (lowestOfTwo != lowestRow) {
        coldestFile = f;
        lowestRow = lowestOfTwo;
        }
        }
        String nameOfFile = coldestFile.getAbsolutePath();
        return nameOfFile;
    }
    
    public void printAllDatesAndTemps(CSVParser parser) {
        for (CSVRecord currentRow : parser) {
            System.out.println(currentRow.get("DateUTC") + ": " + currentRow.get("TemperatureF"));
        }
    }
        
    public void testPrintAll() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("All temperatures on that day were ");
        printAllDatesAndTemps(parser);
    }
    
    public void testFileWithColdestTemperature() {
        String fileWithColdestTemperature = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " + fileWithColdestTemperature);
        FileResource fr = new FileResource(fileWithColdestTemperature);
        CSVParser parser = fr.getCSVParser(); 
        CSVRecord coldest = findColdestInFile(parser);
        System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF"));

        parser = fr.getCSVParser();
        System.out.println("All the temperatures on that day were: ");
        printAllDatesAndTemps(parser);
    }
    
    public void testhottestTempForFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord largest = hottestTempForFile(parser);
        System.out.println("Hottest Temp was " + largest.get("TemperatureF") + " at " + largest.get("DateUTC"));
    }
    
    public void testColdestInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = findColdestInFile(parser);
        System.out.println("Coldest Temp was " + coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestH = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + lowestH.get("Humidity") + " at " + lowestH.get("DateUTC"));
    }
    
    public void testaverageTempInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgT = averageTempInFile(parser);
        System.out.println("Average temperature in file is " + avgT);
    }
    
    public void testavgTwHighHInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgT = avgTwHighHInFile(parser, 80);
        if (avgT == -1) {
            System.out.println("No temperatures with that humidity");
        }
        else {
            System.out.println("Average Temp when high Humidity is " + avgT);
        }
    }
    
    public void testHottestTempInManyDays() {
        CSVRecord largest = hottestTempInManyDays();
        System.out.println("Hottest Temp was " + largest.get("TemperatureF") + " at " + largest.get("DateUTC"));
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowestH = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidty was " + lowestH.get("Humidity") + " at " + lowestH.get("DateUTC"));
    }

}
