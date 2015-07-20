/***********************************************************************
 * Module:  BLETrans.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Declaration of the class BLETrans
 * Comment: BLETrans������
 ***********************************************************************/

#if !defined(__Veg_BLETrans_h)
#define __Veg_BLETrans_h
#define MAC_NUM_LEN 10


class BLETrans
{
public:
   /* ��ָ����ַ�������� */
   int Send(char* buf, int len);
   /* ��ʼ�� */
   int Init();
  /* �������� */
   int Receive(char *buf, int &len);
   /* �ر�BLE */
   int Close();

   //�ڹ涨ʱ����û�нӵ����������ݾͷ���
   int waitLen(int timeout,int len);
   
   int TransWith(char* inputBuf, int len, char* outputBuf, int &outlen,int timeout);

   BLETrans();
   ~BLETrans();

protected:
private:

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



