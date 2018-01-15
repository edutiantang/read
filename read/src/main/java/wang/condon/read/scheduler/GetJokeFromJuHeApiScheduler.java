package wang.condon.read.scheduler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.condon.read.business.ReadBusinessLogic;
import wang.condon.read.entity.Content;
import wang.condon.read.repository.ContentRepository;

import java.util.Date;

@Component
public class GetJokeFromJuHeApiScheduler {

    @Autowired
    private ContentRepository contentRepository;
    private static final Logger logger = LoggerFactory.getLogger(GetJokeFromJuHeApiScheduler.class);
    private JSONArray data;
    private int countInvoke = 27158;

    //@Scheduled(fixedRate = 2000)
    public void executeDownloadTask() {
        logger.info("before invoke api, countInvoke : " + countInvoke);
        String jsonResult = new ReadBusinessLogic().getJokeList(countInvoke);
        logger.info(jsonResult);
        JSONObject response = new JSONObject(jsonResult);

        if (null == response) {
            return;
        }

        JSONObject result = response.getJSONObject("result");

        if (null == result) {
            return;
        }

        data = result.getJSONArray("data");

        if (null == data) {
            return;
        }

        for (int i = 0; i < data.length(); i++) {
            JSONObject joke = data.getJSONObject(i);

            if (null == joke) {
                continue;
            }

            Content content = new Content();
            content.setValue(joke.getString("content"));
            content.setHashId(joke.getString("hashId"));
            content.setCategory("joke");
            Date now = new Date();
            content.setCreatedTime(now);
            content.setUpdatedTime(now);
            content.setCreatedBy("schedulerJob");
            content.setUpdatedBy("schedulerJob");
            contentRepository.saveAndFlush(content);
        }

        countInvoke ++;
        logger.info("after invoke api, countInvoke : " + countInvoke);
    }
}