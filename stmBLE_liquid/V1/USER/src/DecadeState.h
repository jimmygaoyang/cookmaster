/***********************************************************************
 * Module:  DecadeState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:07
 * Purpose: Declaration of the class DecadeState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/
/***********************************************************************
 * Module:  ConcreteStateB.java
 * Author:  Thinkpad
 * Purpose: Defines the Class ConcreteStateB
 ***********************************************************************/

#if !defined(__cmstm_DecadeState_h)
#define __cmstm_DecadeState_h

#include "State.h"

class DecadeState : public State
{
public:
   void handle(void *context);

protected:
private:

};

#endif