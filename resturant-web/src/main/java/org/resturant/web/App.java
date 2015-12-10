package org.resturant.web;

import javax.ejb.Stateless;

@Stateless
public class App {
	public String getHello(){
		return "Hello";
	}
}
