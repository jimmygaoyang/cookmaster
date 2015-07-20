/***********************************************************************
 * Module:  KeyPadCtrl.h
 * Author:  Thinkpad
 * Modified: 2015-07-17 10:53:17
 * Purpose: Declaration of the class KeyPadCtrl
 ***********************************************************************/

#if !defined(__cmstm_KeyPadCtrl_h)
#define __cmstm_KeyPadCtrl_h
#include "GlobalIOSet.h"

#define KEY_BUTTON_OUTPUT 5
#define KEY_BUTTON_SELECT 1
#define KEY_BUTTON_UP 2
#define KEY_BUTTON_DOWN 3
#define KEY_BUTTON_OK 4


class KeyPadCtrl
{
public:
	KeyPadCtrl();
   int getKeyValue(void);

protected:
private:
	CGlobalIOSet *g_IOSet;

};

#endif