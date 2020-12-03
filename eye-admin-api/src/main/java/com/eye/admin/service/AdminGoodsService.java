package com.eye.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eye.admin.dto.GoodsAllinone;
import com.eye.admin.task.KillGoodsExpiredTask;
import com.eye.admin.task.KillGoodsStartTask;
import com.eye.admin.vo.CatVo;
import com.eye.core.qcode.QCodeService;
import com.eye.core.task.TaskService;
import com.eye.core.utils.ResponseUtil;
import com.eye.db.domain.EyeAccessory;
import com.eye.db.domain.EyeBrand;
import com.eye.db.domain.EyeCategory;
import com.eye.db.domain.EyeGoods;
import com.eye.db.domain.EyeGoodsAttribute;
import com.eye.db.domain.EyeGoodsKill;
import com.eye.db.domain.EyeGoodsProduct;
import com.eye.db.domain.EyeGoodsSpecification;
import com.eye.db.domain.NewEyeGoods;
import com.eye.db.service.EyeAccessoryService;
import com.eye.db.service.EyeBrandService;
import com.eye.db.service.EyeCartService;
import com.eye.db.service.EyeCategoryService;
import com.eye.db.service.EyeGoodsAttributeService;
import com.eye.db.service.EyeGoodsKillService;
import com.eye.db.service.EyeGoodsProductService;
import com.eye.db.service.EyeGoodsService;
import com.eye.db.service.EyeGoodsSpecificationService;
import com.eye.db.service.EyeGrouponRulesService;
import com.eye.db.service.EyeIntegralGoodsService;
import com.eye.db.service.EyeMemberGoodsService;
import com.eye.db.util.KillConstant;
import com.eye.storage.service.EyeStorageService;

import static com.eye.admin.util.AdminResponseCode.GOODS_NAME_EXIST;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
@Service
public class AdminGoodsService {

	private final Log logger = LogFactory.getLog(AdminGoodsService.class);

	@Autowired
	private EyeGoodsService goodsService;
	@Autowired
	private EyeGoodsSpecificationService specificationService;
	@Autowired
	private EyeGoodsAttributeService attributeService;
	@Autowired
	private EyeGoodsProductService productService;
	@Autowired
	private EyeCategoryService categoryService;
	@Autowired
	private EyeBrandService brandService;
	@Autowired
	private EyeCartService cartService;
	@Autowired
	private QCodeService qCodeService;
	@Autowired
	private EyeGoodsKillService goodsKillService;
	@Autowired
	private EyeAccessoryService accessoryService;
	@Autowired
	private EyeStorageService storageService;
	@Autowired
	private EyeIntegralGoodsService integralGoodsService;
	@Autowired
	private EyeMemberGoodsService memberGoodsService;
	@Autowired
	private EyeGrouponRulesService grouponRulesService;
	@Autowired
	private TaskService taskService;

