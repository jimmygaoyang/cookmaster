/***********************************************************************
 * Module:  DecadeState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:07
 * Purpose: Implementation of the class DecadeState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "DecadeState.h"
#include "HundredState.h"
#include "Context.h"
#include "ReadyState.h"

////////////////////////////////////////////////////////////////////////
// Name:       DecadeState::handle(Context context)
// Purpose:    Implementation of DecadeState::handle()
// Parameters:
// - context
// Return:     void
////////////////////////////////////////////////////////////////////////

void DecadeState::handle(void *context)
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
			if(pContext->amount[1]=='9')
				pContext->amount[1]='0';
			else	
				pContext->amount[1]+1;
		}
		case KEY_BUTTON_DOWN:
		{
			if(pContext->amount[1]=='0')
				pContext->amount[1]='9';
			else	
				pContext->amount[1]-1;
		}
		case KEY_BUTTON_SELECT:
		{
			delete pContext->state; 
			pContext->state = new HundredState();
		}
	
		default:
			break;
	}
}