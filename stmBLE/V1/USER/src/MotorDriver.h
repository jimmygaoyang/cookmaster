/***********************************************************************
 * Module:  MotorDriver.h
 * Author:  Thinkpad
 * Modified: 2015-07-15 17:37:07
 * Purpose: Declaration of the class MotorDriver
 ***********************************************************************/

#if !defined(__cmstm_MotorDriver_h)
#define __cmstm_MotorDriver_h
#include "GlobalIOSet.h"
#include "Singleton.h"
#include "MotorCycleBuf.h"

class MotorDriver
{
friend class CSingleton<MotorDriver>; //��Ϊ��Ԫ���Է���CSingleton�ı�����Ա

private:
	MotorDriver();
public:		
   void rotateP(int steps);
   void rotateN(int steps);
   void reset();
private:
	void setIOState(char data);

protected:
private:
   CGlobalIOSet *g_IOset;
   MotorCycleBuf *cycBuf;


};

#endif