#include "stm32f10x.h" 
#include "usart.h" 
#include "DelayFun.h"
#include "dataFlash.h"
#include <string.h>
#include <stdio.h>
#include "GlobalIOSet.h"
#include "RS485Trans.h"
#include "BLETrans.h"
#include "Executor.h"

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



int WaitFor_Enter(unsigned int i)          
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
	

	PUT("Enter the machine number...\r\n");


	while(1)
	{
		
		while(1)
		{
			if(usart3_read(&recChar, 1) == 1)
			{
				usart3_write(&recChar, 1);

				if(recChar != 0x08) 
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
				if(strlen(recBuf) != 10)
				{
					PUT("machine munber must be 10 bytes!Please input again ...\r\n");
				}
				else
				{
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
		g_IOset->m_OUT_BLEEnable->SetDigitalOut(HIGH);
		g_IOset->m_OUT_485Direct->SetDigitalOut(LOW);
		int initSetFlag = 0;
		Delay_ms(1000);
		PUT("press Entery key to stop system auto run ...\r\n")

		//
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
		if (initSetFlag == 1)
		{
			PUT("start set global arguments...\r\n")
			GolobalArgSetProcess();
		}

	
		
		memset(tempBuf, 0, sizeof(tempBuf));
		Flash_Read(MACHINE_NUM_ADRESS, (unsigned char*)tempBuf, MACHINE_NUM_LEN);
		memset(showInfo, 0, sizeof(showInfo));
		sprintf(showInfo,"机器编号为%s\r\n", tempBuf);
		PUT(showInfo)
	
		RS485Trans RSObject;
		RSObject.Init(tempBuf);
		char SrcMac[11];
		memset(SrcMac,0,sizeof(SrcMac));
		int recLen = 0;
		int res = 0;
//		BLETrans *bleObj =  new BLETrans();
		Executor exeObj(tempBuf);
			char tmpbuf[2];
			tmpbuf[0]= 0x55;
			tmpbuf[1]= 0xAA;
		g_IOset->m_OUT_485Direct->SetDigitalOut(LOW);
		while(1)
		{
//		DBG_PRN(("%s","活着"))
			exeObj.processing();
//			if(bleObj.Receive(globalBuf,recLen) == 1)
//			{
//				DBG_PRN(("%s","接收到蓝牙数据"))
//				DBG_NPRINT_HEX(globalBuf,recLen)

//				bleObj.Send("7654321",7);
//				DBG_PRN(("%s","返回了蓝牙数据"))
//				DBG_NPRINT_HEX(globalBuf,recLen)
//				g_IOset->m_OUT_BlueLight->SetDigitalOut(LOW);
//				Delay_ms(1000);
//				g_IOset->m_OUT_BlueLight->SetDigitalOut(HIGH);
//			}

//				g_IOset->m_OUT_485Direct->SetDigitalOut(HIGH);
//			Delay_ms(1);
//			usart2_write((char *)tmpbuf,2);
//			//恢复输入
//			Delay_ms(2);
//			g_IOset->m_OUT_485Direct->SetDigitalOut(LOW);
//		 res = 0;
//		 while(res!= 1)
//		 {
//			 RSObject.Send("0000000001", tmpbuf, 2);
//				res = RSObject.TransWith("0000000001", tmpbuf, 2, globalBuf,recLen,4);
//				Delay_ms(500); 
//			 Delay_ms(500); 
//			 Delay_ms(500); 
//			 res = RSObject.TransWith("0000000002", tmpbuf, 2, globalBuf,recLen,4);
			 
//				DBG_PRN(("接收返回值%d",res))
//				Delay_ms(1000);
//				Delay_ms(10000);

//		 }

//		 res = 0;
//		 while(res!= 1)
//		 {
//				res = RSObject.TransWith("0000000002", tmpbuf, 2, globalBuf,recLen,4);
////				DBG_PRN(("接收返回值%d",res))
////				Delay_ms(10000);
////				Delay_ms(10000);

//		 }


//			if(RSObject.Receive(SrcMac,globalBuf,recLen) == 1)
//			{
//				DBG_PRN(("接收到来自%s的485数据",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)
////				g_IOset->m_OUT_BlueLight->SetDigitalOut(LOW);
////				Delay_ms(500);
////				g_IOset->m_OUT_BlueLight->SetDigitalOut(HIGH);
//				RSObject.Send(SrcMac,globalBuf,recLen);
//				DBG_PRN(("向%s发送了数据",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)
//			}
			

			

		}

	
		
	
	
	
	
}

