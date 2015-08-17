#ifndef GLOBALIOSET_H  
#define GLOBALIOSET_H 
 
#include "stm32f10x.h"
#include "Singleton.h"
//Singleton应用实例


#define IN  0
#define OUT 1
#define ANALOG 2


#define LOW 0
#define HIGH 1

class CIOObject
{	
public:	
	/**
	*@brief IOObject() 构造函数
	*
	*@param GPIO_TypeDef * IOGroup IO组
	*@param unsigned int IOnum	 IO号
	*@param unsigned int IOrcc	 IO时钟组
	*
	*@return void 
	*/
	CIOObject(GPIO_TypeDef * IOGroup, unsigned short IOnum, unsigned int IOrcc);

	~CIOObject();
	
	

	/**
	*@brief SetMode() 设置IO口的方向
	*
	*@param int diretor 设置为宏IN即输入 OUT即输出
	*@param int mode  设置开漏输出--1
	*
	*@return void 
	*/
	void SetMode(int director, int mode = 0);
	/**
	*@brief SetDigitalOut() 设置数字输出电平状态
	*
	*@param int Status 设置为宏HIGH即高电平 LOW即低电平
	*
	*@return void 
	*/
	void SetDigitalOut(int Status);
	/**
	*@brief ReadDigitalOut() 读取数字输入电平状态
	*
	*@return int 1-高电平  0-低电平
	*/
	int ReadDigitalIn();
	
	GPIO_TypeDef * IO_Group;	//GPIO组
	unsigned short IO_num;		//GPIO号
	unsigned int IO_rcc;		//GPIO时钟
	
	
};

class CGlobalIOSet
{
    
	friend class CSingleton<CGlobalIOSet>; //作为友元可以访问CSingleton的保护成员
public:
	CIOObject *m_OUT_BLEEnable;	//Enable the BLE moudle
	CIOObject *m_OUT_HOT;		//set the hot module run or close     
	CIOObject *m_OUT_Light;		//set the Light module run or close 
	CIOObject *m_OUT_485Direct;		//设置485读写方向
	CIOObject *m_OUT_BlueLight;		//蓝灯指示
	CIOObject *m_OUT_MotorAP;	//A+
	CIOObject *m_OUT_MotorAN;	//A-
	CIOObject *m_OUT_MotorBP;	//B+
	CIOObject *m_OUT_MotorBN;	//B-

	CIOObject *m_OUT_SelectA;	// 四选一A
	CIOObject *m_OUT_SelectB;	// 四选一B
	CIOObject *m_OUT_SelectC;	// 四选一C
	CIOObject *m_OUT_SelectD;	// 四选一D

	CIOObject *m_IN_4067;	// 4067输入端


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

	CIOObject *m_IN_Output_Key;	// 出料按键
	CIOObject *m_IN_Mode_Key;	
	CIOObject *m_IN_Up_Key;		
	CIOObject *m_IN_Down_Key;	
	CIOObject *m_IN_OK_Key;	

	
	

	

	
	
private:
    CGlobalIOSet(); //不允许直接实例化
};

#endif
