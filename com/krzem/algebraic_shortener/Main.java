package com.krzem.algebraic_shortener;



public class Main{
	public static void main(String[] args){
		new Main();
	}



	public Main(){
		System.out.println(Shortener.shorten("9+7x-3x"));
		System.out.println(Shortener.shorten("3a+5+12a-7"));
		System.out.println(Shortener.shorten("8-4y-y-6"));
		System.out.println(Shortener.shorten("2x+5(2x+4)"));
		System.out.println(Shortener.shorten("8a-(6a-2)"));
		System.out.println(Shortener.shorten("3(5-4x)+10"));
		System.out.println(Shortener.shorten("3+5y-4(2y+5)"));
		System.out.println(Shortener.shorten("5(3b+4)-2(6-7b)"));
		// System.out.println(Shortener.shorten(""));
	}
}