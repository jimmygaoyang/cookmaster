/***********************************************************************
 * Module:  BLETrans.cpp
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Implementation of the class BLETrans
 * Comment: BLETrans操作类
 ***********************************************************************/

#include "BLETrans.h"
#include <string.h>
#include "usart.h" 
#include "crc.h"
#include "DelayFun.h"
#include "GlobalIOSet.h"



#include "LOGCTRL.h"
//#define NO_POS_DEBUG
#include "pos_debug.h"

////////////////////////////////////////////////////////////////////////
// Name:       BLETrans::Send(int buf, int len)
// Purpose:    Implementation of BLETrans::Send()
// Comment:    向指定地址发送数据
// Parameters:
// - Addr
// - buf
// - len
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int BLETrans::Send(char* buf, int len)
{
   // TODO : implement
   unsigned short crc=0;
   m_fillPos = 0;
   //包头
   m_sendBuff[0] = 0x1B;
   m_fillPos++;
   m_sendBuff[1] = 0x10;
   m_fillPos++;
   //信息内容长度
   //memcpy(m_sendBuff + m_fillPos,(char *)&len,2);
	m_sendBuff[2] = (len&0x0ff00)>>8;
	m_sendBuff[3] = (len&0x00ff);
   m_fillPos += 2;
   //信息内容
   memcpy(m_sendBuff + m_fillPos,buf,len);
   m_fillPos += len;

   	//crc校验
	crc = cal_crc(m_sendBuff, m_fillPos);
	DBG_PRN(("crc = %04x",crc))
	*(m_sendBuff + m_fillPos++) =  ((unsigned char*)&crc)[1];
	*(m_sendBuff + m_fillPos++) =  ((unsigned char*)&crc)[0];
	usart1_write((char *)m_sendBuff,m_fillPos);
    DBG_NPRINT_HEX(m_sendBuff,m_fillPos)
   return 1;
}

////////////////////////////////////////////////////////////////////////
// Name:       BLETrans::Init()
// Purpose:    Implementation of BLETrans::Init()
// Comment:    初始化
// Parameters:
// - Addr
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int BLETrans::Init()
{
	
   // TODO : implement
   return 1;
   
}

////////////////////////////////////////////////////////////////////////
// Name:       BLETrans::Receive(int Addr, int buf, int len, std::string errStr)
// Purpose:    Implementation of BLETrans::Receive()
// Comment:    接收数据
// Parameters:
// [in/out] Addr 返回发送方地址	
// [out] buf	 返回接收数据首地址
// [in/out] len  返回接收到的数据长度
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int BLETrans::Receive(char *buf, int &len)
{
   // TODO : implement
   	int overtime = 0;
	int packageLen = 0; //包长度
	int tmpLen;
	unsigned short crc=0;
	m_recvPos = 0;
	
	while (overtime < 1000)
	{
		if (ser_can_read(UART1)> 0)
		{
			//判断包头
			if(waitLen(10,2)!=1)
				return 0;
			usart1_read((char*)m_recvBuff, 2);
			
			if(m_recvBuff[0] != 0x1B || m_recvBuff[1] != 0x10)//包头不对，跳出
			{
				return 0;
			}
			m_recvPos+=2;

			//数据总长度
			if(waitLen(10,2)!=1)
				return 0;
			usart1_read((char *)m_recvBuff+m_recvPos, 2);
			packageLen =m_recvBuff[m_recvPos]*256 +m_recvBuff[m_recvPos+1];
//			memcpy((char*)&packageLen,m_recvBuff+m_recvPos,2);
			DBG_PRN(("%s len= %d","get packge",packageLen))
			if(packageLen > sizeof(m_recvBuff))//数据长度错误
				return 0;
			m_recvPos = m_recvPos+2;
			len = packageLen;					
			int tmpRecLen = 0;			
			if (packageLen > 0)
			{
				while(tmpRecLen < packageLen)
				{
					tmpLen =  usart1_read((char*)(m_recvBuff+m_recvPos), packageLen-tmpRecLen);
					tmpRecLen+=tmpLen;
					m_recvPos += tmpLen;
					if (tmpLen ==0)//超时过多接收不到包就跳出
					{
						overtime++;
						Delay_ms(2);
						if (overtime > 1000)
						{
							return -1;
						}
					}
				}
				DBG_NPRINT_HEX(m_recvBuff,m_recvPos)
				//成功接收完一包数据 开始crc校验
				crc = cal_crc(m_recvBuff, m_recvPos);
				DBG_PRN(("crc = %04x",crc))
				crc = (crc&0x00FF)*256 + (crc&0xFF00)/256;
				DBG_NPRINT_HEX(((char *)&crc),2)
				//接收最后的校验位
				usart1_read((char*)(m_recvBuff+m_recvPos), 2);
				m_recvPos+=2;
				DBG_NPRINT_HEX(m_recvBuff,m_recvPos)
				if(strncmp((const char*)m_recvBuff+m_recvPos-2,(char *)&crc, 2))
				{
					DBG_PRN(("数据校验错误"))
					return 0;
				}
				
				memcpy(buf,m_recvBuff+2+2,packageLen);
				len = packageLen;
				return 1;
			}
		}
		overtime++;
		Delay_ms(2);
	}
	return -1;

}

////////////////////////////////////////////////////////////////////////
// Name:       RS485Operater::Close(std::string errStr)
// Purpose:    Implementation of RS485Operater::Close()
// Comment:    关闭485
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int BLETrans::Close()
{
   // TODO : implement
   return 1;
}
int BLETrans::TransWith(char* inputBuf, int len, char* outputBuf, int &outlen,int timeout)
{
	Send(inputBuf,len);
	DBG_PRN(("%s","发送蓝牙数据包"))
	DBG_NPRINT_HEX(inputBuf,len)
	Delay_ms(50);
	while(timeout>0)
	{
		if(Receive(outputBuf, outlen) == 1)
		{
			break;
		}
		timeout--;
		Delay_ms(100);
		if(timeout==0)
		{
			return -1;
		}
	}

	DBG_PRN(("%s","接收到蓝牙数据包"))
	DBG_NPRINT_HEX(outputBuf,outlen)
	return 1;

}



BLETrans::BLETrans()
{
	//usart1_open(9600);
}
//在规定时间内没有接到给定的数据就返回
int BLETrans::waitLen(int timeout,int len)
{
	while(ser_can_read(UART1)<len)
	{
		timeout--;
		Delay_ms(10);
		if(timeout==0)
			return -1;
		
	}
	return 1;
	
}
