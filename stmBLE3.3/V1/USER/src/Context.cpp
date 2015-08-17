/***********************************************************************
 * Module:  Context.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:31:41
 * Purpose: Implementation of the class Context
 * Comment: ά��һ��ConcreteState�����ʵ�������ʵ�����嵱ǰ��״̬
 ***********************************************************************/

#include "Context.h"
#include "ReadyState.h"
#include "DecadeState.h"
#include "UnitState.h"
#include "HundredState.h"


void Context::ChangeState(int state)
{
	switch(state)
	{
		case READY_STATE:
			this->state = pReadyState;
			break;
		case UNIT_STATE:
			this->state = pUnitState;
			break;
		case DECADE_STATE:
			this->state = pDecadeState;
			break;
		case HUNDRED_STATE:
			this->state = pHundredState;
			break;
	}
	this->state->refresh(this);
}




////////////////////////////////////////////////////////////////////////
// Name:       Context::request(int keyValue)
// Purpose:    Implementation of Context::request()
// Comment:    ��������������������һ��״̬
// Parameters:
// - keyValue
// Return:     void
////////////////////////////////////////////////////////////////////////

void Context::request(int keyValue)
{
   // TODO : implement
   key = keyValue;
   state->handle(this);
}

////////////////////////////////////////////////////////////////////////
// Name:       Context::Context(State state)
// Purpose:    Implementation of Context::Context()
// Parameters:
// - state
// Return:     
////////////////////////////////////////////////////////////////////////

Context::Context()
{
   // TODO : implement
	
   	this->pReadyState = new ReadyState();
	this->pUnitState = new UnitState();
	this->pDecadeState = new DecadeState();
	this->pHundredState = new HundredState();
	this->state = pReadyState;
	this->amount[0]='0';
	this->amount[1]='0';
	this->amount[2]='0';
	this->state->refresh(this);
}

Context::~Context()
{
   // TODO : implement
	
   	delete pReadyState;
	delete pUnitState;
	delete pDecadeState;
	delete pHundredState;
	this->state = NULL;
}