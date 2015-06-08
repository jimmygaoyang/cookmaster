/***********************************************************************
 * Module:  BLETrans.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Declaration of the class BLETrans
 * Comment: BLETrans操作类
 ***********************************************************************/

#if !defined(__Veg_BLETrans_h)
#define __Veg_BLETrans_h
#define MAC_NUM_LEN 10


class BLETrans
{
public:
   /* 向指定地址发送数据 */
   int Send(char* buf, int len);
   /* 初始化 */
   int Init();
   /* 接收数据 */
   int Receive(char *buf, int &len);
   /* 关闭BLE */
   int Close();

   //在规定时间内没有接到给定的数据就返回
   int waitLen(int timeout,int len);
   
   int TransWith(char* inputBuf, int len, char* outputBuf, int &outlen,int timeout);

   BLETrans();
   ~BLETrans();

protected:
private:

	// buffer to receive data
    unsigned char m_recvBuff[256];
	// recvCount;
	int m_recvPos;
	// buffer to send data
	unsigned char m_sendBuff[256]; 
	//fillCount
	int m_fillPos;

};

#endif



