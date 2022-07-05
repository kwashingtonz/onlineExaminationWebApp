package com.onlineExamApp.app.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PaginatorHelper {	

	public static final int DEFAULT_PAGINATION_SIZE=5;
	
	public static final int ONE_PAGINATION_SIZE=1;
	
	public static <T> Page<T> pagiableList(List<T> list,Pageable pageable){
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
		if(start > end) {
			start = end;
		}
		return new PageImpl<T>(list.subList(start, end), pageable, list.size());
	}

}
