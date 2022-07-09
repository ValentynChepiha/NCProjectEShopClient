package ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.*;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.*;

@Controller
@PropertySource("classpath:application.properties")
public class MainController {

    private static final LoggerMsgService logger = new LoggerMsgService(MainController.class) ;

    private final EndpointBasketPostService endpointBasketPostService;
    private final EndpointBasketDonePostService endpointBasketDonePostService;
    private final CheckBeforeLoadService checkBeforeLoadService;
    private final LoadGoodsService loadGoodsService;
    private final LoadExchangeService loadExchangeService;
    private final ParseBasketDataValue parseBasketDataValue;

    @Value("${api.url.load.goods}")
    private String urlLoadGoods;
    @Value("${api.url.create.order}")
    private String urlCreateOrder;

    @Autowired
    public MainController(EndpointBasketPostService endpointBasketPostService, EndpointBasketDonePostService endpointBasketDonePostService,
                          CheckBeforeLoadService checkBeforeLoadService, LoadGoodsService loadGoodsService,
                          LoadExchangeService loadExchangeService, ParseBasketDataValue parseBasketDataValue) {
        this.endpointBasketPostService = endpointBasketPostService;
        this.endpointBasketDonePostService = endpointBasketDonePostService;
        this.checkBeforeLoadService = checkBeforeLoadService;
        this.loadGoodsService = loadGoodsService;
        this.loadExchangeService = loadExchangeService;
        this.parseBasketDataValue = parseBasketDataValue;
    }

    @RequestMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {}

    @GetMapping("/")
    public String mainGet(Model model) {

        logger.msgInfo("Start root endpoint.");
        checkBeforeLoadService.checkUpdateExchangeRate();
        model.addAttribute("exchange", loadExchangeService.loadCurrencyRate());
        model.addAttribute("goods", loadGoodsService.load(urlLoadGoods));
        return "welcome";
    }

    @GetMapping("/{cur}")
    public String changeCurrencyGet(Model model, @PathVariable String cur) {
        logger.msgInfo("Change currency on " + cur);
        checkBeforeLoadService.checkUpdateExchangeRate();
        model.addAttribute("selectedCurrency", cur);
        model.addAttribute("exchange", loadExchangeService.loadCurrencyRate());
        model.addAttribute("goods", loadGoodsService.convertGoodsPriceUseExchangeRate(
                urlLoadGoods,
                loadExchangeService.getSelectedCurrencyRate(cur) )
        );
        return "welcome";
    }

    @PostMapping("/basket")
    public String basketPost(@RequestBody String orderBody, Model model) {
        logger.msgInfo("Run endpoint /basket");
        if(!endpointBasketPostService.start(orderBody)){
            return  endpointBasketPostService.urlGoHome();
        }
        model.addAttribute("goods", endpointBasketPostService.getGoods());
        model.addAttribute("selectedCurrency", endpointBasketPostService.getSelectedCurrency());
        model.addAttribute("mapIdCount", endpointBasketPostService.getMapIdCount());
        model.addAttribute("totalSum", parseBasketDataValue.getTotal(
                endpointBasketPostService.getMapIdCount(),
                endpointBasketPostService.getGoods().getGoods())
        );
        return  "basket";
    }

    @PostMapping("/basket/done")
    public String basketDonePost(@RequestBody String orderBody, Model model) {
        logger.msgInfo("Run endpoint /basket/done");

        if( !endpointBasketDonePostService.start(orderBody) ) {
            return endpointBasketDonePostService.urlGoHome();
        }

        String result = UploadService.upload(urlCreateOrder,
                endpointBasketDonePostService.prepareMapToUpload());

        model.addAttribute("result", "ok".equals(result) ? "Order placed" : "Error. Repeat later");
        return "basket-done";
    }

}

