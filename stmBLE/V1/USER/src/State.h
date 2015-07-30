/***********************************************************************
 * Module:  State.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 13:31:41
 * Purpose: Declaration of the class State
 * Comment: 抽象状态类，定义一个接口以封装与Context的一个特定状态相关的行为
 ***********************************************************************/
/***********************************************************************
 * Module:  State.java
 * Author:  Thinkpad
 * Purpose: Defines the Class State
 ***********************************************************************/

#if !defined(__cmstm_State_h)
#define __cmstm_State_h
#include "KeyPadCtrl.h"
#include "LCDDriver.h"

class State
{
public:
	State();
	~State();
   virtual void handle(void *context) =0;
   virtual void refresh(void *context) =0;

protected:
private:
public:
	LCDDriver LCDOperate;


};

#endif

