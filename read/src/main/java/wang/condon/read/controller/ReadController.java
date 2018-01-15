package wang.condon.read.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import wang.condon.read.business.ReadBusinessLogic;
import wang.condon.read.response.GetRandomContentResponse;
import wang.condon.read.response.ResponseModel;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class ReadController {
    @RequestMapping(value = "/api/{pathVariable}",
            method = RequestMethod.POST,
            headers = {"Constant-Type=application/json"})
    ResponseModel dealPost(
            @PathVariable String pathVariable,
            @RequestBody ResponseModel responseModel,
            @RequestHeader("AccessToken") String accessToken) {

        return new ReadBusinessLogic().getData(pathVariable, responseModel.getName(), accessToken);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    String showMainPage() {
        return "loading......";
    }

    @RequestMapping(value = "/api/v1/portal/random-content",method = RequestMethod.GET)
    GetRandomContentResponse getRandomContent() {
        //return new ReadBusinessLogic().getRandomContent();
        return new ReadBusinessLogic().getRandomContent();
    }

    @RequestMapping(value = "/api/v1/portal/random-joke",method = RequestMethod.GET)
    String getRandomJoke() {
        return new ReadBusinessLogic().getRandomJoke();
    }
}