/* Copyright (c) 2003, CWI, LORIA-INRIA All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation; either version 2, or 
 * (at your option) any later version.
 */

package shared;

public class HashFunctions {
	
	static public int oneAtATime(Object[] o) {
	   // [arg1,...,argn,symbol]
	   int hash = 0;
	   for (int i = 0; i < o.length; i++) {
		 hash += o[i].hashCode();
		 hash += (hash << 10);
		 hash ^= (hash >> 6);
	   }
	   hash += (hash << 3);
	   hash ^= (hash >> 11);
	   hash += (hash << 15);
	   //return (hash & 0x0000FFFF);
	   return hash;
	 }

	 static public int simple(Object[] o) {
	   // [arg1,...,argn,symbol]
	   int hash = o[o.length - 1].hashCode();
	   //      res = 65599*res + o[i].hashCode();
	   //      res = 16*res + (1+i)*o[i].hashCode();
	   for (int i = 0; i < o.length - 1; i++) {
		 hash = 16 * hash + o[i].hashCode();
	   }
	   return hash;
	 }

	 static public int cwi(Object[] o) {
	   // [arg1,...,argn,symbol]
	   int hash = 0;
	   for (int i = 0; i < o.length; i++) {
		 hash = (hash << 1) ^ (hash >> 1) ^ o[i].hashCode();
	   }
	   return hash;
	 }

	 static public int doobs(Object[] o) {
	   //System.out.println("static doobs_hashFuntion");

	   int initval = 0; /* the previous hash value */
	   int a, b, c, len;

	   /* Set up the internal state */
	   len = o.length;
	   a = b = 0x9e3779b9; /* the golden ratio; an arbitrary value */
	   c = initval; /* the previous hash value */

	   /*---------------------------------------- handle most of the key */
	   int k = 0;
	   while (len >= 12) {
		 a
		   += (o[k + 0].hashCode() + (o[k + 1].hashCode() << 8) + (o[k + 2].hashCode() << 16) + (o[k + 3].hashCode() << 24));
		 b
		   += (o[k + 4].hashCode() + (o[k + 5].hashCode() << 8) + (o[k + 6].hashCode() << 16) + (o[k + 7].hashCode() << 24));
		 c
		   += (o[k
			 + 8].hashCode()
			 + (o[k + 9].hashCode() << 8)
			 + (o[k + 10].hashCode() << 16)
			 + (o[k + 11].hashCode() << 24));
		 //mix(a,b,c);
		 a -= b;
		 a -= c;
		 a ^= (c >> 13);
		 b -= c;
		 b -= a;
		 b ^= (a << 8);
		 c -= a;
		 c -= b;
		 c ^= (b >> 13);
		 a -= b;
		 a -= c;
		 a ^= (c >> 12);
		 b -= c;
		 b -= a;
		 b ^= (a << 16);
		 c -= a;
		 c -= b;
		 c ^= (b >> 5);
		 a -= b;
		 a -= c;
		 a ^= (c >> 3);
		 b -= c;
		 b -= a;
		 b ^= (a << 10);
		 c -= a;
		 c -= b;
		 c ^= (b >> 15);

		 k += 12;
		 len -= 12;
	   }

	   /*------------------------------------- handle the last 11 bytes */
	   c += o.length;
	   switch (len) /* all the case statements fall through */ {
		 case 11 :
		   c += (o[k + 10].hashCode() << 24);
		 case 10 :
		   c += (o[k + 9].hashCode() << 16);
		 case 9 :
		   c += (o[k + 8].hashCode() << 8);
		   /* the first byte of c is reserved for the length */
		 case 8 :
		   b += (o[k + 7].hashCode() << 24);
		 case 7 :
		   b += (o[k + 6].hashCode() << 16);
		 case 6 :
		   b += (o[k + 5].hashCode() << 8);
		 case 5 :
		   b += o[k + 4].hashCode();
		 case 4 :
		   a += (o[k + 3].hashCode() << 24);
		 case 3 :
		   a += (o[k + 2].hashCode() << 16);
		 case 2 :
		   a += (o[k + 1].hashCode() << 8);
		 case 1 :
		   a += o[k + 0].hashCode();
		   /* case 0: nothing left to add */
	   }
	   //mix(a,b,c);
	   c = mix(a, b, c);

	   /*-------------------------------------------- report the result */
	   return c;
	 }

	 static public int doobs(String s, int c) {
	   // o[] = [name,Integer(arity), Boolean(isQuoted)]
	   // o[] = [value,offset,count,Integer(arity), Boolean(isQuoted)]

	   int offset = 0;
	   int count = 0;
	   char[] source = null;

	   count = s.length();
	   source = new char[count];
	   offset = 0;
	   s.getChars(0, count, source, 0);

	   int a, b, len;
	   /* Set up the internal state */
	   len = count;
	   a = b = 0x9e3779b9; /* the golden ratio; an arbitrary value */
	   /*------------------------------------- handle the last 11 bytes */
	   int k = offset;

	   while (len >= 12) {
		 a += (source[k + 0] + (source[k + 1] << 8) + (source[k + 2] << 16) + (source[k + 3] << 24));
		 b += (source[k + 4] + (source[k + 5] << 8) + (source[k + 6] << 16) + (source[k + 7] << 24));
		 c += (source[k + 8] + (source[k + 9] << 8) + (source[k + 10] << 16) + (source[k + 11] << 24));
		 // mix(a,b,c);
		 a -= b;
		 a -= c;
		 a ^= (c >> 13);
		 b -= c;
		 b -= a;
		 b ^= (a << 8);
		 c -= a;
		 c -= b;
		 c ^= (b >> 13);
		 a -= b;
		 a -= c;
		 a ^= (c >> 12);
		 b -= c;
		 b -= a;
		 b ^= (a << 16);
		 c -= a;
		 c -= b;
		 c ^= (b >> 5);
		 a -= b;
		 a -= c;
		 a ^= (c >> 3);
		 b -= c;
		 b -= a;
		 b ^= (a << 10);
		 c -= a;
		 c -= b;
		 c ^= (b >> 15);

		 k += 12;
		 len -= 12;
	   }
	   /*---------------------------------------- handle most of the key */
	   c += count;
	   switch (len) {
		 case 11 :
		   c += (source[k + 10] << 24);
		 case 10 :
		   c += (source[k + 9] << 16);
		 case 9 :
		   c += (source[k + 8] << 8);
		   /* the first byte of c is reserved for the length */
		 case 8 :
		   b += (source[k + 7] << 24);
		 case 7 :
		   b += (source[k + 6] << 16);
		 case 6 :
		   b += (source[k + 5] << 8);
		 case 5 :
		   b += source[k + 4];
		 case 4 :
		   a += (source[k + 3] << 24);
		 case 3 :
		   a += (source[k + 2] << 16);
		 case 2 :
		   a += (source[k + 1] << 8);
		 case 1 :
		   a += source[k + 0];
		   /* case 0: nothing left to add */
	   }

	   c = mix(a,b,c);
	   
	   return c;
	 }

	 public static int mix(int a, int b, int c) {
	   a -= b; a -= c; a ^= (c >> 13);
	   b -= c; b -= a; b ^= (a << 8);
	   c -= a; c -= b; c ^= (b >> 13);
	   a -= b; a -= c; a ^= (c >> 12);
	   b -= c; b -= a; b ^= (a << 16);
	   c -= a; c -= b; c ^= (b >> 5);
	   a -= b; a -= c; a ^= (c >> 3);
	   b -= c; b -= a; b ^= (a << 10);
	   c -= a; c -= b; c ^= (b >> 15);

	   return c;
	 }


}
