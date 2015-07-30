#include "GlobalIOSet.h"


CIOObject::CIOObject(GPIO_TypeDef * IOGroup, unsigned short IOnum, unsigned int IOrcc)
{
	IO_Group = IOGroup;
	IO_num = IOnum;
	IO_rcc = IOrcc;
}

void CIOObject::SetMode(int director)
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
	m_IN_BLEState = new CIOObject(GPIOC,GPIO_Pin_7,RCC_APB2Periph_GPIOC);
	m_IN_BLEState->SetMode(IN);
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
	m_OUT_SelectA->SetMode(OUT);
	m_OUT_SelectB = new CIOObject(GPIOC,GPIO_Pin_9,RCC_APB2Periph_GPIOC);
	m_OUT_SelectB->SetMode(OUT);
	m_OUT_SelectC = new CIOObject(GPIOC,GPIO_Pin_10,RCC_APB2Periph_GPIOC);
	m_OUT_SelectC->SetMode(OUT);
	m_OUT_SelectD= new CIOObject(GPIOC,GPIO_Pin_11,RCC_APB2Periph_GPIOC);
	m_OUT_SelectD->SetMode(OUT);

	m_IN_4067 = new CIOObject(GPIOC,GPIO_Pin_4,RCC_APB2Periph_GPIOC);
	m_IN_4067->SetMode(IN);
	m_IN_Output_Key = new CIOObject(GPIOC,GPIO_Pin_5,RCC_APB2Periph_GPIOC);
	m_IN_Output_Key->SetMode(IN);
	
	
}


