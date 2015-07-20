/***********************************************************************
 * Module:  HundredState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:51
 * Purpose: Implementation of the class HundredState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "HundredState.h"
#include "UnitState.h"
#include "Context.h"
#include "ReadyState.h"

////////////////////////////////////////////////////////////////////////
// Name:       HundredState::handle(Context context)
// Purpose:    Implementation of HundredState::handle()
// Parameters:
// - context
// Return:     void
////////////////////////////////////////////////////////////////////////

void HundredState::handle(void *context)
{
   // TODO : implement
    Context* pContext = (Context *)context;
	switch(pContext->key)
	{
		case KEY_BUTTON_OUTPUT:
		{

		}

		case KEY_BUTTON_OK:
		{
			delete pContext->state; 
			pContext->state = new ReadyState();
			break;	
		}
		case KEY_BUTTON_UP:
		{
			if(pContext->amount[2]=='9')
				pContext->amount[2]='0';
			else	
				pContext->amount[2]+1;
		}
		case KEY_BUTTON_DOWN:
		{
			if(pContext->amount[2]=='0')
				pContext->amount[2]='9';
			else	
				pContext->amount[2]-1;
		}
		case KEY_BUTTON_SELECT:
		{
			delete pContext->state; 
			pContext->state = new UnitState();
		}
	
		default:
			break;
	}
}