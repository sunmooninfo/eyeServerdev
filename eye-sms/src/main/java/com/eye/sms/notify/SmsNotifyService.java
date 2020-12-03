package com.eye.sms.notify;


import org.springframework.scheduling.annotation.Async;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商城短信通知服务类
 */
public class SmsNotifyService {

	private SmsSender smsSender;
	private List<Map<String, String>> smsTemplate = new ArrayList<>();
	private List<Map<String, String>> wxTemplate = new ArrayList<>();

	public boolean isSmsEnable() {
		return smsSender != null;
	}

	/**
	 * 短信消息通知
	 *
	 * @param phoneNumber 接收通知的电话号码
	 * @param message     短消息内容，这里短消息内容必须已经在短信平台审核通过
	 */
	@Async
	public void notifySms(String phoneNumber, String message) {
		if (smsSender == null)
			return;

		smsSender.send(phoneNumber, message);
	}

	/**
	 * 短信模版消息通知
	 *
	 * @param phoneNumber 接收通知的电话号码
	 * @param smsNotifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
	 * @param params      通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
	 */
	@Async
	public void notifySmsTemplate(String phoneNumber, SmsNotifyType smsNotifyType, String[] params) {
		if (smsSender == null) {
			return;
		}

		String templateIdStr = getTemplateId(smsNotifyType, smsTemplate);
		if (templateIdStr == null) {
			return;
		}
		smsSender.sendWithTemplate(phoneNumber, templateIdStr, params);
	}

	/**
	 * 以同步的方式发送短信模版消息通知
	 *
	 * @param phoneNumber 接收通知的电话号码
	 * @param smsNotifyType  通知类别，通过该枚举值在配置文件中获取相应的模版ID
	 * @param params      通知模版内容里的参数，类似"您的验证码为{1}"中{1}的值
	 * @return
	 */
	public SmsResult notifySmsTemplateSync(String phoneNumber, SmsNotifyType smsNotifyType, String[] params) {
		if (smsSender == null)
			return null;
		
		return smsSender.sendWithTemplate(phoneNumber, getTemplateId(smsNotifyType, smsTemplate), params);
	}
	private String getTemplateId(SmsNotifyType smsNotifyType, List<Map<String, String>> values) {
		for (Map<String, String> item : values) {
			String notifyTypeStr = smsNotifyType.getType();

			if (item.get("name").equals(notifyTypeStr))
				return item.get("templateId");
		}
		return null;
	}
	public void setSmsSender(SmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public void setSmsTemplate(List<Map<String, String>> smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public void setWxTemplate(List<Map<String, String>> wxTemplate) {
		this.wxTemplate = wxTemplate;
	}
}
