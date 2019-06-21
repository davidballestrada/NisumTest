package com.nisum.test.execution;

import java.util.ArrayList;
import java.util.List;

import com.nisum.test.model.RangeZipCode;
import com.nisum.test.service.ZipUtil;

public class ExecuteZipCodes {

	public static void main(String[] args) {
		List<RangeZipCode> list = new ArrayList<RangeZipCode>();
		List<RangeZipCode> result = new ArrayList<RangeZipCode>();
		
//		RangeZipCode range = new RangeZipCode(6, 6);
//		list.add(range);
//		range = new RangeZipCode(20, 16);
//		list.add(range);
//		range = new RangeZipCode(2, 2);
//		list.add(range);
//		range = new RangeZipCode(10, 30);
//		list.add(range);
//		range = new RangeZipCode(4, 1);
//		list.add(range);
//		range = new RangeZipCode(10, 15);
//		list.add(range);
//		range = new RangeZipCode(29, 28);
//		list.add(range);
		
		RangeZipCode range = new RangeZipCode(94133, 94133);
		list.add(range);
		range = new RangeZipCode(94200, 94299);
		list.add(range);
		range = new RangeZipCode(94226, 94399);
		list.add(range);

		ZipUtil zipUtil = new ZipUtil();
		
		result = zipUtil.getMinimumZipCodeRanges(list);
		
		System.out.println(result);
	}

}
