/***********************************************************************
 * Module:  MotorCycleBuf.h
 * Author:  Thinkpad
 * Modified: 2015-07-16 10:44:41
 * Purpose: Declaration of the class MotorCycleBuf
 ***********************************************************************/

#if !defined(__cmstm_MotorCycleBuf_h)
#define __cmstm_MotorCycleBuf_h

class MotorCycleBuf
{
public:
   MotorCycleBuf(char *buf, int len);
   char getNext(void);
   char getPre(void);

protected:
private:
   char* dataBuf;
   int pos;
   int length;


};

#endif