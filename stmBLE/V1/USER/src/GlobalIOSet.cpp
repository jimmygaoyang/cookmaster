#include "GlobalIOSet.h"
#define GPIO_Remap_SWJ_JTAGDisable  ((uint32_t)0x00300200)  /*!< JTAG-DP Disabled and SW-DP Enabled */


CIOObject::CIOObject(GPIO_TypeDef * IOGroup, unsigned short IOnum, unsigned int IOrcc)
{
	IO_Group = IOGroup;
	IO_num = IOnum;
	IO_rcc = IOrcc;
}

void CIOObject::SetMode(int director,int mode)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	RCC_APB2PeriphClockCmd(IO_rcc,ENABLE);
	GPIO_InitStructure.GPIO_Pin = IO_num;
	if(director == IN)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
	}
	if(director == OUT)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
	}
	if(director == ANALOG)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AIN;
	}

	if(mode == 1)//¿ªÂ©À­¸ß
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_OD;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_Init(IO_Group, &GPIO_InitStructure);	
}

void CIOObject::SetDigitalOut(int Status)
{
	if(Status == HIGH)
	{
		GPIO_SetBits(IO_Group,IO_num);
	}
	if(Status == LOW)
	{
		GPIO_ResetBits(IO_Group,IO_num);
	}	
}

int CIOObject::ReadDigitalIn()
{
	if(GPIO_ReadInputDataBit(IO_Group,IO_num)==Bit_RESET)
	{
		return LOW;	
	}
	else
	{
		return HIGH;
	}
}



CGlobalIOSet::CGlobalIOSet()
{
	

	GPIO_PinRemapConfig(GPIO_Remap_SWJ_JTAGDisable, ENABLE); 
	m_OUT_BLEEnable =  new CIOObject(GPIOC,GPIO_Pin_6,RCC_APB2Periph_GPIOC);
	m_OUT_BLEEnable->SetMode(OUT); 
	m_OUT_485Direct = new CIOObject(GPIOC,GPIO_Pin_12,RCC_APB2Periph_GPIOC);
	m_OUT_485Direct->SetMode(OUT);
	m_OUT_BlueLight = new CIOObject(GPIOC,GPIO_Pin_13,RCC_APB2Periph_GPIOC);
	m_OUT_BlueLight->SetMode(OUT);
	m_OUT_MotorAP = new CIOObject(GPIOC,GPIO_Pin_0,RCC_APB2Periph_GPIOC);
	m_OUT_MotorAP->SetMode(OUT);
	m_OUT_MotorAN = new CIOObject(GPIOC,GPIO_Pin_1,RCC_APB2Periph_GPIOC);
	m_OUT_MotorAN->SetMode(OUT);
	m_OUT_MotorBP = new CIOObject(GPIOC,GPIO_Pin_2,RCC_APB2Periph_GPIOC);
	m_OUT_MotorBP->SetMode(OUT);
	m_OUT_MotorBN = new CIOObject(GPIOC,GPIO_Pin_3,RCC_APB2Periph_GPIOC);
	m_OUT_MotorBN->SetMode(OUT);

	m_OUT_SelectA = new CIOObject(GPIOC,GPIO_Pin_8,RCC_APB2Periph_GPIOC);
	m_OUT_SelectA->SetMode(OUT,1);
	m_OUT_SelectB = new CIOObject(GPIOC,GPIO_Pin_9,RCC_APB2Periph_GPIOC);
	m_OUT_SelectB->SetMode(OUT,1);
	m_OUT_SelectC = new CIOObject(GPIOC,GPIO_Pin_10,RCC_APB2Periph_GPIOC);
	m_OUT_SelectC->SetMode(OUT,1);
	m_OUT_SelectD= new CIOObject(GPIOC,GPIO_Pin_11,RCC_APB2Periph_GPIOC);
	m_OUT_SelectD->SetMode(OUT,1);

	m_IN_4067 = new CIOObject(GPIOC,GPIO_Pin_7,RCC_APB2Periph_GPIOC);
	m_IN_4067->SetMode(IN);


	m_OUT_LCD_RS = new CIOObject(GPIOB,GPIO_Pin_12,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_RS->SetMode(OUT); 
	m_OUT_LCD_EN = new CIOObject(GPIOB,GPIO_Pin_13,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_EN->SetMode(OUT,1);

	m_OUT_LCD_DB0 = new CIOObject(GPIOB,GPIO_Pin_0,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB0->SetMode(OUT); 
	m_OUT_LCD_DB1 = new CIOObject(GPIOB,GPIO_Pin_1,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB1->SetMode(OUT);
	m_OUT_LCD_DB2 = new CIOObject(GPIOB,GPIO_Pin_2,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB2->SetMode(OUT,1);
	m_OUT_LCD_DB3 = new CIOObject(GPIOB,GPIO_Pin_3,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB3->SetMode(OUT);
	m_OUT_LCD_DB4 = new CIOObject(GPIOB,GPIO_Pin_4,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB4->SetMode(OUT,1);
	m_OUT_LCD_DB5 = new CIOObject(GPIOB,GPIO_Pin_5,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB5->SetMode(OUT);
	m_OUT_LCD_DB6 = new CIOObject(GPIOB,GPIO_Pin_8,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB6->SetMode(OUT,1);
	m_OUT_LCD_DB7 = new CIOObject(GPIOB,GPIO_Pin_9,RCC_APB2Periph_GPIOB);
	m_OUT_LCD_DB7->SetMode(OUT,1);

	
	m_OUT_HX711_SCK = new CIOObject(GPIOB,GPIO_Pin_14,RCC_APB2Periph_GPIOB);
	m_OUT_HX711_SCK->SetMode(OUT);
	m_IN_HX711_DA = new CIOObject(GPIOB,GPIO_Pin_15,RCC_APB2Periph_GPIOB);
	m_IN_HX711_DA->SetMode(IN);


	m_IN_Output_Key = new CIOObject(GPIOC,GPIO_Pin_5,RCC_APB2Periph_GPIOC);
	m_IN_Output_Key->SetMode(IN);

	m_IN_Mode_Key = new CIOObject(GPIOA,GPIO_Pin_4,RCC_APB2Periph_GPIOA);
	m_IN_Mode_Key->SetMode(IN);
	m_IN_Up_Key = new CIOObject(GPIOA,GPIO_Pin_5,RCC_APB2Periph_GPIOA);
	m_IN_Up_Key->SetMode(IN);
	m_IN_Down_Key = new CIOObject(GPIOA,GPIO_Pin_6,RCC_APB2Periph_GPIOA);
	m_IN_Down_Key->SetMode(IN);
	m_IN_OK_Key = new CIOObject(GPIOA,GPIO_Pin_7,RCC_APB2Periph_GPIOA);
	m_IN_OK_Key->SetMode(IN);

	
	
}


