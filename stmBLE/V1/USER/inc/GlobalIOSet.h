#ifndef GLOBALIOSET_H  
#define GLOBALIOSET_H 
 
#include "stm32f10x.h"
#include "Singleton.h"
//SingletonӦ��ʵ��


#define IN  0
#define OUT 1
#define ANALOG 2


#define LOW 0
#define HIGH 1

class CIOObject
{	
public:	
	/**
	*@brief IOObject() ���캯��
	*
	*@param GPIO_TypeDef * IOGroup IO��
	*@param unsigned int IOnum	 IO��
	*@param unsigned int IOrcc	 IOʱ����
	*
	*@return void 
	*/
	CIOObject(GPIO_TypeDef * IOGroup, unsigned short IOnum, unsigned int IOrcc);

	~CIOObject();
	
	

	/**
	*@brief SetMode() ����IO�ڵķ���
	*
	*@param int diretor ����Ϊ��IN������ OUT�����
	*
	*@return void 
	*/
	void SetMode(int director);
	/**
	*@brief SetDigitalOut() �������������ƽ״̬
	*
	*@param int Status ����Ϊ��HIGH���ߵ�ƽ LOW���͵�ƽ
	*
	*@return void 
	*/
	void SetDigitalOut(int Status);
	/**
	*@brief ReadDigitalOut() ��ȡ���������ƽ״̬
	*
	*@return int 1-�ߵ�ƽ  0-�͵�ƽ
	*/
	int ReadDigitalIn();
	
	GPIO_TypeDef * IO_Group;	//GPIO��
	unsigned short IO_num;		//GPIO��
	unsigned int IO_rcc;		//GPIOʱ��
	
	
};

class CGlobalIOSet
{
    
	friend class CSingleton<CGlobalIOSet>; //��Ϊ��Ԫ���Է���CSingleton�ı�����Ա
public:
	CIOObject *m_IN_BLEState;		//read the input state of BLE  
	CIOObject *m_OUT_BLEEnable;	//Enable the BLE moudle
	CIOObject *m_OUT_HOT;		//set the hot module run or close     
	CIOObject *m_OUT_Light;		//set the Light module run or close 
	CIOObject *m_OUT_485Direct;		//����485��д����

	
	
private:
    CGlobalIOSet(); //������ֱ��ʵ����
};

#endif