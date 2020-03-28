package com.krzem.algebraic_shortener;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



public class Shortener{
	public static String shorten(String seq){
		return Shortener._combine(Shortener._remove_brackets_l(Shortener._split(seq)));
	}



	public static ArrayList<String> calc(String seq){
		return Shortener._remove_brackets_l(Shortener._split(seq));
	}



	private static ArrayList<String> _split(String seq){
		ArrayList<String> sseq=new ArrayList<String>();
		int b=0;
		int i=0;
		String c="";
		while (i<seq.length()){
			char ch=seq.charAt(i);
			if (ch=='('){
				b++;
			}
			else if (ch==')'){
				b--;
			}
			if (b==0&&ch=='+'){
				if (c.length()>0){
					sseq.add(c);
					c="";
				}
			}
			else if (b==0&&ch=='-'){
				if (c.length()>0){
					sseq.add(c);
				}
				c="-";
			}
			else{
				c+=String.valueOf(ch);
			}
			i++;
		}
		if (c.length()>0){
			sseq.add(c);
		}
		return sseq;
	}



	private static ArrayList<String> _remove_brackets_l(ArrayList<String> seq){
		ArrayList<String> otp=new ArrayList<String>();
		for (int i=0;i<seq.size();i++){
			otp.addAll(Shortener._remove_brackets(seq.get(i)));
		}
		return otp;
	}



	private static ArrayList<String> _remove_brackets(String seq){
		int i=0;
		List<List<String>> l=new ArrayList<List<String>>();
		String mlt="";
		String c="";
		int b=0;
		while (i<seq.length()){
			char ch=seq.charAt(i);
			if (ch=='('){
				b++;
				if (b>1){
					c+=String.valueOf(ch);
				}
			}
			else if (ch==')'){
				b--;
				if (b>1){
					c+=String.valueOf(ch);
				}
				else{
					l.add(Shortener._remove_brackets_l(Shortener._split(c)));
					c="";
				}
			}
			else{
				if (b==0){
					mlt+=String.valueOf(ch);
				}
				else{
					c+=String.valueOf(ch);
				}
			}
			i++;
		}
		return Shortener._cross_mult(mlt,l);
	}



	private static ArrayList<String> _cross_mult(String mlt,List<List<String>> l){
		ArrayList<String> o=new ArrayList<String>();
		if (mlt.length()==0){
			mlt="1";
		}
		if (l.size()==0){
			o.add(mlt);
			return o;
		}
		o.addAll(Shortener._c_mult(mlt,l));
		return o;
	}



	private static ArrayList<String> _c_mult(String mlt,List<List<String>> l){
		ArrayList<String> o=new ArrayList<String>();
		if (l.size()==1){
			for (String e:l.get(0)){
				o.add(Shortener._mult(mlt,e));
			}
		}
		else{
			List<List<String>> nl=new ArrayList<List<String>>();
			for (int i=1;i<l.size();i++){
				nl.add(l.get(i));
			}
			for (String e:l.get(0)){
				o.addAll(Shortener._c_mult(Shortener._mult(mlt,e),nl));
			}
		}
		return o;
	}



	private static String _mult(String a,String b){
		boolean ms=false;
		if (a.indexOf("-")>-1){
			ms=!ms;
			a=a.replace("-","");
		}
		if (b.indexOf("-")>-1){
			ms=!ms;
			b=b.replace("-","");
		}
		double n=1;
		String bn="";
		String o="";
		int i=0;
		a+=" "+b;
		while (i<a.length()){
			String ch=String.valueOf(a.charAt(i));
			if (ch.equals(" ")){
				if (bn.length()>0){
					double n2=Double.parseDouble(bn);
					n*=n2;
					bn="";
				}
				i++;
				continue;
			}
			if ("0123456789.".indexOf(ch)>-1){
				bn+=ch;
			}
			else{
				if (bn.length()>0){
					double n2=Double.parseDouble(bn);
					n*=n2;
					bn="";
				}
				o+=ch;
			}
			i++;
		}
		if (bn.length()>0){
			double n2=Double.parseDouble(bn);
			n*=n2;
		}
		return (ms==true?"-":"")+Double.toString(n)+o;
	}



	private static String _combine(ArrayList<String> l){
		ArrayList<Object[]> ol=new ArrayList<Object[]>();
		for (String s:l){
			Object[] ss=Shortener._split_num(s);
			boolean nw=true;
			for (Object[] e:ol){
				if (((String)e[1]).equals((String)ss[1])){
					e[0]=((double)e[0])+((double)ss[0]);
					nw=false;
					break;
				}
			}
			if (nw==true){
				ol.add(ss);
			}
		}
		Map<String,Integer> mp=new TreeMap<String,Integer>();
		int i=0;
		String al="abcdefghijklmnopqrstuvwxyz";
		for (Object[] e:ol){
			if ((double)e[0]==0){
				i++;
				continue;
			}
			String s=(String)e[1];
			String ops="";
			for (int j=0;j<al.length();j++){
				char c=al.charAt(j);
				ops+=","+Long.toString(s.chars().filter(ch->ch==c).count());
			}
			ops=ops.substring(1);
			String[] opsa=ops.split(",");
			Arrays.sort(opsa);
			String ps="";
			for (int j=opsa.length-1;j>=0;j--){
				ps+=","+opsa[j];
			}
			ps=ps.substring(1);
			mp.put(ps+"."+ops,i);
			i++;
		}
		String o="";
		String[] mpk=mp.keySet().toArray(new String[mp.size()]);
		Integer[] mpv=mp.values().toArray(new Integer[mp.size()]);
		for (int j=mpv.length-1;j>=0;j--){
			int oi=(int)mpv[j];
			Object[] e=ol.get(oi);
			double nm=(double)e[0];
			String fs="";
			String[] fsdt=mpk[j].split("\\.")[1].split(",");
			for (int k=0;k<al.length();k++){
				String c=String.valueOf(al.charAt(k));
				long pw=Long.parseLong(fsdt[k]);
				if (pw==0){
					continue;
				}
				else if (pw==1){
					fs+=c;
				}
				else{
					fs+=c+"^"+fsdt[k];
				}
			}
			o+=(nm<0?" - ":(j==mpv.length-1?" ":" + "))+(Math.abs(nm)==1?"":(nm%1==0?Double.toString(Math.abs(nm)).split("\\.")[0]:Double.toString(Math.abs(nm))))+fs;
		}
		return o.substring(1);
	}



	private static Object[] _split_num(String s){
		boolean ms=false;
		if (s.indexOf("-")>-1){
			ms=true;
			s=s.replace("-","");
		}
		double n=(ms==true?-1:1);
		String bn="";
		String o="";
		int i=0;
		while (i<s.length()){
			String ch=String.valueOf(s.charAt(i));
			if ("0123456789.".indexOf(ch)>-1){
				bn+=ch;
			}
			else{
				if (bn.length()>0){
					double n2=Double.parseDouble(bn);
					n*=n2;
					bn="";
				}
				o+=ch;
			}
			i++;
		}
		if (bn.length()>0){
			double n2=Double.parseDouble(bn);
			n*=n2;
		}
		char[] to=o.toCharArray();
		Arrays.sort(to);
		return new Object[]{n,new String(to)};
	}
}