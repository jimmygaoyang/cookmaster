/***********************************************************************
 * Module:  Context.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:31:41
 * Purpose: Declaration of the class Context
 * Comment: 维护一个ConcreteState子类的实例，这个实例定义当前的状态
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
   /* 对请求做处理，并设置下一个状态 */
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
