package assignment2;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShareLogic {

    private PriceWebService _priceWebService;

    ShareLogic() {}

    public ShareLogic(PriceWebService priceWebService) {
        _priceWebService = priceWebService;
    }

    double calculateValueOfShare(int numberOfShares, double price) {
        if (numberOfShares < 0 || price < 0)
            return 0;

        return numberOfShares * price;
    }

    double calculateNetAssetValue(double ValuesOfShares[]) {
        if(ValuesOfShares == null) return 0;
    	double netAsset = 0;

        for (double price : ValuesOfShares)
            if (price > 0)
                netAsset += price;

        return Math.round(netAsset * 100.0) / 100.0;
    }

    double getStockPrice(String ticker) throws Exception {
        return _priceWebService.getStockPrice(ticker);
    }

    public Map<String, Double> shareValuesAndNet(Map<String, Integer> stockData) throws Exception {
        Map<String, Double> outputLines = new LinkedHashMap<String, Double>();
        double[] valuesOfShares = new double[stockData.size()];

        for (Map.Entry<String, Integer> stockLine : stockData.entrySet()) {
            String ticker = stockLine.getKey();
            int numberOfShares = stockLine.getValue();
            
            double worth = calculateValueOfShare(numberOfShares, getStockPrice(ticker));
            
            outputLines.put(ticker + " " + numberOfShares, worth);
            valuesOfShares[outputLines.size() - 1] = worth;
        }
        
        outputLines.put("Net Asset Value: ", calculateNetAssetValue(valuesOfShares));

        return outputLines;
    }
}
