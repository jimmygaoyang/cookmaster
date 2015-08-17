/***********************************************************************
 * Module:  MotorCycleBuf.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-16 10:44:41
 * Purpose: Implementation of the class MotorCycleBuf
 ***********************************************************************/

#include "MotorCycleBuf.h"
#include <string.h>

////////////////////////////////////////////////////////////////////////
// Name:       MotorCycleBuf::MotorCycleBuf(char buf, int len)
// Purpose:    Implementation of MotorCycleBuf::MotorCycleBuf()
// Parameters:
// - buf
// - len
// Return:     int
////////////////////////////////////////////////////////////////////////

MotorCycleBuf::MotorCycleBuf(char *buf, int len)
{
   // TODO : implement
	dataBuf = new char[len];
 	memcpy(dataBuf,buf,len);
	length = len;
	pos = 0;
   
   
}

////////////////////////////////////////////////////////////////////////
// Name:       MotorCycleBuf::getNext()
// Purpose:    Implementation of MotorCycleBuf::getNext()
// Return:     char
////////////////////////////////////////////////////////////////////////

char MotorCycleBuf::getNext(void)
{
   // TODO : implement
   char tmpdata = dataBuf[pos];
   pos++;
   if(pos == length)
   	pos=0;
   return tmpdata;
}

////////////////////////////////////////////////////////////////////////
// Name:       MotorCycleBuf::getPre()
// Purpose:    Implementation of MotorCycleBuf::getPre()
// Return:     char
////////////////////////////////////////////////////////////////////////

char MotorCycleBuf::getPre(void)
{
   // TODO : implement
   char tmpdata = dataBuf[pos];
	pos--;
	if(pos== -1)
		pos=length-1;
   return tmpdata;
   	
}