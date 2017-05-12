package jtelecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Nikita on 02.05.2017.
 */
@Controller
public class AboutController {

    @RequestMapping(value = "/about")
    public String about() {
        return "newPages/About";
    }
}
