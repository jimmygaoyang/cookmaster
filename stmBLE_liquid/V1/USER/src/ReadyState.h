/***********************************************************************
 * Module:  ReadyState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:36:54
 * Purpose: Declaration of the class ReadyState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/
/***********************************************************************
 * Module:  ConcreteStateA.java
 * Author:  Thinkpad
 * Purpose: Defines the Class ConcreteStateA
 ***********************************************************************/

#if !defined(__cmstm_ReadyState_h)
#define __cmstm_ReadyState_h

#include "State.h"

class ReadyState : public State
{
public:
   void handle(void *context);

protected:
private:

};

#endif