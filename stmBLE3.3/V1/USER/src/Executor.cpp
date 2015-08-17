#include "Executor.h"
#include <string.h>

static char exeBuf[256];

void Executor::processing()
{
	char desMac[10];
	int res;
	if(bleObj->Receive(dataBuf,recLen) == 1)
	{
		memset(desMac,0,sizeof(desMac));
		memcpy(desMac,dataBuf,10);
		if(strncmp((const char *)desMac, macNum,MAC_NUM_LEN )!=0)//地址不对 转发到其它模块
		{
			res = RSObject->TransWith(desMac, dataBuf+10, recLen-10, dataBuf,recLen,4);// 转发其它
			if(res == 1)
			{
				bleObj->Send(dataBuf,recLen);
			}			
		}
		else
		{
			int cmdID = dataBuf[10];
			//自己模块内部处理
			switch(cmdID)
			{
				case 1:
				{
					OutputPro cmd;
					cmd.setMac(macNum);
					cmd.PackageParse((unsigned char*)(dataBuf+10),recLen-10);
					cmd.ConstructRspPackage((unsigned char*)dataBuf,recLen);	
					bleObj->Send(dataBuf,recLen);	
					cmd.Processing();
					break;
				}
				default:
					break;	
			}
		}
	}
	char srcMac[10];
	if(RSObject->Receive(srcMac,dataBuf,recLen) == 1)//直接接收到485的信息
	{
			int cmdID = dataBuf[0];
			//自己模块内部处理
			switch(cmdID)
			{
				case 1:
				{
					OutputPro cmd;
					cmd.setMac(macNum);
					cmd.PackageParse((unsigned char*)(dataBuf),recLen);
					cmd.ConstructRspPackage((unsigned char*)dataBuf,recLen);				
					RSObject->Send(srcMac,dataBuf,recLen);
					cmd.Processing();
					break;
				}

				default:
					break;
					
			}
	}
	
}

Executor::Executor(char * mac)
{
	bleObj = new BLETrans();
	RSObject = new RS485Trans();
	memcpy(macNum,mac,10);
	RSObject->Init(macNum);
	dataBuf = exeBuf;
	
}

Executor::~Executor()
{
	delete bleObj;
	delete RSObject;
}



