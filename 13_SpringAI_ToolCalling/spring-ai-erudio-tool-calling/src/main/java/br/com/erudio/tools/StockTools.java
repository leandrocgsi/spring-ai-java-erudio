package br.com.erudio.tools;

import br.com.erudio.api.DailyStockData;
import br.com.erudio.api.StockData;
import br.com.erudio.api.StockResponse;
import br.com.erudio.services.StockService;
import br.com.erudio.settings.APISettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class StockTools {

    private static final Logger logger = LoggerFactory.getLogger(StockTools.class);

    @Value("${TWELVE_DATA_API_KEY:none}")
    String apiKey;

    private RestTemplate restTemplate;

    public StockTools(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Tool(description = "Latest stock prices")
    public StockResponse getLatestStockPrices(@ToolParam(description = "Name of Company") String company){

        logger.info("Get stock prices for: {}", company);

        StockData data = restTemplate.getForObject(
                APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1day&outputsize=1&apikey={1}",
                StockData.class,
                company,
                apiKey);

        DailyStockData latestData = data.getValues().get(0);

        logger.info("Get stock prices ({}) -> {}", company, latestData);

        return new StockResponse(Float.parseFloat(latestData.getClose()));

    }
}
