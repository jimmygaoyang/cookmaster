/***********************************************************************
 * Module:  LCDDriver.h
 * Author:  Thinkpad
 * Modified: 2015-07-20 15:28:48
 * Purpose: Declaration of the class LCDDriver
 ***********************************************************************/

#if !defined(__cmstm_LCDDriver_h)
#define __cmstm_LCDDriver_h
#include "GlobalIOSet.h"

class LCDDriver
{
	friend class CSingleton<LCDDriver>; //作为友元可以访问CSingleton的保护成员
public:
	LCDDriver();
   void SetFrame(char *buf);
   void GetFrame(char *buf);
   int Show(void);
   int ShowShining(int pos);
private:
   void LCD_write(char data, int rs);

protected:
private:
   char frame[32];
   CGlobalIOSet *g_IOSet;


};

#endif