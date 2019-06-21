package com.nisum.test.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.nisum.test.model.RangeZipCode;

public class ZipUtil {
	
	public List<RangeZipCode> getMinimumZipCodeRanges(List<RangeZipCode> zipCodeRanges){
		if(zipCodeRanges == null)
			return null;
		
		if(zipCodeRanges.size() < 1)
			return null;
		
		List<RangeZipCode> sortedZipCodeRanges = sortZipCodeRangesToHandle(zipCodeRanges);
		
		return sortResult(getContinousZipRanges(new ArrayList<RangeZipCode>(), sortedZipCodeRanges));
	}
	
	@Test
	public void test() {
		List<RangeZipCode> list = new ArrayList<RangeZipCode>();
		List<RangeZipCode> result = new ArrayList<RangeZipCode>();
				
//		RangeZipCode range = new RangeZipCode(94133, 94133);
//		list.add(range);
//		range = new RangeZipCode(94200, 94299);
//		list.add(range);
//		range = new RangeZipCode(94600, 94699);
//		list.add(range);
		
		
//		RangeZipCode range = new RangeZipCode(94133, 94133);
//		list.add(range);
//		range = new RangeZipCode(94200, 94299);
//		list.add(range);
//		range = new RangeZipCode(94226, 94399);
//		list.add(range);
		
		RangeZipCode range = new RangeZipCode(6, 6);
		list.add(range);
		range = new RangeZipCode(20, 16);
		list.add(range);
		range = new RangeZipCode(2, 2);
		list.add(range);
		range = new RangeZipCode(10, 30);
		list.add(range);
		range = new RangeZipCode(4, 1);
		list.add(range);
		range = new RangeZipCode(10, 15);
		list.add(range);
		range = new RangeZipCode(29, 28);
		list.add(range);

		result = getMinimumZipCodeRanges(list);
		
		System.out.println(result);
	}
		
	private List<RangeZipCode> sortZipCodeRangesToHandle(List<RangeZipCode> zipCodeRanges){
		Collections.sort(zipCodeRanges, new Comparator<RangeZipCode>(){
			@Override
			public int compare(RangeZipCode zipCodeRange1, RangeZipCode zipCodeRange2) {
				if(!(zipCodeRange1.getStartZip() <= zipCodeRange1.getEndZip())) {
					Integer aux = zipCodeRange1.getStartZip();
					zipCodeRange1.setStartZip(zipCodeRange1.getEndZip());
					zipCodeRange1.setEndZip(aux);
				}
					
				if(!(zipCodeRange2.getStartZip() <= zipCodeRange2.getEndZip())) {
					Integer aux = zipCodeRange2.getStartZip();
					zipCodeRange2.setStartZip(zipCodeRange2.getEndZip());
					zipCodeRange2.setEndZip(aux);
				}
				
				if(zipCodeRange2.getEndZip().equals(zipCodeRange1.getEndZip()))
					return new Integer(zipCodeRange2.getStartZip().compareTo(zipCodeRange1.getStartZip()));
				else
					return new Integer(zipCodeRange2.getEndZip().compareTo(zipCodeRange1.getEndZip()));
			}
		});
		
		return zipCodeRanges;
	}
	
	private List<RangeZipCode> sortResult(List<RangeZipCode> zipCodeRanges){
		Collections.sort(zipCodeRanges, new Comparator<RangeZipCode>(){
			@Override
			public int compare(RangeZipCode zipCodeRange1, RangeZipCode zipCodeRange2) {				
				if(zipCodeRange2.getEndZip().equals(zipCodeRange1.getEndZip()))
					return new Integer(zipCodeRange1.getStartZip().compareTo(zipCodeRange2.getStartZip()));
				else
					return new Integer(zipCodeRange1.getEndZip().compareTo(zipCodeRange2.getEndZip()));
			}
		});
		
		return zipCodeRanges;
	}

