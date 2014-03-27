package assignment2;

import java.util.LinkedHashMap;
import java.util.Map;
import junit.framework.TestCase;

public class ShareLogicTest extends TestCase {

    private ShareLogic shareLogic;
    private final double TOLERANCE = 0.00001;

    public void setUp() {
        shareLogic = new ShareLogic();
    }

    public void testCanary() {
        assertTrue(true);
    }

    public void testCalculateValueOfShare() {
        assertEquals(400.00, shareLogic.calculateValueOfShare(20, 20), TOLERANCE);
    }

    public void testCalculateValueOfZeroShare() {
        assertEquals(0, shareLogic.calculateValueOfShare(0, 20), TOLERANCE);
    }

    public void testcalculateValueOfShareWithZeroPrice() {
        assertEquals(0, shareLogic.calculateValueOfShare(20, 0), TOLERANCE);
    }

    public void testcalculateValueOfShareWithInvalidPrice() {
        assertEquals(0, shareLogic.calculateValueOfShare(20, -6), TOLERANCE);
    }

    public void testcalculateValueOfShareWithInvalidShareNumber() {
        assertEquals(0, shareLogic.calculateValueOfShare(-20, 6.7), TOLERANCE);
    }

    public void testcalculateValueOfShareWithInvalidPriceAndShareNumber() {
        assertEquals(0, shareLogic.calculateValueOfShare(-20, -136.7), TOLERANCE);
    }

    public void testCalculateNetWorthOfShares() {
        double values[] = {1, 2, 3, 4, 5};
        assertEquals(15.0, shareLogic.calculateNetAssetValue(values), TOLERANCE);
    }

    public void testCalculateNetWorthOfSharesWithNegativeValues() {
        double values[] = {-1, 2.2, -3, 4, -5};
        assertEquals(6.2, shareLogic.calculateNetAssetValue(values), TOLERANCE);
    }

    public void testCalculateNetWorthOfSharesWithAllNegativeValues() {
        double values[] = {-13, -2.2, -3, -44, -5, -35};
        assertEquals(0.0, shareLogic.calculateNetAssetValue(values), TOLERANCE);
    }

    public void testCalculateNetWorthOfSharesWithEmptyArray() {
        double values[] = {};
        assertEquals(0, shareLogic.calculateNetAssetValue(values), TOLERANCE);
    }

    public void testCalculateNetWorthOfSharesWithArrayOfZeros() {
        double values[] = {0, 0, 0, 0};
        assertEquals(0, shareLogic.calculateNetAssetValue(values), TOLERANCE);
    }

    public void testCalculateNetWorthOfSharesWithNullValue() {
        assertEquals(0, shareLogic.calculateNetAssetValue(null), TOLERANCE);
    }
    
    public void testGetStockPriceForAValidTicker() throws Exception {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) {
                return 1212.51;
            }
        });
        assertEquals(1212.51, shareLogic.getStockPrice("GOOG"), TOLERANCE);
    }

    public void testGetStockPriceForInvalidTicker() {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) throws Exception {
                throw new RuntimeException("Invalid ticker");
            }
        });

        try {
            shareLogic.getStockPrice("INVALID");
            fail("Expected getStockPrice to throw an exception for invalid ticker");
        } catch (Exception e) {
        }
    }

    public void testGetStockPriceWhenNoInternetConnection() {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) throws Exception {
                throw new RuntimeException("No network connection");
            }
        });

        try {
            shareLogic.getStockPrice("GOOG");
            fail("Expected getStockPrice to throw an exception for no network connection");
        } catch (Exception e) {
        }
    }

    public void testCalculateReportResultForOneTicker() throws Exception {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) {
                return 1212.51;
            }
        });        
        
        Map<String, Integer> tickerList = new LinkedHashMap<String, Integer>() { {put("GOOG", 30);}};
        
        Map<String, Double> outputExpected = new LinkedHashMap<String, Double>() { {
            put("GOOG 30", 36375.3);
            put("Net Asset Value: ", 36375.3);
        }
        };

        assertEquals(outputExpected, shareLogic.shareValuesAndNet(tickerList));
    }
    
    public void testCalculateReportResultForTwoTicker() throws Exception {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) {
                return 100.12;
            }
        });        
        
        Map<String, Integer> tickerList = new LinkedHashMap<String, Integer>() {{
            put("GOOG", 6);
            put("AAPL", 9);
        }};
        
        Map<String, Double> outputExpected = new LinkedHashMap<String, Double>() {{
            put("GOOG 6", 600.72);
            put("AAPL 9", 901.08);
            put("Net Asset Value: ", 1501.80);
        }};

        assertEquals(outputExpected, shareLogic.shareValuesAndNet(tickerList));
    }
    
    public void testCalculateReportResultForInvalidTicker() throws Exception {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) {
                throw new RuntimeException("Invalid ticker");
            }
        });        
        
        Map<String, Integer> tickerList = new LinkedHashMap<String, Integer>() { {put("INVALID", 30);}};
        
        try {
            shareLogic.shareValuesAndNet(tickerList);
            fail("Expected shareValuesAndNet to throw an exception for invalid ticker");
        } catch (Exception e) {
        }
    }
    
    public void testCalculateReportResultForNullTicker() throws Exception {
        shareLogic = new ShareLogic(new PriceWebService() {
            @Override
            public double getStockPrice(String ticker) {
                return 10.5;
            }
        });        
                
        try {
            shareLogic.shareValuesAndNet(null);
            fail("Expected shareValuesAndNet to throw an exception for null ticker");
        } catch (Exception e) {
        }
    }
}
