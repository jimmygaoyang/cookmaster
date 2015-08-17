/***********************************************************************
 * Module:  ReadyState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:36:54
 * Purpose: Implementation of the class ReadyState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "ReadyState.h"
#include "Context.h"
#include "MotorDriver.h"
#include <string.h>
#include "DelayFun.h" 
#include <stdlib.h>

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
			{
			MotorDriver *g_motor =  CSingleton<MotorDriver>::instance();
			int mount = atoi(pContext->amount);
			g_motor->rotateP(mount);
			Delay_ms(1000);
			g_motor->rotateN(mount);
			Delay_ms(1000);	
			break;
			}
		case KEY_BUTTON_OK:
			break;
	
		default:

		 pContext->ChangeState(UNIT_STATE);
		break;
	}
}

void ReadyState::refresh(void *context)
{
	char lcdBuf[32];
	Context* pContext = (Context *)context;
	memset(lcdBuf,' ',sizeof(lcdBuf));
	memcpy(lcdBuf,"Ready",strlen("Ready"));
	memcpy(lcdBuf+16,pContext->amount,3);
	LCDOperate->SetFrame(lcdBuf);
	LCDOperate->Show();
}