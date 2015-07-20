/***********************************************************************
 * Module:  Context.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:31:41
 * Purpose: Implementation of the class Context
 * Comment: 维护一个ConcreteState子类的实例，这个实例定义当前的状态
 ***********************************************************************/

#include "Context.h"
#include "ReadyState.h"

////////////////////////////////////////////////////////////////////////
// Name:       Context::request(int keyValue)
// Purpose:    Implementation of Context::request()
// Comment:    对请求做处理，并设置下一个状态
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
	this->state = new ReadyState();

}