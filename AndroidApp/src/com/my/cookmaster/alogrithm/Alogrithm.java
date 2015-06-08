package com.my.cookmaster.alogrithm;

public class Alogrithm {
	public static byte[] crc16(byte[] buf, int length)
	{
		 short i;
		 int crc=0;
		 int pos=0;
		 while(length--!=0)
		 {
		     for(i=(0x80&0xFF); i!=0; i/=2)
		     {
		        if((crc&0x08000)!=0) 
			   {
			    crc*=2;
			    crc^=0x18005;
			   }
			    else
			   {
			    crc*=2;
			   }
			   if(((buf[pos])&i)!=0) 
				   crc^=0x18005; 
			  }
		     pos++;
		   }
		 byte[] crcbyte = new byte[2];
		 crcbyte[0] = (byte)((crc&0xFF00)>>8);
		 crcbyte[1] = (byte)(crc&0x00FF);
		 return crcbyte;  
	}

}