	public Object list(Integer goodsId, String goodsSn, String name, Integer categoryId,
			Integer page, Integer limit, String sort, String order) {
		List<EyeGoods> goodsList = goodsService.querySelective(goodsId, goodsSn, name, categoryId, page, limit, sort, order);

		List<NewEyeGoods> EyeGoodsList = new ArrayList<>();

		for (EyeGoods goods : goodsList) {
			EyeCategory categoryL2 = categoryService.findById(goods.getCategoryId());
			NewEyeGoods EyeGoods = new NewEyeGoods();

			if (categoryL2!=null) {
				if ("L2".equals(categoryL2.getLevel())){
					EyeCategory categoryL1 = categoryService.findById(categoryL2.getPid());
					EyeGoods.setCategoryName(categoryL1.getName()+"/"+categoryL2.getName());
				}else {
					EyeGoods.setCategoryName(categoryL2.getName());
				}
			}

			EyeGoods.setAccessoryCode(goods.getAccessoryCode());
			EyeGoods.setAccessoryKey(goods.getAccessoryKey());
			EyeGoods.setAccessoryLink(goods.getAccessoryLink());
			EyeGoods.setAddTime(goods.getAddTime());
			EyeGoods.setBrandId(goods.getBrandId());
			EyeGoods.setBrief(goods.getBrief());
			EyeGoods.setCategoryId(goods.getCategoryId());
			EyeGoods.setCommissionRate(goods.getCommissionRate());
			EyeGoods.setCounterPrice(goods.getCounterPrice());
			EyeGoods.setDeleted(goods.getDeleted());
			EyeGoods.setDetail(goods.getDetail());
			EyeGoods.setEndDate(goods.getEndDate());
			EyeGoods.setGallery(goods.getGallery());
			EyeGoods.setGoodsSn(goods.getGoodsSn());
			EyeGoods.setId(goods.getId());
			EyeGoods.setIsHot(goods.getIsHot());
			EyeGoods.setIsKill(goods.getIsKill());
			EyeGoods.setIsNew(goods.getIsNew());
			EyeGoods.setIsOnSale(goods.getIsOnSale());
			EyeGoods.setIsShown(goods.getIsShown());
			EyeGoods.setKeywords(goods.getKeywords());
			EyeGoods.setName(goods.getName());
			EyeGoods.setPicUrl(goods.getPicUrl());
			EyeGoods.setRetailPrice(goods.getRetailPrice());
			EyeGoods.setShareUrl(goods.getShareUrl());
			EyeGoods.setSortOrder(goods.getSortOrder());
			EyeGoods.setStartDate(goods.getStartDate());
			EyeGoods.setUnit(goods.getUnit());
			EyeGoods.setUpdateTime(goods.getUpdateTime());

			EyeGoodsList.add(EyeGoods);

		}

        int count = goodsService.count();

		return ResponseUtil.okList(EyeGoodsList,count,page,limit);
	}

	private Object validate(GoodsAllinone goodsAllinone) {
		EyeGoods goods = goodsAllinone.getGoods();
		String name = goods.getName();
		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.badArgument();
		}

		//团长佣金比例
		BigDecimal commission = goods.getCommissionRate();
		if (StringUtils.isEmpty(commission)){
			return ResponseUtil.badArgument();
		}


		// 品牌商可以不设置，如果设置则需要验证品牌商存在
		Integer brandId = goods.getBrandId();
		if (brandId != null && brandId != 0) {
			if (brandService.findById(brandId) == null) {
				return ResponseUtil.badArgumentValue();
			}
		}
		// 分类可以不设置，如果设置则需要验证分类存在
		Integer categoryId = goods.getCategoryId();
		if (categoryId != null && categoryId != 0) {
			if (categoryService.findById(categoryId) == null) {
				return ResponseUtil.badArgumentValue();
			}
		}
		Boolean isKill = goods.getIsKill();
		if(isKill){
			EyeGoodsKill goodskill = goodsAllinone.getGoodsKill();
			if(goodskill ==null){
				return ResponseUtil.badArgumentValue();
			}
			if(goodskill.getKillDate() == null){
				return ResponseUtil.badArgumentValue();
			}
			if(goodskill.getKillPrice() == null || goodskill.getKillPrice().compareTo(new BigDecimal(0)) == -1 ){
				return ResponseUtil.badArgumentValue();
			}
			if( goodskill.getStockCount() == null || goodskill.getStockCount().compareTo(0) == -1){
				return ResponseUtil.badArgumentValue();
			}
		}
		EyeGoodsAttribute[] attributes = goodsAllinone.getAttributes();
		for (EyeGoodsAttribute attribute : attributes) {
			String attr = attribute.getAttribute();
			if (StringUtils.isEmpty(attr)) {
				return ResponseUtil.badArgument();
			}
			String value = attribute.getValue();
			if (StringUtils.isEmpty(value)) {
				return ResponseUtil.badArgument();
			}
		}

		EyeGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
		for (EyeGoodsSpecification specification : specifications) {
			String spec = specification.getSpecification();
			if (StringUtils.isEmpty(spec)) {
				return ResponseUtil.badArgument();
			}
			String value = specification.getValue();
			if (StringUtils.isEmpty(value)) {
				return ResponseUtil.badArgument();
			}
		}

