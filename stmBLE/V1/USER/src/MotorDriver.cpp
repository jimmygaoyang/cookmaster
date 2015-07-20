/***********************************************************************
 * Module:  MotorDriver.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-15 17:37:07
 * Purpose: Implementation of the class MotorDriver
 ***********************************************************************/

#include "MotorDriver.h"
#include "BuffType.h"
#include "DelayFun.h"


static char rotateNBuf[8] = {0x01,0x05,0x04,0x06,0x02,0x0a,0x08,0x09};
static char rotateBig[4] = {0x01,0x02,0x04,0x08};
MotorDriver::MotorDriver()
{
	g_IOset = CSingleton<CGlobalIOSet>::instance();
//	cycBuf = new MotorCycleBuf(rotateNBuf,sizeof(rotateNBuf));
	cycBuf = new MotorCycleBuf(rotateBig,sizeof(rotateBig));
	
}
////////////////////////////////////////////////////////////////////////
// Name:       MotorDriver::rotateP(int steps)
// Purpose:    Implementation of MotorDriver::rotateP()
// Parameters:
// - steps
// Return:     void
////////////////////////////////////////////////////////////////////////


void MotorDriver::rotateP(int steps)
{
   // TODO : implement
	for(int i=0; i<steps;i++)
	{
		setIOState(cycBuf->getNext());
		Delay_ms(2);
	}

}

////////////////////////////////////////////////////////////////////////
// Name:       MotorDriver::rotateN(int steps)
// Purpose:    Implementation of MotorDriver::rotateN()
// Parameters:
// - steps
// Return:     void
////////////////////////////////////////////////////////////////////////

void MotorDriver::rotateN(int steps)
{
   // TODO : implement
   	for(int i=0; i<steps;i++)
	{
		setIOState(cycBuf->getPre());
		Delay_ms(2);
	}
}


void MotorDriver::setIOState(char data)
{
	complex8_t state;
	state.all=data;
	g_IOset->m_OUT_MotorAP->SetDigitalOut(state.bit.BIT_0);
	g_IOset->m_OUT_MotorAN->SetDigitalOut(state.bit.BIT_1);
	g_IOset->m_OUT_MotorBP->SetDigitalOut(state.bit.BIT_2);
	g_IOset->m_OUT_MotorBN->SetDigitalOut(state.bit.BIT_3);

}