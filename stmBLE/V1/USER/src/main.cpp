#include "stm32f10x.h" 
#include "usart.h" 
#include "DelayFun.h"
#include "dataFlash.h"
#include <string.h>
#include <stdio.h>
#include "GlobalIOSet.h"
#include "RS485Trans.h"

#include "LOGCTRL.h"
//#define NO_POS_DEBUG
#include "pos_debug.h"

static char globalBuf[1024];
char showInfo[64];
char tempBuf[32];
//static dataBuf

typedef enum {
    MACHINE_NUM,
	VEGFILE_LEN,
	INIT_OVER
   
}GLOBAL_INIT_STATUS;



int WaitFor_Enter(unsigned int i)          //延时函数
{
	char recChar = 0;
	unsigned int recLen= 0;
	unsigned int i_delay,j_delay;
	for(i_delay=0;i_delay<i;i_delay++)
	{
		for(j_delay=0;j_delay<3000;j_delay++)
		{
			if(ser_can_read(UART3)> 0)
			{
				if(usart3_read(&recChar, 1) == 1)
				{
					if(recChar == 0x0d)
					{
						PUT(("find Enter KEY"))
						return 1;
					}	
				}
			}
		}
	 }
	return 0;
}
//全局参数设置函数
void GolobalArgSetProcess()
{
	char tmpBuf[64];
	char recBuf[32];
	char recChar=0;
	int recLen = 0;
	unsigned int curVer = 0;
	unsigned int newVer = 0;
	unsigned int filelen = 0;
	unsigned int blockIndex = 0;
	
	GLOBAL_INIT_STATUS status = MACHINE_NUM ;
	memset(recBuf, 0,sizeof(recBuf));
	
	//设置机器编号
	PUT("Enter the machine number...\r\n");


	while(1)
	{
		//接收数据过程
		while(1)
		{
			if(usart3_read(&recChar, 1) == 1)
			{
				usart3_write(&recChar, 1);

				if(recChar != 0x08)  //如果接收到的是退格(ASCII码为0x08)
	   				recBuf[recLen++] = recChar;
			      else
			    		recLen = (recLen -1 >=0 ? recLen -1 : 0);
			      if(recChar == 0x0d)
			      {
					usart3_send_str("\r\n");
					recBuf[recLen-1]=0;
					recLen=0;
					break;
			      }
				
			}
		}

		switch(status)
		{
			case MACHINE_NUM:
				//判断机器编号长度
				if(strlen(recBuf) != 10)
				{
					//设置机器编号
					PUT("machine munber must be 10 bytes!Please input again ...\r\n");
				}
				else
				{
					//设置机器编号
					Flash_Write(MACHINE_NUM_ADRESS, (unsigned char*)recBuf, MACHINE_NUM_LEN);
					PUT("Enter the Vegetable file length...\r\n");
					status = INIT_OVER;
				}
				break;

			default:
				break;
		}
		if(status == INIT_OVER)
		break;
		
	}

	
}

int main()
{
		usart1_open(9600);
		usart2_open(9600);
		usart3_open(115200);
		Delay_Init(72);
		CGlobalIOSet* g_IOset = CSingleton<CGlobalIOSet>::instance();
		int initSetFlag = 0;
		Delay_ms(1000);
		PUT("press Entery key to stop system auto run ...\r\n")

		//判断回车截止
		for(int i=3; i>0; i--)
		{
			memset(showInfo, 0, sizeof(showInfo));
			sprintf(showInfo,"Delay %d s\r",i);
			PUT((showInfo))
			if (WaitFor_Enter(1000) == 1)	
			{
				initSetFlag = 1;
				break;
			}	
		}
		//进行初始化设置机器编号，初始化标志
		if (initSetFlag == 1)
		{
			PUT("start set global arguments...\r\n")
			//固态存储器设置过程
			GolobalArgSetProcess();
		}

	
		
		//读取机器编号
		memset(tempBuf, 0, sizeof(tempBuf));
		Flash_Read(MACHINE_NUM_ADRESS, (unsigned char*)tempBuf, MACHINE_NUM_LEN);
		memset(showInfo, 0, sizeof(showInfo));
		sprintf(showInfo,"�������Ϊ%s\r\n", tempBuf);
		PUT(showInfo)
	
		RS485Trans RSObject;
		RSObject.Init(tempBuf);
		char SrcMac[11];
		memset(SrcMac,0,sizeof(SrcMac));
		int recLen = 0;
		int res = 0;
		while(1)
		{
			//发送数据到子设备
			res = 0;
			while(res!= 1)
			{
					res = RSObject.TransWith("0000000001", "HELLO", 5, globalBuf,recLen,3);
					DBG_PRN(("���շ���%d",res))
					Delay_ms(10000);
					Delay_ms(10000);
					Delay_ms(10000);
					Delay_ms(10000);
			}

			res = 0;
			while(res!= 1)
			{
					res = RSObject.TransWith("0000000003", "HELLO", 5, globalBuf,recLen,3);
					DBG_PRN(("���շ���%d",res))
					Delay_ms(10000);
					Delay_ms(10000);
					Delay_ms(10000);
					Delay_ms(10000);
			}

//		  res = RSObject.TransWith("0000000003", "HELLO", 5, globalBuf,recLen,3);
//			DBG_PRN(("���շ���%d",res))
//			Delay_ms(10000);
//			Delay_ms(10000);
//			Delay_ms(10000);
//			Delay_ms(10000);
//			Delay_ms(10000);
//			delay_ms(3000);
			
//			if(RSObject.Receive(SrcMac,globalBuf,recLen) == 1)
//			{
//				DBG_PRN(("���յ�����%s�����ݰ�",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)

//				Delay_ms(50);

//				RSObject.Send(SrcMac,globalBuf,recLen);
//				DBG_PRN(("��%s���������ݰ�",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)
//			}

		}

	
		
	
	
	
	
}

