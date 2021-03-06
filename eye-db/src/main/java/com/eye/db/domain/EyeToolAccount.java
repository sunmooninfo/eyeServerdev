package com.eye.db.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class EyeToolAccount implements Serializable {
	public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();

	public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();

	private Integer id;

	@ApiModelProperty(value = "账号名称")
	private String accountName;

	@ApiModelProperty(value = "账号密码")
	private String accountPassword;

	@ApiModelProperty(value = "绑定手机")
	private String bindingMobile;

	@ApiModelProperty(value = "验证码")
	private String verificationCode;

	@ApiModelProperty(value = "添加时间")
	private LocalDateTime addTime;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "是否删除")
	private Boolean deleted;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public String getBindingMobile() {
		return bindingMobile;
	}

	public void setBindingMobile(String bindingMobile) {
		this.bindingMobile = bindingMobile;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
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
		//        sb.append(getClass().getSimpleName());
		//        sb.append(" [");
		//        sb.append("Hash = ").append(hashCode());
		//        sb.append(", IS_DELETED=").append(IS_DELETED);
		//        sb.append(", NOT_DELETED=").append(NOT_DELETED);
		//        sb.append(", id=").append(id);
		//        sb.append(", accountName=").append(accountName);
		//        sb.append(", accountPassword=").append(accountPassword);
		//        sb.append(", bindingMobile=").append(bindingMobile);
		//        sb.append(", verificationCode=").append(verificationCode);
		//        sb.append(", addTime=").append(addTime);
		//        sb.append(", updateTime=").append(updateTime);
		//        sb.append(", deleted=").append(deleted);
		//        sb.append(", serialVersionUID=").append(serialVersionUID);
		//        sb.append("]");
		sb.append("用户信息");
		sb.append(" [");
		sb.append(", id:").append(id);
		sb.append(", 账号名称:").append(accountName);
		sb.append(", 账号密码:").append(accountPassword);
		sb.append(", 手机号:").append(bindingMobile);
		sb.append(", 验证码:").append(verificationCode);
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
		EyeToolAccount other = (EyeToolAccount) that;
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
				&& (this.getAccountPassword() == null ? other.getAccountPassword() == null : this.getAccountPassword().equals(other.getAccountPassword()))
				&& (this.getBindingMobile() == null ? other.getBindingMobile() == null : this.getBindingMobile().equals(other.getBindingMobile()))
				&& (this.getVerificationCode() == null ? other.getVerificationCode() == null : this.getVerificationCode().equals(other.getVerificationCode()))
				&& (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
				&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
				&& (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
		result = prime * result + ((getAccountPassword() == null) ? 0 : getAccountPassword().hashCode());
		result = prime * result + ((getBindingMobile() == null) ? 0 : getBindingMobile().hashCode());
		result = prime * result + ((getVerificationCode() == null) ? 0 : getVerificationCode().hashCode());
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
		accountName("account_name", "accountName", "VARCHAR", false),
		accountPassword("account_password", "accountPassword", "VARCHAR", false),
		bindingMobile("binding_mobile", "bindingMobile", "VARCHAR", false),
		verificationCode("verification_code", "verificationCode", "VARCHAR", false),
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