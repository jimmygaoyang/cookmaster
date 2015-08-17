/***********************************************************************
 * Module:  HundredState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:51
 * Purpose: Declaration of the class HundredState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/
/***********************************************************************
 * Module:  ConcreteStateB.java
 * Author:  Thinkpad
 * Purpose: Defines the Class ConcreteStateB
 ***********************************************************************/

#if !defined(__cmstm_HundredState_h)
#define __cmstm_HundredState_h

#include "State.h"

class HundredState : public State
{
public:
   void handle(void *context);
   void refresh(void *context);

protected:
private:

};

#endif