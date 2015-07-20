/***********************************************************************
 * Module:  AmountCtrl.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 09:16:04
 * Purpose: Implementation of the class AmountCtrl
 ***********************************************************************/

#include "AmountCtrl.h"

AmountCtrl::AmountCtrl()
{
	g_IOSet = CSingleton<CGlobalIOSet>::instance();
	
}
////////////////////////////////////////////////////////////////////////
// Name:       AmountCtrl::GetAmount()
// Purpose:    Implementation of AmountCtrl::GetAmount()
// Return:     int
////////////////////////////////////////////////////////////////////////

int AmountCtrl::GetAmount(void)
{
   // TODO : implement
   int i=0;
   int k=0;
   for(i=0;i<4;i++)
   {
   		selectSignal.all = i+k;
		setOUTPin(selectSignal);
		data |= (g_IOSet->m_IN_4067->ReadDigitalIn()&0X0001)<<i;
   }
   k+=4;
   for(i=0;i<4;i++)
   {
   		selectSignal.all = i+k;
		setOUTPin(selectSignal);
		data10 |= (g_IOSet->m_IN_4067->ReadDigitalIn()&0X0001)<<i;
   }
   
   k+=4;
   for(i=0;i<4;i++)
   {
   		selectSignal.all = i+k;
		setOUTPin(selectSignal);
		data100|= (g_IOSet->m_IN_4067->ReadDigitalIn()&0X0001)<<i;
   }

   int amount;
   amount = data100*100+data10*10+data;
   return amount;
   
   
}

void AmountCtrl::setOUTPin(complex8_t dat)
{
	g_IOSet->m_OUT_SelectA->SetDigitalOut(dat.bit.BIT_0);
	g_IOSet->m_OUT_SelectB->SetDigitalOut(dat.bit.BIT_1);
	g_IOSet->m_OUT_SelectC->SetDigitalOut(dat.bit.BIT_2);
	g_IOSet->m_OUT_SelectD->SetDigitalOut(dat.bit.BIT_3);
}
