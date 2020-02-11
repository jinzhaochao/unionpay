package com.unionpay.common.util;


import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * UUID 生成工具类
 * 
 */
public class UUIDGenerator {

	private static final int IP;
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	/**
	 * 构造方法
	 */
	public UUIDGenerator() {
	}

	/**
	 * 生成16进制表达的字符串 UUID。
	 * 
	 * @return 32 字节长度的 UUID 字符串
	 */
	public String generateHex() {
		StringBuilder sb = new StringBuilder(32);
		sb.append(format(getIP()));
		sb.append(format(getJVM()));
		sb.append(format(getHighTime()));
		sb.append(format(getLowTime()));
		sb.append(format(getCount()));
		return sb.toString().toUpperCase();
	}

	/**
	 * 生成20位数字的订单号
	 * 
	 * @return
	 */
	public static String getOrderNum20() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		buffer.append(RandomUtils.getRandomNum(8));
		return buffer.toString();
	}

	/**
	 * 获取16位数字的订单号
	 * 
	 * @return
	 */
	public static String getOrderNum16() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		buffer.append(RandomUtils.getRandomNum(4));
		return buffer.toString();
	}

	/**
	 * 获取18位数字的订单号
	 * 
	 * @return
	 */
	public static String getOrderNum18() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		buffer.append(RandomUtils.getRandomNum(6));
		return buffer.toString();
	}
	/**
	 * 获取19位数字的订单号
	 * 
	 * @return
	 */
	public static String getAccountNum19() {
		StringBuffer buffer = new StringBuffer("414918");
		buffer.append(new SimpleDateFormat("yyMMddHH").format(new Date()));
		buffer.append(RandomUtils.getRandomNum(5));
		return buffer.toString();
	}

	

	/**
	 * 生成订单号
	 * 
	 * @return
	 */
	public static String getOrderNum() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		buffer.append(RandomUtils.getRandomNum(4));
		return buffer.toString();
	}


	/**
	 * 生成字节数组的UUID
	 * 
	 * @return
	 */
	public byte[] generateBytes() {
		byte[] bytes = new byte[16];
		System.arraycopy(getBytes(getIP()), 0, bytes, 0, 4);
		System.arraycopy(getBytes(getJVM()), 0, bytes, 4, 4);
		System.arraycopy(getBytes(getHighTime()), 0, bytes, 8, 2);
		System.arraycopy(getBytes(getLowTime()), 0, bytes, 10, 4);
		System.arraycopy(getBytes(getCount()), 0, bytes, 14, 2);
		return bytes;
	}

	private String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuilder buf = new StringBuilder("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuilder buf = new StringBuilder("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	private int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	private short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0) {
				counter = 0;
			}
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	private int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	private short getHighTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private int getLowTime() {
		return (int) System.currentTimeMillis();
	}

	private static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}

	private static byte[] getBytes(int intval) {
		return new byte[] { (byte) (intval >> 24), (byte) (intval >> 16), (byte) (intval >> 8), (byte) intval };
	}

	private static byte[] getBytes(short shortval) {
		return new byte[] { (byte) (shortval >> 8), (byte) shortval };
	}

}
