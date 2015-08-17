/***********************************************************************
 * Module:  DecadeState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:07
 * Purpose: Implementation of the class DecadeState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "DecadeState.h"
#include "Context.h"
#include "MotorDriver.h"
#include "DelayFun.h"
#include <stdlib.h>

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
			MotorDriver *g_motor =  CSingleton<MotorDriver>::instance();
			int mount = atoi(pContext->amount);
			g_motor->rotateP(mount);
			Delay_ms(1000);
			g_motor->rotateN(mount);
			Delay_ms(1000);			
			break;
		}

		case KEY_BUTTON_OK:
		{
			pContext->ChangeState(READY_STATE);
			break;	
		}
		case KEY_BUTTON_UP:
		{
			if(pContext->amount[1]=='9')
				pContext->amount[1]='0';
			else	
				pContext->amount[1]+=1;
			refresh(pContext);
			break;
		}
		case KEY_BUTTON_DOWN:
		{
			if(pContext->amount[1]=='0')
				pContext->amount[1]='9';
			else	
				pContext->amount[1]-=1;
			refresh(pContext);
			break;
		}
		case KEY_BUTTON_SELECT:
		{
			pContext->ChangeState(HUNDRED_STATE);
			break;	
		}
	
		default:
			break;
	}
}

void DecadeState::refresh(void *context)
{
	char lcdBuf[32];
	Context* pContext = (Context *)context;
	memset(lcdBuf,' ',sizeof(lcdBuf));
	memcpy(lcdBuf,"Set Decade",strlen("Set Decade"));
	memcpy(lcdBuf+16,pContext->amount,3);
	LCDOperate->SetFrame(lcdBuf);
	LCDOperate->Show();
}