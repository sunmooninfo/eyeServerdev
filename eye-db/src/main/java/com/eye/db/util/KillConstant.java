package com.eye.db.util;

/**
 * 秒杀常数
 * @author HUAWEI
 *
 */
public class KillConstant {

	//未开始：0
	public static final Short KILL_STATUS_NOT_AT_THE = 0;
	//进行中：1
	public static final Short KILL_STATUS_ONGOING = 1;
	//到期：2
	public static final Short KILL_STATUS_DUE_TO = 2;


	//订单状态
	public static final Short STATUS_NONE = 0;
	public static final Short STATUS_ON = 1;
	public static final Short STATUS_SUCCEED = 2;
	public static final Short STATUS_FAIL = 3;

}
