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

class Context
{
public:
   /* ��������������������һ��״̬ */
   void request(int keyValue);
   Context();

protected:
public:
   State *state;
public:
	int key;
	char amount[3];
	



};

#endif
