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
	*@param int mode  ���ÿ�©���--1
	*
	*@return void 
	*/
	void SetMode(int director, int mode = 0);
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
	CIOObject *m_OUT_BLEEnable;	//Enable the BLE moudle
	CIOObject *m_OUT_HOT;		//set the hot module run or close     
	CIOObject *m_OUT_Light;		//set the Light module run or close 
	CIOObject *m_OUT_485Direct;		//����485��д����
	CIOObject *m_OUT_BlueLight;		//����ָʾ
	CIOObject *m_OUT_MotorAP;	//A+
	CIOObject *m_OUT_MotorAN;	//A-
	CIOObject *m_OUT_MotorBP;	//B+
	CIOObject *m_OUT_MotorBN;	//B-

	CIOObject *m_OUT_SelectA;	// ��ѡһA
	CIOObject *m_OUT_SelectB;	// ��ѡһB
	CIOObject *m_OUT_SelectC;	// ��ѡһC
	CIOObject *m_OUT_SelectD;	// ��ѡһD

	CIOObject *m_IN_4067;	// 4067�����


	CIOObject *m_OUT_LCD_RS;	// lcd_RS
	CIOObject *m_OUT_LCD_EN;	// lcd_EN

	CIOObject *m_OUT_LCD_DB0;	
	CIOObject *m_OUT_LCD_DB1;
	CIOObject *m_OUT_LCD_DB2;
	CIOObject *m_OUT_LCD_DB3;
	CIOObject *m_OUT_LCD_DB4;
	CIOObject *m_OUT_LCD_DB5;
	CIOObject *m_OUT_LCD_DB6;
	CIOObject *m_OUT_LCD_DB7;

	CIOObject *m_OUT_HX711_SCK;
	CIOObject *m_IN_HX711_DA;

	CIOObject *m_IN_Output_Key;	// ���ϰ���
	CIOObject *m_IN_Mode_Key;	
	CIOObject *m_IN_Up_Key;		
	CIOObject *m_IN_Down_Key;	
	CIOObject *m_IN_OK_Key;	

	
	

	

	
	
private:
    CGlobalIOSet(); //������ֱ��ʵ����
};

#endif
