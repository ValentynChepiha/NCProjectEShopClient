package ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigApp;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.*;

@Controller
public class MainController {

    private static final LoggerMsgService logger = new LoggerMsgService(MainController.class) ;

    @Autowired
    private EndpointBasketPostService endpointBasketPostService;
    @Autowired
    private EndpointBasketDonePostService endpointBasketDonePostService;
    @Autowired
    private CheckBeforeLoad checkBeforeLoad;
    @Autowired
    private LoadGoodsService loadGoodsService;
    @Autowired
    private LoadExchangeService loadExchangeService;
    @Autowired
    private ParseBasketDataValue parseBasketDataValue;

    // todo:
    //        example
    //        https://www.oreilly.com/library/view/java-network-programming/1565928709/ch15s02.html
    //        https://www.baeldung.com/java-http-request
    //        https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java

    @RequestMapping("/favicon.ico")
    @ResponseBody
    public void favicon() {}

    @GetMapping("/")
    public String mainGet(Model model) {
        logger.msgInfo("Start root endpoint.");
        checkBeforeLoad.checkUpdateExchangeRate();
        model.addAttribute("exchange", loadExchangeService.loadCurrencyRate());
        model.addAttribute("goods", loadGoodsService.load(ConfigApp.URL_LOAD_GOODS));
        return "welcome";
    }

    @GetMapping("/{cur}")
    public String changeCurrencyGet(Model model, @PathVariable String cur) {
        logger.msgInfo("Change currency on " + cur);
        checkBeforeLoad.checkUpdateExchangeRate();
        model.addAttribute("selectedCurrency", cur);
        model.addAttribute("exchange", loadExchangeService.loadCurrencyRate());
        model.addAttribute("goods", loadGoodsService.convertGoodsPriceUseExchangeRate(
                        ConfigApp.URL_LOAD_GOODS,
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

        String result = UploadService.upload(ConfigApp.URL_CREATE_ORDER,
                             endpointBasketDonePostService.prepareMapToUpload());

        model.addAttribute("result", "ok".equals(result) ? "Order placed" : "Error. Repeat later");
        return "basket-done";
    }

}
