/***********************************************************************
 * Module:  ReadyState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:36:54
 * Purpose: Implementation of the class ReadyState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "ReadyState.h"
#include "Context.h"
#include "UnitState.h"

////////////////////////////////////////////////////////////////////////
// Name:       ReadyState::handle(Context context)
// Purpose:    Implementation of ReadyState::handle()
// Parameters:
// - context
// Return:     void
////////////////////////////////////////////////////////////////////////

void ReadyState::handle(void *context)
{
   // TODO : implement
	Context* pContext = (Context *)context;
	switch(pContext->key)
	{
		case KEY_BUTTON_OUTPUT:
			//出料
			break;
		case KEY_BUTTON_OK:
			break;
	
		default:
		{
			delete pContext->state; 
			pContext->state = new UnitState();
		}	
			break;
	}
}