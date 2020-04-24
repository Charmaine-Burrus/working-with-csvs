import edu.duke.*;
import org.apache.commons.csv.*;

/**
 * Write a description of Assignment1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CountryExports {
    public String countryInfo(CSVParser parser, String countryOfInterest) {
        String countryInfo = "";
        for (CSVRecord record : parser) {
            String country = record.get("Country");
            if (country.contains(countryOfInterest)) {
                String exports = record.get("Exports");
                String value = record.get("Value (dollars)");
                countryInfo = country + ": " + exports + ": " + value;
                //don't know if break is the best way or if I should assign countryInfo as "not found" when I declare it and then not have an else statement
                //what if country is in the list twice? 
                break;
            } else {
                    countryInfo = "NOT FOUND";
            }
        }
        return countryInfo;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem1)) {
                if (exports.contains(exportItem2)) {
                    System.out.println(record.get("Country"));
                }
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int number = 0;
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem)) {
                number++;
            }
        }
        return number;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            String value = record.get("Value (dollars)");
            if (value.length() > amount.length()) {
                System.out.println(record.get("Country") + " " + value);
            }
        }
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //call 1st method... each time that call parser on a method, reset by calling fr.getSCVParser() to get a new parser
        listExportersTwoProducts(parser, "gold", "diamonds");
        
        parser = fr.getCSVParser();
        int numberOfExporters = numberOfExporters(parser, "sugar");
        System.out.println(numberOfExporters);
        
        parser = fr.getCSVParser();
        String countryInfo = countryInfo(parser, "Nauru");
        System.out.println(countryInfo);
        
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
        
        
    }

}

