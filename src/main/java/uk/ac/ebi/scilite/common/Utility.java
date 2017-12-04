package uk.ac.ebi.scilite.common;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import uk.ac.ebi.literature.mongodb.util.Results;

public class Utility {

	public static boolean isNotEmpty(Object val){
		if (val == null){
			return false;
		}
		
		if (val instanceof String){
			if ("".equalsIgnoreCase(((String)val).trim())){
				return false;
			}
		}
		
		if (val instanceof List){
			if (((List)val).size()==0){
				return false;
			}
		}
		
		return true;
	}
}
