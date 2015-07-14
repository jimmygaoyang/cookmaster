#include "OutputPro.h"
#include <string.h>

#include "LOGCTRL.h"
	//#define NO_POS_DEBUG
#include "pos_debug.h"


OutputPro::OutputPro()
{
	
}
OutputPro::~OutputPro()
{
	
}
	//协议包解析与处理
int OutputPro::PackageParse(unsigned char *recv, int length)
{
	int i=0;
	cmdID=recv[i++];
	amount = recv[i]*256+recv[i+1];
	return 1;
}
	//协议事件处理
int OutputPro::Processing()
{
	DBG_PRN(("产生了 %d g的调料",amount))
	return 1;
}
	//组装返回数据包
int OutputPro::ConstructRspPackage(unsigned char *send, int &length)
{
	int i=0;
	memcpy(send,macNum,10);
	i+=10;
	send[i++]=1;
	length = i;
	return 1;
	
}
