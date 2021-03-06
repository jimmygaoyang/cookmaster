/***********************************************************************
 * Module:  RS485Operater.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Declaration of the class RS485Operater
 * Comment: RS485操作类
 ***********************************************************************/

#if !defined(__Veg_RS485Operater_h)
#define __Veg_RS485Operater_h
#define MAC_NUM_LEN 10
//在规定时间内没有接到给定的数据就返回
int waitLen(int timeout,int len);

class RS485Trans
{
public:
   /* 向指定地址发送数据 */
   int Send(char* Addr, char* buf, int len);
   /* 初始化 */
   int Init(char* Addr);
   /* 接收数据 */
   int Receive(char* Addr, char *buf, int &len);
   /* 关闭485 */
   int Close();

   int TransWith(char* Addr, char* inputBuf, int len, char* outputBuf, int &outlen,int timeout);

   RS485Trans();
   ~RS485Trans();

protected:
private:
	char m_addr[MAC_NUM_LEN];
	// buffer to receive data
    unsigned char *m_recvBuff;
	// recvCount;
	int m_recvPos;
	// buffer to send data
	unsigned char *m_sendBuff; 
	//fillCount
	int m_fillPos;

};

#endif



