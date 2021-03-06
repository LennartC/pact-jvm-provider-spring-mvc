package sample;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {

    private MyService myResponseService;

    @RequestMapping(value = "/json", method = RequestMethod.GET, consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> json() {
        return myResponseService.getResponse();
    }

    @RequestMapping(value = "/cookies", method = RequestMethod.GET, consumes = "application/json", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> cookies(@CookieValue("cookie1") String cookie1, @CookieValue("cookie2") String cookie2) {
        return myResponseService.getResponseForCookies(new String[]{cookie1, cookie2});
    }

    public MyController withMyResponseService(MyService myResponseService) {
        this.myResponseService = myResponseService;
        return this;
    }
}
