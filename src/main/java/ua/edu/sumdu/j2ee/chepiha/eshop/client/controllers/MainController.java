package ua.edu.sumdu.j2ee.chepiha.eshop.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.CheckBeforeLoad;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.LoadGoodsService;

@Controller
public class MainController {

    @Autowired
    CheckBeforeLoad checkBeforeLoad;
    @Autowired
    LoadGoodsService loadGoodsService;

    // todo:
    //        example
    //        https://www.oreilly.com/library/view/java-network-programming/1565928709/ch15s02.html

    @GetMapping("/")
    public String mainGet(Model model) {
        checkBeforeLoad.checkUpdateExchangeRate();

        model.addAttribute("goods", loadGoodsService.load());

        return "welcome";
    }


}
