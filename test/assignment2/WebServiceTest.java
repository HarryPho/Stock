package assignment2;

import junit.framework.TestCase;

public class WebServiceTest extends TestCase {

    private WebService webService;

    public void setUp() {
        webService = new WebService();
    }

    public void testCanary() {
        assertTrue(true);
    }

    public void testNetworkFailureForWebService() {
        WebService webService = new WebService() {
            public double getStockPrice(String ticker) {
                throw new RuntimeException();
            }
        };

        try {
            webService.getStockPrice("GOOG");
            fail("Expected getStockPrice to throw an exception for network failure");
        } catch (Exception e) {
        }
    }
}
