package com.unionpay.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 仅仅只是对size进行了封装
 * @author xiongym
 *
 */
public class SimplePageBuilder {
	
	/**
	 * 以常量的形式存储，在实际的运用中应该从properties文件中取得，思路都一样
	 * */
	public static final int size = 10;
	public static final int page = 1;
	
	public static Pageable generate(int page,int size,Sort sort) {
		if(sort==null) return  PageRequest.of(page, size);
		return PageRequest.of(page, size, sort);
	}
	
	public static Pageable generate() {
		return generate(page,size,null);
	}
	
	public static Pageable generate(int page) {
		return generate(page,size,null);
	}
	
	public static Pageable generate(int page,int size) {
		return generate(page,size,null);
	}
	
	public static Pageable generate(int page,Sort sort) {
		return generate(page,size,sort);
	}
}
