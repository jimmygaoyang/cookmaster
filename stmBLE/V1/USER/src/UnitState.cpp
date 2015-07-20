/***********************************************************************
 * Module:  UnitState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:37:47
 * Purpose: Implementation of the class UnitState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "UnitState.h"
#include "Context.h"
#include "ReadyState.h"
#include "DecadeState.h"

////////////////////////////////////////////////////////////////////////
// Name:       UnitState::handle(Context context)
// Purpose:    Implementation of UnitState::handle()
// Parameters:
// - context
// Return:     void
////////////////////////////////////////////////////////////////////////

void UnitState::handle(void *context)
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
			if(pContext->amount[0]=='9')
				pContext->amount[0]='0';
			else	
				pContext->amount[0]+1;
		}
		case KEY_BUTTON_DOWN:
		{
			if(pContext->amount[0]=='0')
				pContext->amount[0]='9';
			else	
				pContext->amount[0]-1;
		}
		case KEY_BUTTON_SELECT:
		{
			delete pContext->state; 
			pContext->state = new DecadeState();
		}
	
		default:
			break;
	}
	
}