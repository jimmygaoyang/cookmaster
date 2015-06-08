#include "stm32f10x.h" 
#include "usart.h" 
#include "DelayFun.h"
#include "dataFlash.h"
#include <string.h>
#include <stdio.h>
#include "GlobalIOSet.h"
#include "RS485Trans.h"
#include "BLETrans.h"

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



int WaitFor_Enter(unsigned int i)          //氓禄露忙鈥斅睹モ÷矫︹⒙
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
//氓鈥βヂ扁偓氓聫鈥毭︹⒙懊久铰モ÷矫︹⒙
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
	
	//猫庐戮莽陆庐忙艙潞氓鈩⒙尖撁ヂ徛
	PUT("Enter the machine number...\r\n");


	while(1)
	{
		//忙沤楼忙鈥澛睹︹⒙懊β嵚库∶ㄢ
		while(1)
		{
			if(usart3_read(&recChar, 1) == 1)
			{
				usart3_write(&recChar, 1);

				if(recChar != 0x08)  //氓娄鈥毭ε九撁ε铰ッ︹澛睹ニ喡懊♀灻λ溌┾偓鈧β犅(ASCII莽聽聛盲赂潞0x08)
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
				//氓藛陇忙鈥撀ε撀好モ劉篓莽录鈥撁ヂ徛访┾⒙棵ヂ郝
				if(strlen(recBuf) != 10)
				{
					//猫庐戮莽陆庐忙艙潞氓鈩⒙尖撁ヂ徛
					PUT("machine munber must be 10 bytes!Please input again ...\r\n");
				}
				else
				{
					//猫庐戮莽陆庐忙艙潞氓鈩⒙尖撁ヂ徛
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
		int initSetFlag = 0;
		Delay_ms(1000);
		PUT("press Entery key to stop system auto run ...\r\n")

		//氓藛陇忙鈥撀モ号久铰γλ喡β
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
		//猫驴鈥好∨捗ニ喡澝ヂр姑ヅ掆撁久铰ε撀好モ劉篓莽录鈥撁ヂ徛访寂捗ニ喡澝ヂр姑ヅ掆撁β犫∶ヂ库
		if (initSetFlag == 1)
		{
			PUT("start set global arguments...\r\n")
			//氓鈥郝好︹偓聛氓颅藴氓鈥毬モ劉篓猫庐戮莽陆庐猫驴鈥∶ㄢ
			GolobalArgSetProcess();
		}

	
		
		//猫炉禄氓聫鈥撁ε撀好モ劉篓莽录鈥撁ヂ徛
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
		BLETrans bleObj;

		while(1)
		{

			if(bleObj.Receive(globalBuf,recLen) == 1)
			{
				DBG_PRN(("%s","接收到蓝牙数据包"))
				DBG_NPRINT_HEX(globalBuf,recLen)

				Delay_ms(50);

				bleObj.Send("7654321",7);
				DBG_PRN(("%s","发送了蓝牙数据包"))
				DBG_NPRINT_HEX(globalBuf,recLen)
			}

//		 res = 0;
//		 while(res!= 1)
//		 {
//				res = RSObject.TransWith("0000000001", "HELLO", 5, globalBuf,recLen,4);
//				DBG_PRN(("接收返回值%d",res))
//				Delay_ms(10000);
//				Delay_ms(10000);
//				Delay_ms(10000);
//				Delay_ms(10000);
//		 }

//			res = 0;
//			while(res!= 1)
//			{
//					res = RSObject.TransWith("0000000003", "HELLO", 5, globalBuf,recLen,4);
//					DBG_PRN(("接收返回值%d",res))
//					Delay_ms(10000);
//					Delay_ms(10000);
//					Delay_ms(10000);
//					Delay_ms(10000);
//			}

			
//			if(RSObject.Receive(SrcMac,globalBuf,recLen) == 1)
//			{
//				DBG_PRN(("接收到%s发来的数据包",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)

//				Delay_ms(50);

//				RSObject.Send(SrcMac,globalBuf,recLen);
//				DBG_PRN(("向%s发送了数据包",SrcMac))
//				DBG_NPRINT_HEX(globalBuf,recLen)
//			}
			

		}

	
		
	
	
	
	
}

