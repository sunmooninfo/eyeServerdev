package com.eye.common.config;

import com.eye.db.domain.EyeTopic;
import com.eye.db.service.EyeTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonConfigPathInterceptor implements HandlerInterceptor {

    @Autowired
   private EyeTopicService topicService;

    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //业务代码
        String id = request.getParameter("id");

        Integer topicId = Integer.parseInt(id);

        EyeTopic topic = topicService.findById(topicId);
        if (topic != null) {
            String readCount = topic.getReadCount();

            Integer sum = Integer.parseInt(readCount)+ 1;
            topic.setReadCount(""+sum);
            topicService.updateById(topic);

        }

        return true;
    }

}
