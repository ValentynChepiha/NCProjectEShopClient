package ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.config.ConfigApp;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.xml.Goods;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final LoggerMsgService logger = new LoggerMsgService(MainController.class) ;

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
        model.addAttribute(
                "goods",
                loadGoodsService.convertGoodsPriceUseExchangeRate(
                        ConfigApp.URL_LOAD_GOODS,
                        loadExchangeService.getSelectedCurrencyRate(cur)
                )
        );
        return "welcome";
    }

    @PostMapping("/basket")
    public String basketPost(@RequestBody String orderBody, Model model) {
        logger.msgInfo("Run endpoint /basket");

        parseBasketDataValue.setStringToListString(orderBody, "&");
        String selectedCurrency = parseBasketDataValue.getSelectedCurrency();
        String goHome = "redirect:/" + selectedCurrency;
        List<String> selectedProducts = parseBasketDataValue.getSelectedProducts();

        logger.msgDebug("/basket: selectedCurrency - " + selectedCurrency);
        logger.msgDebug("/basket: selectedProducts - " + selectedProducts);
        if(selectedProducts.size() == 0){
            return goHome;
        }

        Map<Long, Integer> mapIdCount = parseBasketDataValue.getMapSelectedIdCount(selectedProducts);
        System.out.println(" parse in basket : mapIdCount :: " + mapIdCount);

        if( mapIdCount.size()==0 ){
            return goHome;
        }
        List<Long> listId = parseBasketDataValue.getListSelectedId(mapIdCount);
        System.out.println(" parse in basket : listId :: " + listId);

        Goods goods = loadGoodsService.convertGoodsPriceUseExchangeRate(
                ConfigApp.URL_LOAD_GOODS + "/" + parseBasketDataValue.concatenateId(listId, ","),
                loadExchangeService.getSelectedCurrencyRate(selectedCurrency)
        );

        model.addAttribute("goods", goods);
        model.addAttribute("selectedCurrency", selectedCurrency);
        model.addAttribute("mapIdCount", mapIdCount);
        model.addAttribute("totalSum", parseBasketDataValue.getTotal(mapIdCount, goods.getGoods()) );
        return  "basket";
    }

}
