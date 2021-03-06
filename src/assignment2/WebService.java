package assignment2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class WebService implements PriceWebService {	
    private final String yahooStockUrlPrefix = "http://ichart.finance.yahoo.com/table.csv?s=";

    public double getStockPrice(String ticker) throws Exception {
        double stockValue = -1;

        URL url = new URL(yahooStockUrlPrefix + ticker);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String data = "";
        
        br.readLine();
        data = br.readLine();

        String[] valuesInCsv = data.split(",");
        stockValue = Double.parseDouble(valuesInCsv[valuesInCsv.length - 1]);

        return stockValue;
    }
}
