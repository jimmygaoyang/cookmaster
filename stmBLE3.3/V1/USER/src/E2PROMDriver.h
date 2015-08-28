#ifndef E2PROMDRIVER_H
#define E2PROMDRIVER_H

#define EEPROM_ADDRESS  0xA0
#define I2C_PageSize      16
#include "Singleton.h"



class E2PROMDriver
{
	friend class CSingleton<E2PROMDriver>; //��Ϊ��Ԫ���Է���CSingleton�ı�����Ա

private:
	   E2PROMDriver();
public:
	void MultiByteWrite(unsigned char* pBuffer, unsigned char WriteAddr, unsigned char NumByteToWrite);//дEEROM
	void MultiByteRead(unsigned char* pBuffer, unsigned char ReadAddr, unsigned short NumByteToRead);//��EEROM
};

#endif

