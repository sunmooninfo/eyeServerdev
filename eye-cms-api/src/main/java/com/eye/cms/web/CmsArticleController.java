package com.eye.cms.web;

import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleAttribute;
import com.eye.db.domain.SEyeArticle;
import com.eye.db.service.EyeAccessoryService;
import com.eye.db.service.EyeArticleAttributeService;
import com.eye.db.service.EyeArticleService;
import com.eye.db.service.EyeSystemConfigService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cms/article")
@Validated
@Api(description = "文章服务")
public class CmsArticleController {
    private final Log logger = LogFactory.getLog(CmsArticleController.class);

    @Autowired
    private EyeArticleService articleService;
    @Autowired
    private EyeArticleAttributeService attributeService;
    @Autowired
    private EyeAccessoryService accessoryService;

    @Autowired
    private EyeSystemConfigService systemConfigService;

    @GetMapping("/list")
    @ApiOperation("文章列表")
    @ApiImplicitParam(name = "categoryId",value = "分类id",required=false,paramType="path",dataType="int")
    public Object list(Integer categoryId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){
        List<EyeArticle> list = articleService.querySelective(categoryId,page,limit,sort,order);
        return ResponseUtil.okList(list);
    }

    @GetMapping("/detail")
    @ApiOperation("文章详情")
    @ApiImplicitParam(name = "id",value = "文章id",required=true,paramType="path",dataType="int")
    public Object detail(@NotNull Integer id){
        EyeArticle article = articleService.findById(id);
        List<EyeArticleAttribute> attributes = attributeService.queryByAid(id);
        //文章附件
        EyeAccessory accessory = accessoryService.queryByGid(article.getId(), 1);

        Map<String,Object> date = new HashMap<>();
        date.put("article",article);
        date.put("attributes",attributes);
        date.put("accessory",accessory);
        return ResponseUtil.ok(date);
    }

    /**
     * 根据关键字查询
     * @param query
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("根据关键字搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name="query",value = "查询关键字",required=true,paramType="path",dataType="String"),
            @ApiImplicitParam(name="page",value = "显示页数",required=false,paramType="path",dataType="Integer"),
            @ApiImplicitParam(name="limit",value = "显示行数",required=false,paramType="path",dataType="Integer"),
            @ApiImplicitParam(name="sort",value = "排序字段",required=false,paramType="path",dataType="String"),
            @ApiImplicitParam(name="order",value = "排序规则",required=false,paramType="path",dataType="String")
    })
    public Object search(String query,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer limit,
                         @Sort(accepts = {"add_time"}) @RequestParam(defaultValue = "add_time") String sort,
                         @Order @RequestParam(defaultValue = "desc") String order){
        List<SEyeArticle> list = (List<SEyeArticle>)articleService.search(query, page, limit, sort, order);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("queryResult", list);
        data.put("categoryId",1036030);
        if (list instanceof Page) {
            Page pages = (Page) list;
            data.put("total", pages.getTotal());
            data.put("page", pages.getPageNum());
            data.put("limit", pages.getPageSize());
            data.put("pages", pages.getPages());
        } else {
            data.put("total", list.size());
            data.put("page", 1);
            data.put("limit", list.size());
            data.put("pages", 1);
        }
        return ResponseUtil.ok(data);
    }

    @GetMapping("/certificate")
    @ApiOperation("证书")
    public Object certificate(){

        Map<String, String> map = systemConfigService.listMail();
        String certificate = map.get("eye_mall_certificate");

        Map<String,String> data = new HashMap<>();
        data.put("certificate", certificate);

        return ResponseUtil.ok(data);
    }
}
