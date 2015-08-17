#include "BaseProtocol.h"
#include <string.h>
BaseProtocol::BaseProtocol()
{
//	memcpy(macNum,mac,10);
}

BaseProtocol::~BaseProtocol()
{

}
void BaseProtocol::setMac( char *mac)
{
	memcpy(macNum,mac,10);
}



