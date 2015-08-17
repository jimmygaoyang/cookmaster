/***********************************************************************
 * Module:  HundredState.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:51
 * Purpose: Implementation of the class HundredState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/

#include "HundredState.h"
#include "Context.h"
#include "MotorDriver.h"
#include "DelayFun.h"
#include <stdlib.h>

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
			if(pContext->amount[0]=='5')
				pContext->amount[0]='0';
			else	
				pContext->amount[0]+=1;
			refresh(pContext);
			break;
		}
		case KEY_BUTTON_DOWN:
		{
			if(pContext->amount[0]=='0')
				pContext->amount[0]='5';
			else	
				pContext->amount[0]-=1;
			refresh(pContext);
			break;
		}
		case KEY_BUTTON_SELECT:
		{
			pContext->ChangeState(UNIT_STATE);
			break;	
		}
	
		default:
			break;
	}
}

void HundredState::refresh(void *context)
{
	char lcdBuf[32];
	Context* pContext = (Context *)context;
	memset(lcdBuf,' ',sizeof(lcdBuf));
	memcpy(lcdBuf,"Set Hundred",strlen("Set Hundred"));
	memcpy(lcdBuf+16,pContext->amount,3);
	LCDOperate->SetFrame(lcdBuf);
	LCDOperate->Show();
}