package com.eye.admin.web;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.eye.admin.annotation.RequiresPermissionsDesc;
import com.eye.admin.dto.ArticleAllinone;
import com.eye.core.rabbitmq.MessageSender;
import com.eye.core.utils.ESConstant;
import com.eye.core.utils.ResponseUtil;
import com.eye.core.validator.Order;
import com.eye.core.validator.Sort;
import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeArticle;
import com.eye.db.domain.EyeArticleAttribute;
import com.eye.db.domain.EyeCategory;
import com.eye.db.domain.SEyeArticle;
import com.eye.db.service.EyeAccessoryService;
import com.eye.db.service.EyeArticleAttributeService;
import com.eye.db.service.EyeArticleService;
import com.eye.db.service.EyeCategoryService;
import com.eye.storage.service.EyeStorageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "文章管理")
@RestController
@RequestMapping("/admin/article")
@Validated
public class AdminArticleController {
    private final Log logger = LogFactory.getLog(AdminArticleController.class);

    @Autowired
    private EyeArticleService articleService;
    @Autowired
    private EyeArticleAttributeService attributeService;
    @Autowired
    private EyeCategoryService categoryService;
    @Autowired
    private EyeAccessoryService accessoryService;
    @Autowired
    private EyeStorageService storageService;
    @Autowired
    private MessageSender messageSender;

    @ApiOperation(value = "文章列表查询")
    @ApiImplicitParam(name="title",value = "文章标题",required=false,paramType="path",dataType="string")
    @RequiresPermissions("admin:article:list")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章列表"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer id,String title,Integer categoryId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order){

        List<SEyeArticle> list = articleService.querySelective(id, title, categoryId, page, limit, sort, order);
        int count = articleService.count();
        return ResponseUtil.okList(list,count,page,limit);
    }

    private Object validate(EyeArticle article) {
        Integer categoryId = article.getCategoryId();
        if (categoryId == null) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @ApiOperation(value = "文章列表添加")
    @RequiresPermissions("admin:article:create")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章列表"}, button = "添加")
    @PostMapping("/create")
    @Transactional
    public Object create(@RequestBody ArticleAllinone allinone){
        EyeArticle article = allinone.getArticle();
        EyeArticleAttribute[] attributes = allinone.getAttributes();
        EyeAccessory accessory = allinone.getAccessory();

        Object error = validate(article);
        if (error != null){
            return error;
        }

        articleService.add(article);

        if (attributes != null || attributes.length != 0) {
            for (EyeArticleAttribute attribute : attributes) {
                attribute.setArticleId(article.getId());
                attributeService.add(attribute);
            }
        }

        //如果商品有附件，保存附件信息
        if(accessory != null && accessory.getName() == null && accessory.getAccessoryLink() == null ){

        }else if(accessory !=null || accessory.getAccessoryLink() !=null  || accessory.getAccessoryCode() !=null){
            accessory.setGoodsId(article.getId());
            if(accessory.getName()== null) {
                accessory.setName(article.getTitle());
            }
            accessoryService.add(accessory,1);
        }else if(accessory != null || accessory.getName() !=null){
            accessory.setGoodsId(article.getId());
            accessoryService.add(accessory,1);
        }

        //更新ES索引库
//        messageSender.updateIndex(ESConstant.indexName);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "文章列表编辑")
    @RequiresPermissions("admin:article:update")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章列表"}, button = "编辑")
    @PostMapping("/update")
    @Transactional
    public Object update(@RequestBody ArticleAllinone allinone){
        EyeArticle article = allinone.getArticle();
        EyeArticleAttribute[] attributes = allinone.getAttributes();
        EyeAccessory accessory = allinone.getAccessory();

        Object error = validate(article);
        if (error != null){
            return error;
        }

        Integer id = article.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        if (articleService.update(article) == 0){
            return ResponseUtil.updatedDataFailed();
        }

        if (attributes != null || attributes.length != 0){
            for (EyeArticleAttribute attribute : attributes) {
                if (attribute.getId() == null || attribute.getId().equals(0)){
                    attribute.setArticleId(article.getId());
                    attributeService.add(attribute);
                }
                else if(attribute.getDeleted()){
                    attributeService.deleteById(attribute.getId());
                }
                else if(attribute.getUpdateTime() == null){
                    attributeService.updateById(attribute);
                }
            }
        }

        if(accessory !=null){
            EyeAccessory EyeAccessory = accessoryService.queryByGid(article.getId(), 1);
            if(EyeAccessory == null){
                accessory.setGoodsId(article.getId());
                if(accessory.getName() == null){
                    accessory.setName(article.getTitle());
                }
                accessoryService.add(accessory,1);
            }else if(accessory.getDeleted()){
                if(!StringUtils.isEmpty(EyeAccessory.getKey())){
                    storageService.deleteByKey(EyeAccessory.getKey());
                }
                accessoryService.deleteById(EyeAccessory.getId());
            }else if(EyeAccessory != null){
                EyeAccessory.setKey(accessory.getKey());
                EyeAccessory.setAddtime(accessory.getAddtime());
                EyeAccessory.setAccessoryCode(accessory.getAccessoryCode());
                EyeAccessory.setAccessoryLink(accessory.getAccessoryLink());
                EyeAccessory.setName(accessory.getName());
                EyeAccessory.setSize(accessory.getSize());
                EyeAccessory.setType(accessory.getType());
                EyeAccessory.setUrl(accessory.getUrl());
                accessoryService.updateById(EyeAccessory);
            }
        }

        //更新ES索引库
//        messageSender.updateIndex(ESConstant.indexName);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "文章列表详情")

    @RequiresPermissions("admin:article:detail")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章列表"}, button = "详情")
    @ApiImplicitParam(name="id",value = "文章id",required=true,paramType="path",dataType="int")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id){

        EyeArticle article  = articleService.findById(id);
        EyeCategory category = categoryService.findById(article.getCategoryId());
        List<EyeArticleAttribute> attributes = attributeService.queryByAid(id);

        Integer[] categoryIds = new Integer[]{};
        if (category != null) {
            Integer parentCategoryId = category.getPid();
            if (parentCategoryId == 0){
                categoryIds = new Integer[]{article.getCategoryId()};
            }else {
                categoryIds = new Integer[]{parentCategoryId, article.getCategoryId()};
            }
        }

        EyeAccessory accessory = accessoryService.queryByGid(id, 1);

        Map<String, Object> data = new HashMap<>();
        data.put("article",article);
        data.put("categoryIds",categoryIds);
        data.put("attributes",attributes);
        data.put("accessory",accessory);
        return ResponseUtil.ok(data);
    }


    @ApiOperation(value = "文章列表删除")
    @RequiresPermissions("admin:article:delete")
    @RequiresPermissionsDesc(menu = {"文章管理", "文章列表"}, button = "删除")
    @PostMapping("/delete")
    @Transactional
    public Object delete(@RequestBody EyeArticle article){
        Integer id = article.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        articleService.delete(id);
        attributeService.deleteByAid(id);
        accessoryService.deleteByGid(id,1);

        //更新ES索引库
//        messageSender.updateIndex(ESConstant.indexName);
        return ResponseUtil.ok();
    }

}