#include "OutputPro.h"
#include "DelayFun.h"
#include "GlobalIOSet.h"
#include <string.h>
#include "MotorDriver.h"

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
	CGlobalIOSet* g_IOset = CSingleton<CGlobalIOSet>::instance();
	g_IOset->m_OUT_BlueLight->SetDigitalOut(LOW);
	Delay_ms(100);
	g_IOset->m_OUT_BlueLight->SetDigitalOut(HIGH);
	MotorDriver *g_motor =  CSingleton<MotorDriver>::instance();
	g_motor->rotateP(amount*10);
	g_motor->rotateN(amount*10);
	
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