		EyeGoodsProduct[] products = goodsAllinone.getProducts();
		for (EyeGoodsProduct product : products) {
			Integer number = product.getNumber();
			if (number == null || number < 0) {
				return ResponseUtil.badArgument();
			}

			BigDecimal price = product.getPrice();
			if (price == null) {
				return ResponseUtil.badArgument();
			}

			String[] productSpecifications = product.getSpecifications();
			if (productSpecifications.length == 0) {
				return ResponseUtil.badArgument();
			}
		}

		EyeAccessory accessory = goodsAllinone.getAccessory();
		if(accessory != null && accessory.getName() != null ){

			if(StringUtils.isEmpty(accessory.getSize())){
				return ResponseUtil.badArgument();
			}
		}
		return null;
	}

	/**
	 * 编辑商品
	 *
	 * NOTE：
	 * 由于商品涉及到四个表，特别是Eye_goods_product表依赖Eye_goods_specification表，
	 * 这导致允许所有字段都是可编辑会带来一些问题，因此这里商品编辑功能是受限制：
	 * （1）Eye_goods表可以编辑字段；
	 * （2）Eye_goods_specification表只能编辑pic_url字段，其他操作不支持；
	 * （3）Eye_goods_product表只能编辑price, number和url字段，其他操作不支持；
	 * （4）Eye_goods_attribute表支持编辑、添加和删除操作。
	 * （5）Eye_goods_kill表支持编辑、添加和删除操作(秒杀时间封装在一个String[]中）。
	 * （6）Eye_accessory表支持添加，编辑名称，删除。
	 * NOTE2:
	 * 前后端这里使用了一个小技巧：
	 * 如果前端传来的update_time字段是空，则说明前端已经更新了某个记录，则这个记录会更新；
	 * 否则说明这个记录没有编辑过，无需更新该记录。
	 *
	 * NOTE3:
	 * （1）购物车缓存了一些商品信息，因此需要及时更新。
	 * 目前这些字段是goods_sn, goods_name, price, pic_url。
	 * （2）但是订单里面的商品信息则是不会更新。
	 * 如果订单是未支付订单，此时仍然以旧的价格支付。
	 */
	@Transactional
	public Object update(GoodsAllinone goodsAllinone) {
		Object error = validate(goodsAllinone);
		if (error != null) {
			return error;
		}

		EyeGoods goods = goodsAllinone.getGoods();
		EyeGoodsAttribute[] attributes = goodsAllinone.getAttributes();
		EyeGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
		EyeGoodsProduct[] products = goodsAllinone.getProducts();
		EyeGoodsKill goodsKill=goodsAllinone.getGoodsKill();
		EyeAccessory accessory = goodsAllinone.getAccessory();

		//将生成的分享图片地址写入数据库
		String url = qCodeService.createGoodShareImage(goods.getId().toString(), goods.getPicUrl(), goods.getName());
		goods.setShareUrl(url);

		// 商品表里面有一个字段retailPrice记录当前商品的最低价
		BigDecimal retailPrice = new BigDecimal(Integer.MAX_VALUE);
		for (EyeGoodsProduct product : products) {
			BigDecimal productPrice = product.getPrice();
			if(retailPrice.compareTo(productPrice) == 1){
				retailPrice = productPrice;
			}
		}
		goods.setRetailPrice(retailPrice);

		if(goodsKill.getIsKill()==false){
			goods.setIsKill(false);
		}else {
			goods.setIsKill(true);
		}
		goods.setCommissionRate(goods.getCommissionRate().divide(new BigDecimal(100)));
		// 商品基本信息表Eye_goods
		if (goodsService.updateById(goods) == 0) {
			throw new RuntimeException("更新数据失败");
		}

		Integer gid = goods.getId();

		// 商品规格表Eye_goods_specification
		for (EyeGoodsSpecification specification : specifications) {
			// 目前只支持更新规格表的图片字段
			if(specification.getUpdateTime() == null){
				specification.setSpecification(null);
				specification.setValue(null);
				specificationService.updateById(specification);
			}
		}

		// 商品货品表Eye_product
		for (EyeGoodsProduct product : products) {
			if(product.getUpdateTime() == null) {
				productService.updateById(product);
			}
		}

		// 商品参数表Eye_goods_attribute
		for (EyeGoodsAttribute attribute : attributes) {
			if (attribute.getId() == null || attribute.getId().equals(0)){
				attribute.setGoodsId(goods.getId());
				attributeService.add(attribute);
			}
			else if(attribute.getDeleted()){
				attributeService.deleteById(attribute.getId());
			}
			else if(attribute.getUpdateTime() == null){
				attributeService.updateById(attribute);
			}
		}

		// 这里需要注意的是购物车Eye_cart有些字段是拷贝商品的一些字段，因此需要及时更新
		// 目前这些字段是goods_sn, goods_name, price, pic_url
		for (EyeGoodsProduct product : products) {
			cartService.updateProduct(product.getId(), goods.getGoodsSn(), goods.getName(), product.getPrice(), product.getUrl());
		}
		// 商品秒杀表Eye_goods_kill

		if(goodsKill!=null ) {
			String[] killdate=new String[]{};
			killdate=goodsKill.getKillDate();
			if(killdate != null && goodsKill.getIsKill()!=false) {
				//移除旧延时
				removeDelay(goodsKill);
				//增加新的延时
				goodsKill = addDelay(killdate, goods.getId(), goodsKill);
			}
			goodsKill.setGoodsId(goods.getId());
			if(goodsKill.getIsKill()==null) {
				String[] str = {"null","null"};
				goodsKill.setKillDate(str);
			}
		}
		goodsKillService.updateById(goodsKill);

		if(accessory !=null){
			if(accessory.getId() == null || accessory.getId().equals(0)){
				accessory.setGoodsId(goods.getId());
				if(accessory.getName() == null){
					accessory.setName(goods.getName());
				}
				accessoryService.add(accessory,0);
			}else if(accessory.getDeleted()){
				if(!StringUtils.isEmpty(accessory.getKey())){
					storageService.deleteByKey(accessory.getKey());
				}
				accessoryService.deleteById(accessory.getId());
			}else if(accessory.getUpdatetime() == null){
				accessoryService.updateById(accessory);
			}
		}

		return ResponseUtil.ok();
	}

	private void removeDelay(EyeGoodsKill goodsKill) {
		EyeGoodsKill oldGoodsKill = goodsKillService.findById(goodsKill.getId());
		LocalDateTime oldStartDate = oldGoodsKill.getStartDate();
		LocalDateTime oldEndDate = oldGoodsKill.getEndDate();
		LocalDateTime nowTime = oldGoodsKill.getNowTime();
		if(oldStartDate == null || oldEndDate == null){
			return ;
		}
		if(nowTime != null && nowTime.isBefore(oldStartDate)){
			long oldStartDelay = ChronoUnit.MILLIS.between(nowTime, oldStartDate);
			taskService.removeTask(new KillGoodsStartTask(oldGoodsKill.getGoodsId(),oldStartDelay));
		}
		long oldEndDelay = ChronoUnit.MILLIS.between(nowTime, oldEndDate);
		taskService.removeTask(new KillGoodsExpiredTask(oldGoodsKill.getGoodsId(),oldEndDelay));
	}

	@Transactional
	public Object delete(EyeGoods goods) {
		Integer id = goods.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}
		Integer gid = goods.getId();
		goodsService.deleteById(gid);
		specificationService.deleteByGid(gid);
		attributeService.deleteByGid(gid);
		productService.deleteByGid(gid);
		goodsKillService.deleteByGid(gid);
		integralGoodsService.deleteByGid(gid);
		memberGoodsService.deleteByGid(gid);
		grouponRulesService.deleteByGid(gid);
		EyeAccessory accessory = accessoryService.queryByGid(gid,0);
		if(accessory !=null && !StringUtils.isEmpty(accessory.getKey())){
			storageService.deleteByKey(accessory.getKey());
		}
		accessoryService.deleteByGid(gid,0);

		return ResponseUtil.ok();
	}
	/**
	 * 商品上架
	 * @param goodsAllinone
	 * @return
	 */
	@Transactional
	public Object create(GoodsAllinone goodsAllinone) {
		Object error = validate(goodsAllinone);
		if (error != null) {
			return error;
		}
		EyeGoods goods = goodsAllinone.getGoods();
		EyeGoodsAttribute[] attributes = goodsAllinone.getAttributes();
		EyeGoodsSpecification[] specifications = goodsAllinone.getSpecifications();
		EyeGoodsProduct[] products = goodsAllinone.getProducts();
		EyeGoodsKill goodsKill=goodsAllinone.getGoodsKill();
		EyeAccessory accessory = goodsAllinone.getAccessory();

		String name = goods.getName();
		if (goodsService.checkExistByName(name)) {
			return ResponseUtil.fail(GOODS_NAME_EXIST, "商品名已经存在");
		}

		// 商品表里面有一个字段retailPrice记录当前商品的最低价
		BigDecimal retailPrice = new BigDecimal(Integer.MAX_VALUE);
		for (EyeGoodsProduct product : products) {
			BigDecimal productPrice = product.getPrice();
			if(retailPrice.compareTo(productPrice) == 1){
				retailPrice = productPrice;
			}
		}
		goods.setRetailPrice(retailPrice);
		if(goodsKill.getIsKill()==null) {
			goods.setIsKill(false);
		}else {
			goods.setIsKill(true);
		}
		// 商品基本信息表Eye_goods
		goods.setCommissionRate(goods.getCommissionRate().divide(new BigDecimal(100)));

		goodsService.add(goods);

		//商品秒杀表Eye_goods_kill
		if(goodsKill!=null ) {
			String[] killdate=new String[]{};
			killdate=goodsKill.getKillDate();
			if(killdate != null) {
				//秒杀商品加入延时
				goodsKill = addDelay(killdate,goods.getId(),goodsKill);

			}
			goodsKill.setGoodsId(goods.getId());
			if(goodsKill.getIsKill()==null) {
				String[] str = {"null","null"};
				goodsKill.setKillDate(str);
			}
		}
			goodsKillService.add(goodsKill);


		//将生成的分享图片地址写入数据库
		String url = qCodeService.createGoodShareImage(goods.getId().toString(), goods.getPicUrl(), goods.getName());
		if (!StringUtils.isEmpty(url)) {
			goods.setShareUrl(url);
			if (goodsService.updateById(goods) == 0) {
				throw new RuntimeException("更新数据失败");
			}
		}

		// 商品规格表Eye_goods_specification
		for (EyeGoodsSpecification specification : specifications) {
			specification.setGoodsId(goods.getId());
			specificationService.add(specification);
		}

		// 商品参数表Eye_goods_attribute
		for (EyeGoodsAttribute attribute : attributes) {
			attribute.setGoodsId(goods.getId());
			attributeService.add(attribute);
		}

		// 商品货品表Eye_product
		for (EyeGoodsProduct product : products) {
			product.setGoodsId(goods.getId());
			productService.add(product);
		}

		//如果商品有附件，保存附件信息
		if(accessory != null && accessory.getName() == null && accessory.getAccessoryLink() == null ){

		}else if(accessory !=null || accessory.getAccessoryLink() !=null  || accessory.getAccessoryCode() !=null){
			accessory.setGoodsId(goods.getId());
			if(accessory.getName()== null) {
				accessory.setName(goods.getName());
			}
			accessoryService.add(accessory,0);
		}else if(accessory != null || accessory.getName() !=null){
			accessory.setGoodsId(goods.getId());
			accessoryService.add(accessory,0);
		}
		return ResponseUtil.ok();
	}

	private EyeGoodsKill addDelay(String[] killdate, Integer goodsId,EyeGoodsKill goodsKill) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		goodsKill.setStartDate(LocalDateTime.parse(killdate[0],df));
		goodsKill.setEndDate(LocalDateTime.parse(killdate[1],df));

		LocalDateTime startDate = LocalDateTime.parse(killdate[0], df);
		LocalDateTime endDate = LocalDateTime.parse(killdate[1], df);
		LocalDateTime now = LocalDateTime.now();
		//秒杀商品开始任务
		if(now.isBefore(startDate)){
			long killStartDelay = ChronoUnit.MILLIS.between(now, startDate);
			goodsKill.setKillStatus(KillConstant.KILL_STATUS_NOT_AT_THE);
			taskService.addTask(new KillGoodsStartTask(goodsId,killStartDelay));
		}else{
			goodsKill.setKillStatus(KillConstant.KILL_STATUS_ONGOING);
		}

		//秒杀商品过期任务
		long killEndDelay = ChronoUnit.MILLIS.between(now, endDate);
		taskService.addTask(new KillGoodsExpiredTask(goodsId,killEndDelay));
		goodsKill.setNowTime(now);
		return goodsKill;
	}

	public Object list2() {
		// http://element-cn.eleme.io/#/zh-CN/component/cascader
		// 管理员设置“所属分类”
		List<EyeCategory> l1CatList = categoryService.queryL1();
		List<CatVo> categoryList = new ArrayList<>(l1CatList.size());
		for (EyeCategory l1 : l1CatList) {
			CatVo l1CatVo = new CatVo();
			l1CatVo.setValue(l1.getId());
			l1CatVo.setLabel(l1.getName());

			List<EyeCategory> l2CatList = categoryService.queryByPid(l1.getId());
			List<CatVo> children = new ArrayList<>(l2CatList.size());
			for (EyeCategory l2 : l2CatList) {
				CatVo l2CatVo = new CatVo();
				l2CatVo.setValue(l2.getId());
				l2CatVo.setLabel(l2.getName());
				children.add(l2CatVo);
			}
			l1CatVo.setChildren(children);
			categoryList.add(l1CatVo);
		}

		// http://element-cn.eleme.io/#/zh-CN/component/select
		// 管理员设置“所属品牌商”
		List<EyeBrand> list = brandService.all();
		List<Map<String, Object>> brandList = new ArrayList<>(l1CatList.size());
		for (EyeBrand brand : list) {
			Map<String, Object> b = new HashMap<>(2);
			b.put("value", brand.getId());
			b.put("label", brand.getName());
			brandList.add(b);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("categoryList", categoryList);
		data.put("brandList", brandList);
		return ResponseUtil.ok(data);
	}

	/**
	 * 根据id查询商品信息
	 * @param id
	 * @return
	 */
	public Object detail(Integer id) {
		EyeGoods goods = goodsService.findById(id);
		List<EyeGoodsProduct> products = productService.queryByGid(id);
		List<EyeGoodsSpecification> specifications = specificationService.queryByGid(id);
		List<EyeGoodsAttribute> attributes = attributeService.queryByGid(id);
		EyeGoodsKill goodskill = goodsKillService.findByGoodsId(goods.getId());
		goods.setCommissionRate(goods.getCommissionRate().multiply(new BigDecimal(100)));
		Integer categoryId = goods.getCategoryId();
		EyeCategory category = categoryService.findById(categoryId);
		EyeAccessory accessory = accessoryService.queryByGid(id,0);

		Integer[] categoryIds = new Integer[]{};
		if (category != null) {
			Integer parentCategoryId = category.getPid();
			categoryIds = new Integer[]{parentCategoryId, categoryId};
		}
		String[] killdate1=new String[]{};

		killdate1=goodskill.getKillDate();
		if(goodskill!=null && !StringUtils.isEmpty(goodskill.getKillDate())&& killdate1[0]==null) {
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		
			String[] killdate=new String[]{df.format(goodskill.getStartDate()),df.format(goodskill.getEndDate())};
			goodskill.setKillDate(killdate);
		}

		Map<String, Object> data = new HashMap<>();
		data.put("goods", goods);
		data.put("specifications", specifications);
		data.put("products", products);
		data.put("attributes", attributes);
		data.put("categoryIds", categoryIds);
		data.put("goodsKill", goodskill);
		data.put("accessory",accessory);
		return ResponseUtil.ok(data);
	}

}
