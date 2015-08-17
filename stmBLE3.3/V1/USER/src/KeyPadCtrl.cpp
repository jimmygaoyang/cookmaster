/***********************************************************************
 * Module:  KeyPadCtrl.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 10:53:17
 * Purpose: Implementation of the class KeyPadCtrl
 ***********************************************************************/

#include "KeyPadCtrl.h"
#include "DelayFun.h"
KeyPadCtrl::KeyPadCtrl()
{
	g_IOSet = CSingleton<CGlobalIOSet>::instance();
}
////////////////////////////////////////////////////////////////////////
// Name:       KeyPadCtrl::getKeyValue()
// Purpose:    Implementation of KeyPadCtrl::getKeyValue()
// Return:     int
////////////////////////////////////////////////////////////////////////

int KeyPadCtrl::getKeyValue(void)
{
	int KeyValue = 0;
   // TODO : implement
   if(checkKeyDown(g_IOSet->m_IN_Output_Key)==1)
	 {
		KeyValue = KEY_BUTTON_OUTPUT;
 		return KeyValue;

	 }
   	
   if(checkKeyDown(g_IOSet->m_IN_Mode_Key)==1)
	 {
		 KeyValue = KEY_BUTTON_SELECT;
		 return KeyValue;
	}

   if(checkKeyDown(g_IOSet->m_IN_Up_Key)==1)
	 {
		 KeyValue = KEY_BUTTON_UP;
		 return KeyValue;
	 }
   	
   if(checkKeyDown(g_IOSet->m_IN_Down_Key)==1)
	 {
		 KeyValue = KEY_BUTTON_DOWN;
		 return KeyValue;
	 }
   	
   if(checkKeyDown(g_IOSet->m_IN_OK_Key)==1)
	 {
		 KeyValue = KEY_BUTTON_OK;   
			return KeyValue;
	 }
	 return KeyValue;

   
   
}


int KeyPadCtrl::checkKeyDown(CIOObject *pIO)
{
	 if(pIO->ReadDigitalIn())
	 {
	 	Delay_ms(10);
		if(pIO->ReadDigitalIn())
		{
			while(pIO->ReadDigitalIn());
			Delay_ms(10);
			if(pIO->ReadDigitalIn()==LOW)
			{
		 		return 1;
			}

		}

	 }
	 else
	 	return 0;
}
