/***********************************************************************
 * Module:  ReadyState.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:36:54
 * Purpose: Declaration of the class ReadyState
 * Comment: ����״̬��ÿһ����ʵ��һ����Context��һ��״̬��ص���Ϊ
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