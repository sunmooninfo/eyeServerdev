package com.eye.db.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class EyeGoods implements Serializable {
	public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    private Integer id;

    @ApiModelProperty(value = "商品编号")
    private String goodsSn;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品所属类目ID")
    private Integer categoryId;

    private Integer brandId;

    @ApiModelProperty(value = "商品宣传图片列表，采用JSON数组格式")
    private String[] gallery;

    @ApiModelProperty(value = "商品关键字，采用逗号间隔")
    private String keywords;

    @ApiModelProperty(value = "商品简介")
    private String brief;

    @ApiModelProperty(value = "是否上架")
    private Boolean isOnSale;

    private Short sortOrder;

    @ApiModelProperty(value = "商品页面商品图片")
    private String picUrl;

    @ApiModelProperty(value = "商品分享海报")
    private String shareUrl;

    @ApiModelProperty(value = "是否新品首发，如果设置则可以在新品首发页面展示")
    private Boolean isNew;

    @ApiModelProperty(value = "是否人气推荐，如果设置则可以在人气推荐页面展示")
    private Boolean isHot;

    @ApiModelProperty(value = "是否秒杀，如果设置则可以在秒杀活动页面展示")
    private Boolean isKill;

    @ApiModelProperty(value = "是否在小程序展示，如果设置则可以在小程序页面展示")
    private Boolean isShown;

    @ApiModelProperty(value = "商品单位，例如件、盒")
    private String unit;

    @ApiModelProperty(value = "专柜价格")
    private BigDecimal counterPrice;

    @ApiModelProperty(value = "零售价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "佣金比例")
    private BigDecimal commissionRate;

    @ApiModelProperty(value = "附件key")
    private String accessoryKey;

    @ApiModelProperty(value = "附件下载链接")
    private String accessoryLink;

    @ApiModelProperty(value = "附件下载提取码")
    private String accessoryCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "商品详细介绍，是富文本格式")
    private String detail;

    private static final long serialVersionUID = 1L;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String[] getGallery() {
        return gallery;
    }

    public void setGallery(String[] gallery) {
        this.gallery = gallery;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public Boolean getIsKill() {
        return isKill;
    }

    public void setIsKill(Boolean isKill) {
        this.isKill = isKill;
    }

    public Boolean getIsShown() {
        return isShown;
    }

    public void setIsShown(Boolean isShown) {
        this.isShown = isShown;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getAccessoryKey() {
        return accessoryKey;
    }

    public void setAccessoryKey(String accessoryKey) {
        this.accessoryKey = accessoryKey;
    }

    public String getAccessoryLink() {
        return accessoryLink;
    }

    public void setAccessoryLink(String accessoryLink) {
        this.accessoryLink = accessoryLink;
    }

    public String getAccessoryCode() {
        return accessoryCode;
    }

    public void setAccessoryCode(String accessoryCode) {
        this.accessoryCode = accessoryCode;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void andLogicalDeleted(boolean deleted) {
        setDeleted(deleted ? Deleted.IS_DELETED.value() : Deleted.NOT_DELETED.value());
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", name=").append(name);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", brandId=").append(brandId);
        sb.append(", gallery=").append(gallery);
        sb.append(", keywords=").append(keywords);
        sb.append(", brief=").append(brief);
        sb.append(", isOnSale=").append(isOnSale);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", picUrl=").append(picUrl);
        sb.append(", shareUrl=").append(shareUrl);
        sb.append(", isNew=").append(isNew);
        sb.append(", isHot=").append(isHot);
        sb.append(", isKill=").append(isKill);
        sb.append(", isShown=").append(isShown);
        sb.append(", unit=").append(unit);
        sb.append(", counterPrice=").append(counterPrice);
        sb.append(", retailPrice=").append(retailPrice);
        sb.append(", commissionRate=").append(commissionRate);
        sb.append(", accessoryKey=").append(accessoryKey);
        sb.append(", accessoryLink=").append(accessoryLink);
        sb.append(", accessoryCode=").append(accessoryCode);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", detail=").append(detail);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EyeGoods other = (EyeGoods) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoodsSn() == null ? other.getGoodsSn() == null : this.getGoodsSn().equals(other.getGoodsSn()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (Arrays.equals(this.getGallery(), other.getGallery()))
            && (this.getKeywords() == null ? other.getKeywords() == null : this.getKeywords().equals(other.getKeywords()))
            && (this.getBrief() == null ? other.getBrief() == null : this.getBrief().equals(other.getBrief()))
            && (this.getIsOnSale() == null ? other.getIsOnSale() == null : this.getIsOnSale().equals(other.getIsOnSale()))
            && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()))
            && (this.getPicUrl() == null ? other.getPicUrl() == null : this.getPicUrl().equals(other.getPicUrl()))
            && (this.getShareUrl() == null ? other.getShareUrl() == null : this.getShareUrl().equals(other.getShareUrl()))
            && (this.getIsNew() == null ? other.getIsNew() == null : this.getIsNew().equals(other.getIsNew()))
            && (this.getIsHot() == null ? other.getIsHot() == null : this.getIsHot().equals(other.getIsHot()))
            && (this.getIsKill() == null ? other.getIsKill() == null : this.getIsKill().equals(other.getIsKill()))
            && (this.getIsShown() == null ? other.getIsShown() == null : this.getIsShown().equals(other.getIsShown()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getCounterPrice() == null ? other.getCounterPrice() == null : this.getCounterPrice().equals(other.getCounterPrice()))
            && (this.getRetailPrice() == null ? other.getRetailPrice() == null : this.getRetailPrice().equals(other.getRetailPrice()))
            && (this.getCommissionRate() == null ? other.getCommissionRate() == null : this.getCommissionRate().equals(other.getCommissionRate()))
            && (this.getAccessoryKey() == null ? other.getAccessoryKey() == null : this.getAccessoryKey().equals(other.getAccessoryKey()))
            && (this.getAccessoryLink() == null ? other.getAccessoryLink() == null : this.getAccessoryLink().equals(other.getAccessoryLink()))
            && (this.getAccessoryCode() == null ? other.getAccessoryCode() == null : this.getAccessoryCode().equals(other.getAccessoryCode()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getDetail() == null ? other.getDetail() == null : this.getDetail().equals(other.getDetail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsSn() == null) ? 0 : getGoodsSn().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + (Arrays.hashCode(getGallery()));
        result = prime * result + ((getKeywords() == null) ? 0 : getKeywords().hashCode());
        result = prime * result + ((getBrief() == null) ? 0 : getBrief().hashCode());
        result = prime * result + ((getIsOnSale() == null) ? 0 : getIsOnSale().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        result = prime * result + ((getPicUrl() == null) ? 0 : getPicUrl().hashCode());
        result = prime * result + ((getShareUrl() == null) ? 0 : getShareUrl().hashCode());
        result = prime * result + ((getIsNew() == null) ? 0 : getIsNew().hashCode());
        result = prime * result + ((getIsHot() == null) ? 0 : getIsHot().hashCode());
        result = prime * result + ((getIsKill() == null) ? 0 : getIsKill().hashCode());
        result = prime * result + ((getIsShown() == null) ? 0 : getIsShown().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getCounterPrice() == null) ? 0 : getCounterPrice().hashCode());
        result = prime * result + ((getRetailPrice() == null) ? 0 : getRetailPrice().hashCode());
        result = prime * result + ((getCommissionRate() == null) ? 0 : getCommissionRate().hashCode());
        result = prime * result + ((getAccessoryKey() == null) ? 0 : getAccessoryKey().hashCode());
        result = prime * result + ((getAccessoryLink() == null) ? 0 : getAccessoryLink().hashCode());
        result = prime * result + ((getAccessoryCode() == null) ? 0 : getAccessoryCode().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getDetail() == null) ? 0 : getDetail().hashCode());
        return result;
    }

    public enum Deleted {
        NOT_DELETED(new Boolean("0"), "未删除"),
        IS_DELETED(new Boolean("1"), "已删除");

        private final Boolean value;

        private final String name;

        Deleted(Boolean value, String name) {
            this.value = value;
            this.name = name;
        }

        public Boolean getValue() {
            return this.value;
        }

        public Boolean value() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }

    public enum Column {
        id("id", "id", "INTEGER", false),
        goodsSn("goods_sn", "goodsSn", "VARCHAR", false),
        name("name", "name", "VARCHAR", true),
        categoryId("category_id", "categoryId", "INTEGER", false),
        brandId("brand_id", "brandId", "INTEGER", false),
        gallery("gallery", "gallery", "VARCHAR", false),
        keywords("keywords", "keywords", "VARCHAR", false),
        brief("brief", "brief", "VARCHAR", false),
        isOnSale("is_on_sale", "isOnSale", "BIT", false),
        sortOrder("sort_order", "sortOrder", "SMALLINT", false),
        picUrl("pic_url", "picUrl", "VARCHAR", false),
        shareUrl("share_url", "shareUrl", "VARCHAR", false),
        isNew("is_new", "isNew", "BIT", false),
        isHot("is_hot", "isHot", "BIT", false),
        isKill("is_kill", "isKill", "BIT", false),
        isShown("is_shown", "isShown", "BIT", false),
        unit("unit", "unit", "VARCHAR", false),
        counterPrice("counter_price", "counterPrice", "DECIMAL", false),
        retailPrice("retail_price", "retailPrice", "DECIMAL", false),
        commissionRate("commission_rate", "commissionRate", "DECIMAL", false),
        accessoryKey("accessory_key", "accessoryKey", "VARCHAR", false),
        accessoryLink("accessory_link", "accessoryLink", "VARCHAR", false),
        accessoryCode("accessory_code", "accessoryCode", "VARCHAR", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false),
        detail("detail", "detail", "LONGVARCHAR", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}