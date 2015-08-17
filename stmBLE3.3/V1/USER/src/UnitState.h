/***********************************************************************
 * Module:  UnitState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:37:47
 * Purpose: Declaration of the class UnitState
 * Comment: 具体状态，每一子类实现一个与Context的一个状态相关的行为
 ***********************************************************************/
/***********************************************************************
 * Module:  ConcreteStateC.java
 * Author:  Thinkpad
 * Purpose: Defines the Class ConcreteStateC
 ***********************************************************************/

#if !defined(__cmstm_UnitState_h)
#define __cmstm_UnitState_h

#include "State.h"

class UnitState : public State
{
public:
   void handle(void *context);
   void refresh(void *context);

protected:
private:

};

#endif