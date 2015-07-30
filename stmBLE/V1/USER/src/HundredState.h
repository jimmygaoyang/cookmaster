/***********************************************************************
 * Module:  HundredState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:38:51
 * Purpose: Declaration of the class HundredState
 * Comment: ����״̬��ÿһ����ʵ��һ����Context��һ��״̬��ص���Ϊ
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