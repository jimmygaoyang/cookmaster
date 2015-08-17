/***********************************************************************
 * Module:  UnitState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:37:47
 * Purpose: Declaration of the class UnitState
 * Comment: ����״̬��ÿһ����ʵ��һ����Context��һ��״̬��ص���Ϊ
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