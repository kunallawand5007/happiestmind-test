/**
 * 
 */
package com.happiestmind.api.util;

/**
 * @author klawand
 *
 */
public class FindWordRequest {

	String name;
	
	public FindWordRequest(String name) {
	 this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
