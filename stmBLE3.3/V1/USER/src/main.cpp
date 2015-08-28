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
#include "KeyPadCtrl.h"
#include "MotorDriver.h"
#include "Context.h"
#include "WeightMeasure.h"
#include "AmountCtrl.h"
#include "E2PROMDriver.h"

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
					E2PROMDriver *g_E2PROM = CSingleton<E2PROMDriver>::instance();
					g_E2PROM->MultiByteWrite((unsigned char*)recBuf, 0, MACHINE_NUM_LEN);
//					Flash_Write(MACHINE_NUM_ADRESS, (unsigned char*)recBuf, MACHINE_NUM_LEN);
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

	
		E2PROMDriver *g_E2PROM = CSingleton<E2PROMDriver>::instance();
		memset(tempBuf, 0, sizeof(tempBuf));	
		g_E2PROM->MultiByteRead((unsigned char*)tempBuf, 0, MACHINE_NUM_LEN);
//		Flash_Read(MACHINE_NUM_ADRESS, (unsigned char*)tempBuf, MACHINE_NUM_LEN);
		memset(showInfo, 0, sizeof(showInfo));
//		memcpy(tempBuf,"0000000002",10);
		sprintf(showInfo,"机器编号为%s\r\n", tempBuf);
		PUT(showInfo)

		KeyPadCtrl keyCtrl;
		int keyValue=0;
		RS485Trans RSObject;
		RSObject.Init(tempBuf);
		char SrcMac[11];
		memset(SrcMac,0,sizeof(SrcMac));
		int recLen = 0;
		int res = 0;
//		BLETrans *bleObj =  new BLETrans();
		Executor exeObj(tempBuf);
		g_IOset->m_OUT_485Direct->SetDigitalOut(LOW);
		AmountCtrl amount;
		MotorDriver *g_motor =  CSingleton<MotorDriver>::instance();
		g_motor->reset();
		Context CurWin;
		WeightMeasure weight;
		while(1)
		{
//			exeObj.processing();
//			keyValue = keyCtrl.getKeyValue();
//			if(keyValue>0)
//			{
//				//有按键事件
//				DBG_PRN(("按下的按键值为 %d",keyValue))
//				CurWin.request(keyValue);

//			}
			long dat = weight.read();
			memset(showInfo, 0, sizeof(showInfo));
			sprintf(showInfo,"%d\r\n", dat);
			PUT(showInfo);
//			double weightDat = weight.GetWeight();
//			memset(showInfo, 0, sizeof(showInfo));
//			sprintf(showInfo,"%f\r\n", weightDat);
//			PUT(showInfo);
			Delay_ms(25);
//			DBG_PRN(("重量为%d",weightDat))
//		MotorDriver *g_motor =  CSingleton<MotorDriver>::instance();
//		g_motor->rotateP(500*10);
//		Delay_ms(10);

		
//			DBG_PRN(("活着"))
//			DBG_PRN(("出料量 = %d",amount.GetAmount()))
			

		}

	
		
	
	
	
	
}

