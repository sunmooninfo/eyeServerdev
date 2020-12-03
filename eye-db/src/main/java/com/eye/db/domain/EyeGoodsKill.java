package com.eye.db.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class EyeGoodsKill implements Serializable {
    public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

    public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

    private Integer id;

    @ApiModelProperty(value = "秒杀商品的id")
    private Integer goodsId;

    @ApiModelProperty(value = "秒杀商品价格")
    private BigDecimal killPrice;

    @ApiModelProperty(value = "秒杀商品库存数量")
    private Integer stockCount;

    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "秒杀结束时间")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "秒杀时间")
    private String[] killDate;

    @ApiModelProperty(value = "是否参加商品秒杀，如果设置则可以在商品秒杀页面展示")
    private Boolean isKill;

    @ApiModelProperty(value = "秒杀状态，0是未开始，1是开始中，2是已结束")
    private Short killStatus;

    @ApiModelProperty(value = "延时相对现在时间")
    private LocalDateTime nowTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(BigDecimal killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

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

    public String[] getKillDate() {
        return killDate;
    }

    public void setKillDate(String[] killDate) {
        this.killDate = killDate;
    }

    public Boolean getIsKill() {
        return isKill;
    }

    public void setIsKill(Boolean isKill) {
        this.isKill = isKill;
    }

    public Short getKillStatus() {
        return killStatus;
    }

    public void setKillStatus(Short killStatus) {
        this.killStatus = killStatus;
    }

    public LocalDateTime getNowTime() {
        return nowTime;
    }

    public void setNowTime(LocalDateTime nowTime) {
        this.nowTime = nowTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", IS_DELETED=").append(IS_DELETED);
        sb.append(", NOT_DELETED=").append(NOT_DELETED);
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", killPrice=").append(killPrice);
        sb.append(", stockCount=").append(stockCount);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", killDate=").append(killDate);
        sb.append(", isKill=").append(isKill);
        sb.append(", killStatus=").append(killStatus);
        sb.append(", nowTime=").append(nowTime);
        sb.append(", addTime=").append(addTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
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
        EyeGoodsKill other = (EyeGoodsKill) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getKillPrice() == null ? other.getKillPrice() == null : this.getKillPrice().equals(other.getKillPrice()))
            && (this.getStockCount() == null ? other.getStockCount() == null : this.getStockCount().equals(other.getStockCount()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (Arrays.equals(this.getKillDate(), other.getKillDate()))
            && (this.getIsKill() == null ? other.getIsKill() == null : this.getIsKill().equals(other.getIsKill()))
            && (this.getKillStatus() == null ? other.getKillStatus() == null : this.getKillStatus().equals(other.getKillStatus()))
            && (this.getNowTime() == null ? other.getNowTime() == null : this.getNowTime().equals(other.getNowTime()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getKillPrice() == null) ? 0 : getKillPrice().hashCode());
        result = prime * result + ((getStockCount() == null) ? 0 : getStockCount().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + (Arrays.hashCode(getKillDate()));
        result = prime * result + ((getIsKill() == null) ? 0 : getIsKill().hashCode());
        result = prime * result + ((getKillStatus() == null) ? 0 : getKillStatus().hashCode());
        result = prime * result + ((getNowTime() == null) ? 0 : getNowTime().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
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
        goodsId("goods_id", "goodsId", "INTEGER", false),
        killPrice("kill_price", "killPrice", "DECIMAL", false),
        stockCount("stock_count", "stockCount", "INTEGER", false),
        startDate("start_date", "startDate", "TIMESTAMP", false),
        endDate("end_date", "endDate", "TIMESTAMP", false),
        killDate("kill_date", "killDate", "VARCHAR", false),
        isKill("is_kill", "isKill", "BIT", false),
        killStatus("kill_status", "killStatus", "SMALLINT", false),
        nowTime("now_time", "nowTime", "TIMESTAMP", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        deleted("deleted", "deleted", "BIT", false);

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