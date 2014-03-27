package assignment2.driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import assignment2.ShareLogic;
import assignment2.WebService;

public class StockProgram {

    public Map<String, Integer> readSharesFromFile(String fileName) {
        BufferedReader reader = null;
        Map<String, Integer> inputShares = new LinkedHashMap<String, Integer>();
        
        try {
            reader = new BufferedReader(new FileReader(fileName));            
            String line = null;

            while ((line = reader.readLine()) != null) {
                String[] valuesInLine = line.split(" ");
                String ticker = valuesInLine[0];
                int numberOfShares = Integer.parseInt(valuesInLine[1]);

                inputShares.put(ticker, numberOfShares);
            }            
        }
        catch (NumberFormatException|IOException e) {
            System.out.println("File was not found or could not be opened.");
            throw new RuntimeException("File "+fileName+" not found");
        }
        finally {
        	try {reader.close();} catch (IOException e) {}
        }
        
        return inputShares;
    }
        
    private void printReport(Map<String, Double> shareDetails) {
    	NumberFormat fmt = NumberFormat.getCurrencyInstance();    
    	
    	System.out.println("Share\tNumberOfShares\tNetValue");
    	for(String shareDetail : shareDetails.keySet()){
    		if(shareDetail.contains("Net Asset Value")) break;
    		
    		String firstPart = shareDetail.replace(" ", "\t");
    		System.out.println(firstPart + "\t\t" + fmt.format(shareDetails.get(shareDetail)));
    	}
    	System.out.println("----------------------------------");
    	System.out.println("Net Asset Value: \t" + fmt.format(shareDetails.get("Net Asset Value: ")));
    }    
    
    public static void main(String[] args) {
    	try {
    		StockProgram driver = new StockProgram();    	           
    		ShareLogic logic = new ShareLogic(new WebService());
    		
    		Map<String, Double> reportDetails = logic.shareValuesAndNet(driver.readSharesFromFile("Stock.txt"));
    		driver.printReport(reportDetails);
    	}
    	catch(Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }  
    }
}
