/***********************************************************************
 * Module:  Context.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:31:41
 * Purpose: Declaration of the class Context
 * Comment: ά��һ��ConcreteState�����ʵ�������ʵ�����嵱ǰ��״̬
 ***********************************************************************/
/***********************************************************************
 * Module:  Context.java
 * Author:  Thinkpad
 * Purpose: Defines the Class Context
 ***********************************************************************/

#if !defined(__cmstm_Context_h)
#define __cmstm_Context_h

#include "State.h"
#define READY_STATE 0
#define UNIT_STATE 1
#define DECADE_STATE 2
#define HUNDRED_STATE 3




class Context
{
public:
   /* ��������������������һ��״̬ */
   void request(int keyValue);
   Context();
   ~Context();
   void ChangeState(int state);

protected:
private:
   State *state;
public:
	int key;
	char amount[3];
private:
	State *pReadyState;
	State *pUnitState;
	State *pDecadeState;
	State *pHundredState;
	



};

#endif
