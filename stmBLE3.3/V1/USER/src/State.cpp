#include "State.h"
#include "LCDDriver.h"
State::State()
{
	LCDOperate = CSingleton<LCDDriver>::instance();
}
State::~State()
{
}