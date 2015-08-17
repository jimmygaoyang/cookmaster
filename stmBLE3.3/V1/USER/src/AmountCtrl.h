/***********************************************************************
 * Module:  AmountCtrl.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 09:16:04
 * Purpose: Declaration of the class AmountCtrl
 ***********************************************************************/

#if !defined(__cmstm_AmountCtrl_h)
#define __cmstm_AmountCtrl_h
#include "BuffType.h"
#include "GlobalIOSet.h"

class AmountCtrl
{
public:
	AmountCtrl();
   int GetAmount(void);
private:
	void setOUTPin(complex8_t dat);

protected:
private:
	CGlobalIOSet *g_IOSet;
	complex8_t selectSignal;
	char data;
	char data10;
	char data100;
};

#endif