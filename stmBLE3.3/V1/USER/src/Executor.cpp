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
		if(strncmp((const char *)desMac, macNum,MAC_NUM_LEN )!=0)//��ַ���� ת��������ģ��
		{
			res = RSObject->TransWith(desMac, dataBuf+10, recLen-10, dataBuf,recLen,4);// ת������
			if(res == 1)
			{
				bleObj->Send(dataBuf,recLen);
			}			
		}
		else
		{
			int cmdID = dataBuf[10];
			//�Լ�ģ���ڲ�����
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
	if(RSObject->Receive(srcMac,dataBuf,recLen) == 1)//ֱ�ӽ��յ�485����Ϣ
	{
			int cmdID = dataBuf[0];
			//�Լ�ģ���ڲ�����
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



