package com.unionpay.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wangyue
 *
 */
public class ObjectCastEntity {

	/**
	 * Object[]转化成自定义实体类
	 */
	public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
		List<T> returnList = new ArrayList<T>();
		Object[] co = list.get(0);
		Class[] c2 = new Class[co.length];
		for (int i = 0; i < co.length; i++) {
			c2[i] = co[i].getClass();
		}
		for (Object[] o : list) {
			Constructor<T> constructor = clazz.getConstructor(c2);
			returnList.add(constructor.newInstance(o));
		}
		return returnList;
	}
	
}