	private List<RangeZipCode> getContinousZipRanges(List<RangeZipCode> zipRangesNoOverlaped, List<RangeZipCode> zipRangesOverlaped){
		RangeZipCode currentZipRange = zipRangesOverlaped.remove(0);
		
		if(zipRangesOverlaped.equals(Collections.EMPTY_LIST)){
			zipRangesNoOverlaped.add(currentZipRange);
			return zipRangesNoOverlaped;
		}

		RangeZipCode zipRangeToCheck = zipRangesOverlaped.get(0);
		
		if(getZipRangeCase(currentZipRange, zipRangeToCheck) == RangeZipCode.PeriodCase.INDEPENDENT)
			zipRangesNoOverlaped.add(currentZipRange);
		else{
			RangeZipCode mergedZipRange = getUnionZipRangeOverlaped(currentZipRange, zipRangeToCheck);
			zipRangesOverlaped.set(0, mergedZipRange);
		}
		
		return getContinousZipRanges(zipRangesNoOverlaped, zipRangesOverlaped);
	}
	
	private RangeZipCode.PeriodCase getZipRangeCase(RangeZipCode periodo1, RangeZipCode periodo2){
		if(independentRanges(periodo1, periodo2))
			return RangeZipCode.PeriodCase.INDEPENDENT;
		if(sameZipRanges(periodo1, periodo2))
			return RangeZipCode.PeriodCase.SAME;
		if(rangeContainedInsideRange(periodo1, periodo2))
			return RangeZipCode.PeriodCase.INSIDE_CONTENT;
		if(containedRange(periodo1, periodo2))
			return RangeZipCode.PeriodCase.PART_CONTENT;
		
		return null;
	}
	
	private boolean independentRanges(RangeZipCode zipRange1, RangeZipCode zipRange2){
		return !zipContainedInRange(zipRange1.getStartZip(), zipRange2) && !zipContainedInRange(zipRange1.getEndZip(), zipRange2) && !zipContainedInRange(zipRange2.getStartZip(), zipRange1) && !zipContainedInRange(zipRange2.getEndZip(), zipRange1);
	}
	
	private boolean containedRange(RangeZipCode periodo1, RangeZipCode periodo2){
		return zipContainedInRange(periodo1.getStartZip(), periodo2) || zipContainedInRange(periodo1.getEndZip(), periodo2); 
	}
	
	private boolean zipContainedInRange(Integer zipCode, RangeZipCode zipRange){
		return zipCode + 1 >= zipRange.getStartZip() && zipCode - 1 <= zipRange.getEndZip();
	}
	
	private boolean sameZipRanges(RangeZipCode zipRange1, RangeZipCode zipRange2){
		return zipRange1.getStartZip() == zipRange2.getStartZip() && zipRange1.getEndZip() == zipRange2.getEndZip();
	}
	
	private boolean rangeContainedInsideRange(RangeZipCode zipRange1, RangeZipCode zipRange2){
		return (zipContainedInRange(zipRange1.getStartZip(), zipRange2) && zipContainedInRange(zipRange1.getEndZip(), zipRange2))
		||(zipContainedInRange(zipRange2.getStartZip(), zipRange1) && zipContainedInRange(zipRange2.getEndZip(), zipRange1));
	}
	
	private RangeZipCode getUnionZipRangeOverlaped(RangeZipCode zipRange1, RangeZipCode zipRange2){
		if(getZipRangeCase(zipRange1, zipRange2) == RangeZipCode.PeriodCase.INDEPENDENT)
			return null;
		
		RangeZipCode unitedZipRange = new RangeZipCode();
		
		Integer startZipRange;
		Integer endZipRange;
		
		startZipRange = getZipRangeLessEqual(zipRange1.getStartZip(), zipRange2.getStartZip());
		endZipRange = getZipRangeGreater(zipRange1.getEndZip(), zipRange2.getEndZip());
		
		unitedZipRange.setStartZip(startZipRange);
		unitedZipRange.setEndZip(endZipRange);
		
		return unitedZipRange;
	}
	
	private Integer getZipRangeLessEqual(Integer zipRange1, Integer zipRange2){
		return (zipRange1 <= zipRange2) ? zipRange1 : zipRange2;
	}
	
	private Integer getZipRangeGreater(Integer zipRange1, Integer zipRange2){
		return (zipRange1 >= zipRange2) ? zipRange1 : zipRange2;
	}
}
