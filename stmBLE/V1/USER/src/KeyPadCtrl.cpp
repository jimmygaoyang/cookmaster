/***********************************************************************
 * Module:  KeyPadCtrl.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 10:53:17
 * Purpose: Implementation of the class KeyPadCtrl
 ***********************************************************************/

#include "KeyPadCtrl.h"

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
   if(g_IOSet->m_IN_Output_Key->ReadDigitalIn())
   	KeyValue = KEY_BUTTON_OUTPUT;
   if(g_IOSet->m_IN_Mode_Key->ReadDigitalIn())
   	KeyValue = KEY_BUTTON_SELECT;
   if(g_IOSet->m_IN_Up_Key->ReadDigitalIn())
   	KeyValue = KEY_BUTTON_UP;
   if(g_IOSet->m_IN_Down_Key->ReadDigitalIn())
   	KeyValue = KEY_BUTTON_DOWN;
   if(g_IOSet->m_IN_OK_Key->ReadDigitalIn())
   	KeyValue = KEY_BUTTON_OK;   
   return KeyValue;
   
   
}