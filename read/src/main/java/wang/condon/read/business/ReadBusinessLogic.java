package wang.condon.read.business;

import wang.condon.read.util.HttpUtil;
import wang.condon.read.response.GetRandomContentResponse;
import wang.condon.read.response.ResponseModel;

import java.util.Random;

public class ReadBusinessLogic {

    public ResponseModel getData(String pathVariable, String name, String accessToken) {

        return new ResponseModel(pathVariable, name, accessToken);
    }

    public GetRandomContentResponse getRandomContent() {

        GetRandomContentResponse response = new GetRandomContentResponse();
        int temp = new Random().nextInt(542729);
        String content = String.valueOf(temp);
        response.setContent(content);

        return response;
    }

    public String getRandomJoke() {
        int temp = new Random().nextInt(542730);
        String content = "";
        try {
            content = HttpUtil.get("http://japi.juhe.cn/joke/content/text.from?page=" + temp + "&pagesize=1&key=d2120da13d039c56d2940312ca27107c", null);
        } catch (Exception e) {
        }

        return content;
    }

    public String getJokeList(int currentPage) {
        String content = "";
        try {
            content = HttpUtil.get("http://japi.juhe.cn/joke/content/text.from?page=" + currentPage + "&pagesize=20&key=d2120da13d039c56d2940312ca27107c", null);
        } catch (Exception e) {
        }

        return content;
    }
}