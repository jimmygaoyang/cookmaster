/***********************************************************************
 * Module:  ReadyState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:36:54
 * Purpose: Implementation of the class ReadyState
 * Comment: ����״̬��ÿһ����ʵ��һ����Context��һ��״̬��ص���Ϊ
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
			//����
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