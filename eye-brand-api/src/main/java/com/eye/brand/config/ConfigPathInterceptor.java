package com.eye.brand.config;

import com.eye.db.domain.EyeBrandWeb;
import com.eye.db.service.EyeBrandWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ConfigPathInterceptor implements HandlerInterceptor {

    @Autowired
    private EyeBrandWebService langboBrandService;

    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //业务代码
        String id = request.getParameter("id");

        Integer brandId = Integer.parseInt(id);

        EyeBrandWeb brand = langboBrandService.findById(brandId);
        if (brand != null) {
            Long l = Long.valueOf(1);
            brand.setClicks(brand.getClicks() + l);
            langboBrandService.update(brand);

        }

        return true;
    }

}
